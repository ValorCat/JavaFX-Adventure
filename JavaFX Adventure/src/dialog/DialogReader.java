package dialog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogReader {

	private static final String PATH_NAME = "src/dialog/res/%s.dlg";
	private static final Pattern PATTERN = Pattern.compile("\\A\"(.*)\"\\s?(.*)\\Z");
	private static final Pattern EQUATION = Pattern.compile("\\A([\\w_]+)([\\p{Punct}]{1,2})([\\p{Alnum}\\-\\.]+)\\Z");
	private static final String COMMENT = "#";
	
	private BufferedReader reader;
	private List<DialogNode> dialogTree;
	private Stack<DialogNode> currentBranch;
	private Map<String, DialogNode> labels;
	private Map<String, List<DialogNode>> needFutureLabel;
	private String rawLine;
	private int prevIndent = 0;
	private int currentLine = 1;
	private boolean lastWasNPCLine = false;

	public DialogReader(String dialogFileName) {
		dialogTree = new ArrayList<>();
		currentBranch = new Stack<>();
		labels = new HashMap<>();
		needFutureLabel = new HashMap<>();
		try {
			File file = new File(String.format(PATH_NAME, dialogFileName));
			reader = new BufferedReader(new FileReader(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<DialogNode> getDialogTree() {
		try {
			while ((rawLine = reader.readLine()) != null) {
				if (!rawLine.startsWith(COMMENT))
					process();
				currentLine++;
			}
			commitBranch();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dialogTree;
	}

	private int getIndentCount() {
		int count = 0;
		for (char c : rawLine.toCharArray()) {
			if (c != '\t')
				break;
			count++;
		}
		return count;
	}

	private void process() {
		int newIndent = getIndentCount();
		if (newIndent == 0) {
			commitBranch();
		} else if (newIndent == prevIndent) {
			DialogNode lastResponse = currentBranch.pop();
			currentBranch.peek().addResponse(lastResponse);
		} else if (newIndent < prevIndent) {
			closeOpenBranches(prevIndent - newIndent + 1);
		}
		currentBranch.push(buildNodeFromLine());
		prevIndent = newIndent;
	}
	
	private void commitBranch() {
		if (currentBranch.isEmpty())
			return;
		while (currentBranch.size() > 1) {
			DialogNode node = currentBranch.pop();
			currentBranch.peek().addResponse(node);
		}
		dialogTree.add(currentBranch.pop());
	}
	
	private void closeOpenBranches(int count) {
		while (count > 0) {
			DialogNode node = currentBranch.pop();
			currentBranch.peek().addResponse(node);
			count--;
		}
	}

	private DialogNode buildNodeFromLine() {
		DialogNode node = new DialogNode();
		Matcher matcher = PATTERN.matcher(rawLine.trim());
		if (!matcher.matches())
			throw new DialogParseException(rawLine, currentLine);
		node.setText(matcher.group(1));
		String specialOptions = matcher.group(2);
		if (specialOptions.length() > 0)
			setupNodeSpecialFlags(specialOptions, node);
		if (!lastWasNPCLine) {
			node.setSpeakerName(DialogNode.NPC_SPEAKER);
		}
		lastWasNPCLine = !lastWasNPCLine;
		return node;
	}
	
	private void setupNodeSpecialFlags(String text, DialogNode node) {
		// "text" @LBL XX% cond... -> &GOTO trade actn...
		boolean actionMode = false;
		for (String token : text.split(" ")) {
			if (actionMode) {
				if (token.startsWith("@")) {
					setupResponsesFromLabel(node, token.substring(1));
				} else if (token.equals("trade")) {
					node.setOpensTradeWindow(true);
				} else {
					setupNewAction(node, token);
				}
			} else {
				if (token.startsWith("@")) {
					setupNewLabel(node, token.substring(1));
				} else if (token.endsWith("%")) {
					setupRandomChance(node, token.substring(0, token.length() - 1));
				} else if (token.equals("->")) {
					actionMode = true;
				} else {
					setupNewCondition(node, token);
				}
			}
		}
	}
	
	private void setupResponsesFromLabel(DialogNode node, String labelName) {
		if (labels.containsKey(labelName)) {
			DialogNode destination = labels.get(labelName);
			node.setResponses(destination.getResponses());
		} else if (needFutureLabel.containsKey(labelName)) {
			needFutureLabel.get(labelName).add(node);
		} else {
			List<DialogNode> list = new ArrayList<>();
			list.add(node);
			needFutureLabel.put(labelName, list);
		}
	}
	
	private void setupRandomChance(DialogNode node, String percent) {
		node.setRandChance(Double.valueOf(percent) / 100);
	}
	
	private void setupNewLabel(DialogNode node, String labelName) {
		if (labels.containsKey(labelName))
			throw new DialogParseException(rawLine, currentLine, String.format("Label %s already exists", labelName));
		labels.put(labelName, node);
		if (needFutureLabel.containsKey(labelName)) {
			for (DialogNode needsLabel : needFutureLabel.get(labelName)) {
				needsLabel.setResponses(node.getResponses());
			}
		}
	}
	
	private void setupNewCondition(DialogNode node, String token) {
		Comparison condition = buildComparison(token);
		if (condition == null)
			throw new DialogParseException(rawLine, currentLine, "Invalid dialog condition: " + token);
		node.addCondition(condition);
	}
	
	private void setupNewAction(DialogNode node, String token) {
		Comparison action = buildComparison(token);
		if (action == null)
			throw new DialogParseException(rawLine, currentLine, "Invalid dialog action: " + token);
		node.addAction(action);
	}
	
	private Comparison buildComparison(String token) {
		Matcher matcher = EQUATION.matcher(token);
		if (!matcher.matches())
			return null;
		try {
			return new Comparison(matcher.group(1), matcher.group(2), matcher.group(3), currentLine);
		} catch (IllegalArgumentException e) {
			throw new DialogParseException(rawLine, currentLine, "Invalid comparison operator: " + token);
		}
	}

}
