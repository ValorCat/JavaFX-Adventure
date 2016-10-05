package ui;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;

public class LoreTracker implements RightPanelObject {
	
	private ListView<String> tracker;
	private ScrollPane scroller;
	
	public LoreTracker() {
		this.tracker = new ListView<>();
		this.scroller = new ScrollPane(tracker);
		RightPanel.setupPanelObject(scroller);
	}
	
	public Node getNode() {
		return scroller;
	}

}
