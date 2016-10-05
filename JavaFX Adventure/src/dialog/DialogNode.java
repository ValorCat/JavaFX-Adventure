package dialog;

import java.util.ArrayList;
import java.util.List;

public class DialogNode {
	
	public static final String PLAYER_SPEAKER = "<Player>";
	public static final String NPC_SPEAKER = "<MainNPC>";
	
	private String text;
	private String speakerName;
	private List<DialogNode> responses;
	private List<Comparison> conditions;
	private List<Comparison> actions;
	private double randChance;
	private boolean opensTradeWindow;
	
	public DialogNode() {
		this.text = "";
		this.speakerName = PLAYER_SPEAKER;
		this.responses = new ArrayList<>();
		this.conditions = new ArrayList<>();
		this.actions = new ArrayList<>();
		this.randChance = 1.0;
		this.setOpensTradeWindow(false);
	}

	public String getText() {
		return text;
	}

	public String getSpeakerName() {
		return speakerName;
	}

	public List<DialogNode> getResponses() {
		return responses;
	}

	public List<Comparison> getConditions() {
		return conditions;
	}

	public List<Comparison> getActions() {
		return actions;
	}
	
	public double getRandChance() {
		return randChance;
	}
	
	public boolean willOpenTradeWindow() {
		return opensTradeWindow;
	}

	public void setResponses(List<DialogNode> responses) {
		this.responses = responses;
	}
	
	public void addResponse(DialogNode response) {
		this.responses.add(response);
	}

	public void addCondition(Comparison condition) {
		this.conditions.add(condition);
	}

	public void addAction(Comparison action) {
		this.actions.add(action);
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void setSpeakerName(String speakerName) {
		this.speakerName = speakerName;
	}

	public void setRandChance(double randChance) {
		this.randChance = randChance;
	}

	public void setOpensTradeWindow(boolean opensTradeWindow) {
		this.opensTradeWindow = opensTradeWindow;
	}

	public String toString() {
		return text.trim();
	}

}
