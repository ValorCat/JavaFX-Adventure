package ui;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

public class Journal implements RightPanelObject {
	
	private static final String JOURNAL_PROMPT = "Enter your notes here...";
	
	private TextArea text;
	private ScrollPane scroller;
	
	public Journal() {
		this.text = new TextArea();
		this.scroller = new ScrollPane(text);
		text.setPromptText(JOURNAL_PROMPT);
		text.setWrapText(true);
		RightPanel.setupPanelObject(scroller);
		scroller.setPannable(false);
	}
	
	public Node getNode() {
		return scroller;
	}

}
