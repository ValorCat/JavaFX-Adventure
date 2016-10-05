package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import chars.Player;
import main.Boot;
import world.Encounter;
import world.Feature;
import world.InteractTarget;
import world.Area;

public class InteractPrompt {
	
	private static final int
			WIDTH = 260,
			HEIGHT = 130,
			HORIZ_SPACING = 12,
			VERT_SPACING = 18,
			BTN_WIDTH = 120;
	
	private static Stage window = null;
	
	private GridPane layout;
	private ChoiceBox<InteractTarget> targetBox;
	private ChoiceBox<String> actionBox;
	private InteractTarget target = null;
	private String action = null;
	
	public InteractPrompt(String title, Area region) {
		if (window != null)
			window.hide();
		window = new Stage();
		
		this.targetBox = getTargetBox(getTargetList(region));
		this.actionBox = getActionBox();
		this.layout = getLayout();
		
		setupWindow(title);
		window.showAndWait();
	}
	
	public InteractPrompt(String title, List<InteractTarget> targetsToList) {
		if (window != null)
			window.hide();
		window = new Stage();
		
		this.targetBox = getTargetBox(FXCollections.observableArrayList(targetsToList));
		this.actionBox = null;
		this.layout = getLayout();
		
		setupWindow(title);
		window.showAndWait();
	}
	
	private ChoiceBox<InteractTarget> getTargetBox(ObservableList<InteractTarget> targets) {
		ChoiceBox<InteractTarget> targetBox = new ChoiceBox<>(targets);
		targetBox.setMinWidth(BTN_WIDTH);
		targetBox.setMaxWidth(BTN_WIDTH);
		targetBox.setConverter(new StringConverter<InteractTarget>() {
			@Override public String toString(InteractTarget object) { return object.getName(); }
			@Override public InteractTarget fromString(String string) { return null; }
		});
		targetBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				target = targetBox.getValue();
				if (actionBox != null) {
					actionBox.setItems(getActionList(target));
					actionBox.setDisable(false);
				} else {
					window.hide();
				}
			}
		});
		return targetBox;
	}
	
	private ChoiceBox<String> getActionBox() {
		ChoiceBox<String> actionBox = new ChoiceBox<>();
		actionBox.setMinWidth(BTN_WIDTH);
		actionBox.setMaxWidth(BTN_WIDTH);
		actionBox.setDisable(true);
		actionBox.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				action = actionBox.getValue();
				window.hide();
			}
		});
		return actionBox;
	}
	
	private GridPane getLayout() {
		GridPane layout = new GridPane();
		layout.setAlignment(Pos.CENTER);
		layout.setHgap(HORIZ_SPACING);
		layout.setVgap(VERT_SPACING);
		layout.add(new Label("Target:"), 0, 0);
		layout.add(targetBox, 1, 0);
		if (actionBox != null) {
			layout.add(new Label("Action:"), 0, 1);
			layout.add(actionBox, 1, 1);
		}
		return layout;
	}
	
	private void setupWindow(String title) {
		window.setScene(new Scene(layout, WIDTH, HEIGHT));
		window.setTitle(title);
		window.setResizable(false);
		window.setOnHidden(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				window = null;
			}
		});
	}
	
	public static ObservableList<InteractTarget> getTargetList(Area region) {
		List<InteractTarget> objects = new ArrayList<>();
		for (Feature feature : region.getFeatures()) {
			objects.add(feature);
		}
		for (Encounter encounter : region.getEncounters()) {
			objects.add(encounter);
		}
		for (Player player : Boot.getGame().getParty().getParty()) {
			if (player.getCurrentRegion() == region) {
				objects.add(player);
			}
		}
		Collections.sort(objects, (a, b) -> a.getName().compareTo(b.getName()));
		return FXCollections.observableArrayList(objects);
	}
	
	public static ObservableList<String> getActionList(InteractTarget target) {
		return FXCollections.observableArrayList(target.getActionView().getActions());
	}
	
	public InteractTarget getTarget() {
		return target;
	}
	
	public String getAction() {
		return action;
	}

}