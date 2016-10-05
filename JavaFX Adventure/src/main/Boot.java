package main;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.UserInterface;

public class Boot extends Application {
	
	private static Game game;
	private static UserInterface userInterface;
	private static Stage window;
	
	@Override
	public void start(Stage stage) throws Exception {
		window = stage;
		userInterface = new UserInterface(stage);
		game = new Game();
		game.runGame();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static Game getGame() {
		return game;
	}
	
	public static UserInterface getUI() {
		return userInterface;
	}
	
	public static Stage getWindow() {
		return window;
	}

}
