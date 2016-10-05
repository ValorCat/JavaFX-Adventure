package ui;

import dialog.DialogTree;
import dialog.DialogTree.DialogExitMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.StringUtil;

public class DialogViewer {
	
	private static final String TITLE = "Conversation";
	public static final int
			WIDTH = 400,
			HEIGHT = 400,
			VERTICAL_SPACING = 15,
			CONTENT_WIDTH = 360,
			NPC_TEXT_HEIGHT = 170,
			PC_OPTIONS_HEIGHT = 170;

	private static Stage window = null;
	
	private GridPane layout;
	private ScrollPane npcText;
	private ListView<Text> pcOptions;
	private Separator breakLine;
	
	private DialogTree tree;
	
	public DialogViewer(DialogTree tree) {
		if (window != null)
			window.hide();
		window = new Stage();
		
		this.tree = tree;
		this.npcText = getNPCText();
		this.pcOptions = getPCOptions();
		this.breakLine = getSeparator();
		this.layout = getLayout();
		
		setupWindow();
		window.showAndWait();
	}
	
	private ScrollPane getNPCText() {
		Text text = new Text(tree.getNPCText());
		ScrollPane scroller = new ScrollPane(text);
		scroller.setMinSize(CONTENT_WIDTH, NPC_TEXT_HEIGHT);
		scroller.setMaxSize(CONTENT_WIDTH, NPC_TEXT_HEIGHT);
		scroller.setBackground(null);
		scroller.setFocusTraversable(false);
		scroller.setMouseTransparent(true);
		return scroller;
	}
	
	private ListView<Text> getPCOptions() {
		ListView<Text> view = new ListView<>(getPCOptionList());
		view.setMinSize(CONTENT_WIDTH, PC_OPTIONS_HEIGHT);
		view.setMaxSize(CONTENT_WIDTH, PC_OPTIONS_HEIGHT);
		view.setBackground(null);
		view.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String optionText = StringUtil.getRowFromListView(event.getTarget());
				if (optionText.equals("null"))
					return;
				int optionNum = StringUtil.getDialogOptionNumber(optionText);
				tree.selectOption(optionNum);
				if (tree.getExitMode() != DialogExitMode.CONTINUE) {
					window.close();
					tree.reload();
				} else {
					((Text) (npcText.getContent())).setText(tree.getNPCText());
					pcOptions.setItems(getPCOptionList());
				}
			}
		});
		return view;
	}
	
	private ObservableList<Text> getPCOptionList() {
		return FXCollections.observableArrayList(tree.getPCOptions());
	}
	
	private Separator getSeparator() {
		Separator line = new Separator();
		line.setMinWidth(CONTENT_WIDTH);
		line.setMaxWidth(CONTENT_WIDTH);
		return line;
	}
	
	private GridPane getLayout() {
		GridPane layout = new GridPane();
		layout.setAlignment(Pos.CENTER);
		layout.setVgap(VERTICAL_SPACING);
		layout.add(npcText, 0, 0);
		layout.add(breakLine, 0, 1);
		layout.add(pcOptions, 0, 2);
		return layout;
	}
	
	private void setupWindow() {
		window.setScene(new Scene(layout, WIDTH, HEIGHT));
		window.setTitle(TITLE);
		window.setResizable(false);
		window.setOnHidden(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				window = null;
			}
		});
	}

}
