package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class RightPanelSelector {

	private final int INTER_BUTTON_SPACE = 15;
	private final String CHAR_BTN = "Characters";
	private final String LORE_BTN = "Lore";
	private final String JRNL_BTN = "Journal";
	
	private HBox selector;
	private RightPanel rightPanel;
	private ToggleGroup group;
	
	public RightPanelSelector(RightPanel rightPanel) {
		this.selector = new HBox(INTER_BUTTON_SPACE);
		this.rightPanel = rightPanel;
		this.group = new ToggleGroup();
		selector.setAlignment(Pos.CENTER);
		selector.getChildren().addAll(
				createButton(CHAR_BTN, rightPanel.getCharPanel(), true),
				createButton(LORE_BTN, rightPanel.getLorePanel(), false),
				createButton(JRNL_BTN, rightPanel.getJournal(), false));
	}
	
	private ToggleButton createButton(String name, RightPanelObject panel, boolean initial) {
		ToggleButton button = new ToggleButton(name);
		button.setToggleGroup(group);
		button.setSelected(initial);
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				rightPanel.setPanel(panel);
			}
		});
		return button;
	}
	
	public Node getNode() {
		return selector;
	}
	
}
