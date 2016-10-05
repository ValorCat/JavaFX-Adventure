package ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

public class RightPanel {
	
	private static final int WIDTH = 200;
	private static final int HEIGHT = 585;
	
	private CharacterTracker chars;
	private LoreTracker lore;
	private Journal journal;
	private RightPanelObject current;
	
	public RightPanel() {
		this.chars = new CharacterTracker();
		this.lore = new LoreTracker();
		this.journal = new Journal();
		this.current = chars;
		current.getNode().setVisible(true);
	}

	public CharacterTracker getCharPanel() {
		return chars;
	}

	public LoreTracker getLorePanel() {
		return lore;
	}
	
	public Journal getJournal() {
		return journal;
	}
	
	public void setPanel(RightPanelObject panel) {
		current.getNode().setVisible(false);
		panel.getNode().setVisible(true);
		current = panel;
	}
	
	public static void setupPanelObject(ScrollPane scroller) {
		scroller.setMinWidth(WIDTH);
		scroller.setMinHeight(HEIGHT);
		//scroller.setMinSize(WIDTH, HEIGHT);
		//scroller.setMaxSize(WIDTH, HEIGHT);
		scroller.setHbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setVbarPolicy(ScrollBarPolicy.NEVER);
		scroller.setPannable(true);
		scroller.setVisible(false);
	}

}
