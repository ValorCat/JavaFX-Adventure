package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserInterface {
	
	private static final String WINDOW_TITLE = "Dungeon Troller";
	private static final int INIT_WIDTH = 950;
	private static final int INIT_HEIGHT = 750;
	
	private GridPane layout;
	private GameLog gameLog;
	private ActionBar actionBar;
	private RightPanel rightPanel;
	private RightPanelSelector pageSelector;
	private CharacterTracker charTracker;
	private LoreTracker loreTracker;
	private Journal journal;
	
	public UserInterface(Stage window) {
		this.layout = getLayout();
		this.gameLog = new GameLog();
		this.actionBar = new ActionBar();
		this.rightPanel = new RightPanel();
		this.pageSelector = new RightPanelSelector(rightPanel);
		this.charTracker = rightPanel.getCharPanel();
		this.loreTracker = rightPanel.getLorePanel();
		this.journal = rightPanel.getJournal();
		
		updateLayout();
		window.setScene(new Scene(layout, INIT_WIDTH, INIT_HEIGHT));
		window.setTitle(WINDOW_TITLE);
		window.show();
	}
	
	private GridPane getLayout() {
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(25);
		pane.setVgap(8);
		pane.setPadding(new Insets(25, 25, 25, 25));
		return pane;
	}
	
	private void updateLayout() {
		VBox leftSide = new VBox(15);
		StackPane actionBarArea = new StackPane();
		actionBarArea.getChildren().addAll(actionBar.getPartyBar(), actionBar.getPlayerBar());
		leftSide.getChildren().addAll(gameLog.getNode(), actionBarArea);
		layout.add(leftSide, 0, 1);
		layout.add(pageSelector.getNode(), 1, 0);
		layout.add(charTracker.getNode(), 1, 1);
		layout.add(loreTracker.getNode(), 1, 1);
		//layout.add(journal.getNode(), 1, 1);
	}

	public GameLog getGameLog() {
		return gameLog;
	}

	public ActionBar getActionBar() {
		return actionBar;
	}

	public CharacterTracker getCharTracker() {
		return charTracker;
	}

	public LoreTracker getLoreTracker() {
		return loreTracker;
	}

	public Journal getJournal() {
		return journal;
	}
	
	public void gameUpdate(String message, Object... params) {
		update(String.format(message, params), GameLog.GAME_MSG_COL);
	}
	
	public void systemUpdate(String message, Object... params) {
		update(String.format(message, params), GameLog.SYS_MSG_COL);
	}
	
	public void update(String message, Color color) {
		Text text = new Text(message);
		text.setWrappingWidth(GameLog.LINE_WRAP);
		text.setFill(color);
		gameLog.getContent().getItems().add(text);
	}

}
