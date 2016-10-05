package dialog;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.text.Text;
import ui.DialogViewer;
import world.Encounter;

public class DialogTree {
	
	public static enum DialogExitMode { CONTINUE, CLOSE, CLOSE_AND_TRADE }
	
	public static char OPTION_DIVIDER = '.';
	
	private List<DialogNode> initialNPCOptions;
	private List<DialogNode> currentNPCOptions;
	private List<DialogNode> currentPCOptions;
	private DialogNode currentNPCNode;
	private Encounter npc;
	private DialogExitMode mode;
	
	public DialogTree(String fileName, Encounter npc) {
		DialogReader reader = new DialogReader(fileName);
		this.initialNPCOptions = reader.getDialogTree();
		this.currentNPCOptions = initialNPCOptions;
		this.currentPCOptions = new ArrayList<>();
		this.npc = npc;
		this.currentNPCNode = getNPCNode();
		this.mode = DialogExitMode.CONTINUE;
	}
	
	public void reload() {
		currentNPCOptions = initialNPCOptions;
		currentPCOptions.clear();
		currentNPCNode = getNPCNode();
		mode = DialogExitMode.CONTINUE;
	}
	
	public String getNPCText() {
		return currentNPCNode.getText();
	}
	
	public List<Text> getPCOptions() {
		currentPCOptions.clear();
		List<Text> options = new ArrayList<>();
		List<DialogNode> allPCOptions = currentNPCNode.getResponses();
		for (int i = 0; i < allPCOptions.size(); i++) {
			DialogNode node = allPCOptions.get(i);
			currentPCOptions.add(node);
			Text text = new Text(String.format("%d%s %s", i + 1, OPTION_DIVIDER, node.getText()));
			text.setWrappingWidth(DialogViewer.CONTENT_WIDTH - 20);
			options.add(text);
		}
		return options;
	}
	
	public void selectOption(int optionNum) {
		if (mode != DialogExitMode.CONTINUE)
			throw new IllegalStateException("Dialog tree traversal already complete");
		DialogNode selectedOption = currentPCOptions.get(optionNum - 1);
		currentNPCOptions = selectedOption.getResponses();
		if (currentNPCOptions.isEmpty()) {
			mode = selectedOption.willOpenTradeWindow() ? DialogExitMode.CLOSE_AND_TRADE : DialogExitMode.CLOSE;
		} else {
			currentNPCNode = getNPCNode();
		}
		doDialogActions(npc, selectedOption.getActions());
	}
	
	public DialogExitMode getExitMode() {
		return mode;
	}
	
	private DialogNode getNPCNode() {
		int size = currentNPCOptions.size(); 
		for (int i = 0; i < size; i++) {
			DialogNode node = currentNPCOptions.get(i);
			if (i < size - 1) {
				double chance = node.getRandChance();
				if ((chance != 1.0 && Math.random() > chance)
						|| !npcMeetsAllConditions(npc, node.getConditions())) {
					continue;
				}
			}
			doDialogActions(npc, node.getActions());
			return node;
		}
		return null;
	}
	
	private static boolean npcMeetsAllConditions(Encounter npc, List<Comparison> conditions) {
		for (Comparison condition : conditions) {
			String strCheckValue = condition.getValue();
			int checkValue = Character.isDigit(strCheckValue.charAt(0))
					? Integer.valueOf(strCheckValue)
					: npc.getAttribute(strCheckValue);
			int attrValue = npc.getAttribute(condition.getAttributeName());
			switch (condition.getOperator()) {
			case EQUAL:
				if (attrValue != checkValue)
					return false;
				break;
			case INEQUAL:
				if (attrValue == checkValue)
					return false;
				break;
			case GREATER:
				if (attrValue <= checkValue)
					return false;
				break;
			case LESSER:
				if (attrValue >= checkValue)
					return false;
				break;
			default:
				throw new DialogParseException(condition.getLineNumber(), "Invalid condition operator: " + condition.getOperator());
			}
		}
		return true;
	}
	
	private static void doDialogActions(Encounter npc, List<Comparison> actions) {
		for (Comparison action : actions) {
			String strSetValue = action.getValue();
			int setValue = Character.isDigit(strSetValue.charAt(0))
					? Integer.valueOf(strSetValue)
					: npc.getAttribute(strSetValue);
			int newValue = npc.getAttribute(action.getAttributeName());
			switch (action.getOperator()) {
			case EQUAL:
				newValue = setValue;
				break;
			case ADD:
				newValue += setValue;
				break;
			case SUBTRACT:
				newValue -= setValue;
				break;
			case MULTIPLY:
				newValue *= setValue;
				break;
			case DIVIDE:
				newValue /= setValue;
				break;
			case POWER:
				newValue = (int) Math.pow(newValue, setValue);
				break;
			default:
				throw new DialogParseException(action.getLineNumber(), "Invalid action operator: " + action.getOperator());
			}
			npc.setAttribute(action.getAttributeName(), newValue);
		}
	}
	
}
