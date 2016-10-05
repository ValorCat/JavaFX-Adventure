package ui;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameLog {
	
	public final static int LINE_WRAP = 465;
	public final static Color COMBAT_MSG_COL = Color.CORAL;
	public final static Color DIALOG_MSG_COL = Color.CADETBLUE;
	public final static Color GAME_MSG_COL = Color.GREEN;
	public final static Color SYS_MSG_COL = Color.GOLDENROD;
	
	private final int WIDTH = 500;
	private final int HEIGHT = 600;
	
	private ListView<Text> gameLog;
	
	public GameLog() {
		this.gameLog = new ListView<>();
		gameLog.setMinWidth(WIDTH);
		gameLog.setMinHeight(HEIGHT);
		gameLog.setMaxWidth(WIDTH);
		gameLog.setMaxHeight(HEIGHT);
		gameLog.setFocusTraversable(false);
	}

	public ListView<Text> getContent() {
		return gameLog;
	}

	public Node getNode() {
		return gameLog;
	}

}
