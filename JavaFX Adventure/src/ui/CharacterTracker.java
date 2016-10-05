package ui;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import chars.Player;
import main.Boot;
import util.StringUtil;

public class CharacterTracker implements RightPanelObject {

	private ListView<String> tracker;
	private String[] items;
	private ScrollPane scroller;
	
	public CharacterTracker() {
		this.scroller = new ScrollPane(tracker);
		RightPanel.setupPanelObject(scroller);
		updateTracker(new String[] {});
	}
	
	public Node getNode() {
		return scroller;
	}
	
	public void updateTracker(String[] newTracker) {
		items = newTracker;
		tracker = new ListView<String>(FXCollections.observableArrayList(newTracker));
		tracker.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String name = StringUtil.getRowFromListView(event.getTarget());
				for (Player player : Boot.getGame().getParty().getParty()) {
					if (player.getName().equals(name))
						Boot.getGame().getActionHandler().setActivePlayer(player);
				}
			}
		});
		scroller.setContent(tracker);
	}
	
	public void addCharacter(String name) {
		String[] newList = new String[items.length + 1];
		int pos = 0;
		boolean added = false;
		for (String item : items) {
			if (!added && name.compareTo(item) < 0) {
				newList[pos] = name;
				newList[pos + 1] = item;
				pos += 2;
				added = true;
			} else {
				newList[pos] = item;
				pos++;
			}
		}
		updateTracker(newList);
	}
	
}