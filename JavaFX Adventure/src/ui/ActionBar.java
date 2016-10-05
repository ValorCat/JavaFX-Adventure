package ui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Boot;
import world.InteractTarget;

public class ActionBar {
	
	private final int PARTY_BTN_SPACE = 8;
	private final int PLR_BTN_SPACE = 35;
	private final int PLR_ACT_BTN_SPACE = 8;
	private final int PLR_SPD_BOX_SPACE = 6;
	
	private final String DIVIDE_BTN = "Multitask";
	private final String INSPECT_BTN = InteractTarget.Action.EXAMINE;
	private final String ITEMS_BTN = "Inventory";
	private final String MOVE_BTN = "Move";
	private final String REST_BTN = "Rest";
	private final String SKILLS_BTN = "Skills";
	private final String TRADE_BTN = "Trade";
	private final String USE_BTN = "Interact";
	
	private final String ADJUST_SPEED = "Movement:";
	private final String STEALTH_MODE = "Sneak";
	private final String NORMAL_MODE = "Walk";
	private final String FAST_MODE = "Sprint";
	
	private HBox partyBar;
	private HBox plrBar;
	
	public ActionBar() {
		this.partyBar = setupPartyBar();
		this.plrBar = setupPlayerBar();
		showPartyBar();
	}
	
	private HBox setupPartyBar() {
		HBox partyBar = new HBox(PARTY_BTN_SPACE);
		partyBar.setAlignment(Pos.CENTER);
		partyBar.getChildren().add(setupPartyTravelButton());
		partyBar.getChildren().add(setupPartyInspectButton());
		partyBar.getChildren().add(new Button(DIVIDE_BTN));
		partyBar.getChildren().add(new Button(TRADE_BTN));
		partyBar.getChildren().add(new Button(REST_BTN));
		return partyBar;
	}
	
	private HBox setupPlayerBar() {
		HBox plrBar = new HBox(PLR_BTN_SPACE);
		HBox actionButtons = setupActionButtons();
		HBox moveButtons = setupSpeedControl();
		Button deselect = setupDeselectButton();
		
		plrBar.setAlignment(Pos.CENTER);
		plrBar.getChildren().add(actionButtons);
		plrBar.getChildren().add(moveButtons);
		plrBar.getChildren().add(deselect);
		return plrBar;
	}
	
	private Button setupPartyInspectButton() {
		Button button = new Button(INSPECT_BTN);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Boot.getGame().getActionHandler().partyInspect();
			}
		});
		return button;
	}
	
	private Button setupPartyTravelButton() {
		Button button = new Button(MOVE_BTN);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Boot.getGame().getActionHandler().partyTravel();
			}
		});
		return button;
	}
	
	private HBox setupActionButtons() {
		HBox actionButtons = new HBox(PLR_ACT_BTN_SPACE);
		Button useButton = new Button(USE_BTN);
		Button itemButton = new Button(ITEMS_BTN);
		actionButtons.getChildren().add(useButton);
		actionButtons.getChildren().add(itemButton);
		actionButtons.getChildren().add(new Button(SKILLS_BTN));
		useButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Boot.getGame().getActionHandler().interact();
			}
		});
		itemButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Boot.getGame().getActionHandler().openInventory();
			}
		});
		return actionButtons;
	}
	
	private HBox setupSpeedControl() {
		HBox speedControl = new HBox(PLR_SPD_BOX_SPACE);
		ChoiceBox<String> dropdown = new ChoiceBox<String>(FXCollections.observableArrayList(STEALTH_MODE, NORMAL_MODE, FAST_MODE));
		speedControl.setAlignment(Pos.BASELINE_CENTER);
		speedControl.getChildren().add(new Label(ADJUST_SPEED));
		speedControl.getChildren().add(dropdown);
		dropdown.setValue(NORMAL_MODE);
		return speedControl;
	}
	
	private Button setupDeselectButton() {
		Button deselect = new Button("×");
		deselect.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 15));
		deselect.setTextFill(Color.FIREBRICK);
		deselect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Boot.getGame().getActionHandler().setActivePlayer(null);
			}
		});
		return deselect;
	}
	
	public Node getPartyBar() {
		return partyBar;
	}
	
	public Node getPlayerBar() {
		return plrBar;
	}
	
	public void showPartyBar() {
		plrBar.setVisible(false);
		partyBar.setVisible(true);
	}
	
	public void showPlayerBar() {
		partyBar.setVisible(false);
		plrBar.setVisible(true);
	}
	
	public void disablePartyBar() {
		for (Node node : partyBar.getChildren()) {
			if (node instanceof Button) {
				((Button) node).setDisable(true);
			}
		}
	}
	
	public void enablePartyBar() {
		for (Node node : partyBar.getChildren()) {
			if (node instanceof Button) {
				((Button) node).setDisable(false);
			}
		}
	}

}
