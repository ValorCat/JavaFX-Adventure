package ui;

import chars.Player;
import item.Equippable;
import item.InvSlot;
import item.Item;
import item.ItemType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.StringUtil;

public class Inventory {
	
	private static final int
			WIDTH = 650, 
			HEIGHT = 500,
			ITEM_PANE_WIDTH = 260,
			ITEM_PANE_HEIGHT = 420,
			DETAIL_PANE_WIDTH = 315,
			DETAIL_PANE_HEIGHT = 330,
			WEIGHT_BAR_WIDTH = 200,
			WEIGHT_BAR_HEIGHT = 18,
			CENTER_SPACE_WIDTH = 25,
			SPACE_ABOVE_WEIGHT = 25,
			SPACE_BELOW_TABS = 10,
			SPACE_BETWEEN_TABS = 7,
			TAB_FONT_SIZE = 10;
	private static final String TITLE = "%s's Inventory";
	
	private static Stage window = null;
	
	private GridPane layout;
	private VBox leftSide;
	private VBox rightSide;
	private ToggleGroup itemTabGroup;
	private HBox itemTabs;
	private FilteredList<InvSlot> itemList;
	private ListView<InvSlot> itemPane;
	private ScrollPane detailPane;
	private HBox itemButtons;
	private HBox weightBar;
	
	private Button equipButton, unequipButton, dropButton, appraiseButton;
	
	private Player player;
	private InvSlot selectedItem = null;
	
	public Inventory(Player player) {
		if (window != null)
			window.hide();
		window = new Stage();
		
		this.player = player;
		this.leftSide = new VBox(SPACE_BELOW_TABS);
		this.rightSide = new VBox(SPACE_ABOVE_WEIGHT);
		this.itemTabGroup = new ToggleGroup();
		this.itemTabs = getItemTabs();
		this.itemList = getItemList();
		this.itemPane = getItemPane();
		this.detailPane = getDetailPane();
		this.itemButtons = getItemButtons();
		this.weightBar = getWeightBar();
		this.layout = getLayout();
		
		setupWindow();
		window.showAndWait();
	}

	private HBox getItemTabs() {
		HBox tabs = new HBox(SPACE_BETWEEN_TABS);
		tabs.setAlignment(Pos.CENTER);
		tabs.getChildren().addAll(
				createAllTab(), createEquippedTab(), createGearTab(), createWealthTab(), createOtherTab());
		return tabs;
	}
	
	private ToggleButton createAllTab() {
		ToggleButton button = new ToggleButton("All");
		button.setFont(Font.font(TAB_FONT_SIZE));
		button.setToggleGroup(itemTabGroup);
		button.setSelected(true);
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				itemList.setPredicate(x -> true);
			}
		});
		return button;
	}
	
	private ToggleButton createEquippedTab() {
		ToggleButton button = new ToggleButton("Equipped");
		button.setFont(Font.font(TAB_FONT_SIZE));
		button.setToggleGroup(itemTabGroup);
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				itemList.setPredicate(x -> (x.getItem() instanceof Equippable) && ((Equippable) x.getItem()).isEquipped());
			}
		});
		return button;
	}
	
	private ToggleButton createGearTab() {
		ToggleButton button = new ToggleButton("Gear");
		button.setFont(Font.font(TAB_FONT_SIZE));
		button.setToggleGroup(itemTabGroup);
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				itemList.setPredicate(x -> x.getItem().getType() == ItemType.GEAR);
			}
		});
		return button;
	}
	
	private ToggleButton createWealthTab() {
		ToggleButton button = new ToggleButton("Wealth");
		button.setFont(Font.font(TAB_FONT_SIZE));
		button.setToggleGroup(itemTabGroup);
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				itemList.setPredicate(x -> x.getItem().getType() == ItemType.WEALTH);
			}
		});
		return button;
	}
	
	private ToggleButton createOtherTab() {
		ToggleButton button = new ToggleButton("Other");
		button.setFont(Font.font(TAB_FONT_SIZE));
		button.setToggleGroup(itemTabGroup);
		button.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				itemList.setPredicate(x -> x.getItem().getType() == ItemType.OTHER);
			}
		});
		return button;
	}
	
	private FilteredList<InvSlot> getItemList() {
		return new FilteredList<InvSlot>(FXCollections.observableArrayList(player.getItems().getItems()));
	}
	
	private ListView<InvSlot> getItemPane() {
		ListView<InvSlot> view = new ListView<>(itemList);
		view.setMinSize(ITEM_PANE_WIDTH, ITEM_PANE_HEIGHT);
		view.setMaxSize(ITEM_PANE_WIDTH, ITEM_PANE_HEIGHT);
		view.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				String itemName = StringUtil.getRowFromListView(event.getTarget());
				for (InvSlot slot : itemList) {
					if (slot.getDisplayName().equals(itemName)) {
						onItemSelect(slot);
						break;
					}
				}
			}
		});
		return view;
	}
	
	private ScrollPane getDetailPane() {
		Text text = new Text("");
		ScrollPane scroller = new ScrollPane(text);
		scroller.setMinSize(DETAIL_PANE_WIDTH, DETAIL_PANE_HEIGHT);
		scroller.setMaxSize(DETAIL_PANE_WIDTH, DETAIL_PANE_HEIGHT);
		return scroller;
	}
	
	public HBox getItemButtons() {
		HBox row = new HBox(20);
		equipButton = new Button("Equip");
		unequipButton = new Button("Unequip");
		dropButton = new Button("Drop");
		appraiseButton = new Button("Appraise");
		row.setMinHeight(30);
		row.setAlignment(Pos.CENTER);
		/*row.getChildren().addAll(useButton, equipButton, unequipButton, dropButton, appraiseButton);
		setButtonStatus(useButton, false);
		setButtonStatus(equipButton, false);
		setButtonStatus(unequipButton, false);
		setButtonStatus(dropButton, false);
		setButtonStatus(appraiseButton, false);*/
		return row;
	}
	
	private void setButtonStatus(Button button, boolean enabled) {
		ObservableList<Node> buttons = itemButtons.getChildren();
		if (!enabled) {
			buttons.remove(button);
		} else if (!buttons.contains(button)) {
			buttons.add(button);
		}
	}
	
	private HBox getWeightBar() {
		HBox tracker = new HBox(5);
		ProgressBar bar = new ProgressBar(player.getItems().getWeightPercent());
		bar.setMinSize(WEIGHT_BAR_WIDTH, WEIGHT_BAR_HEIGHT);
		bar.setMaxSize(WEIGHT_BAR_WIDTH, WEIGHT_BAR_HEIGHT);
		tracker.setAlignment(Pos.CENTER);
		tracker.getChildren().add(new Label("Carrying Capacity:"));
		tracker.getChildren().add(bar);
		return tracker;
	}
	
	private void onItemSelect(InvSlot slot) {
		selectedItem = slot;
		((Text) detailPane.getContent()).setText(slot.getItem().getName());
		Item item = selectedItem.getItem();
		itemButtons.getChildren().clear();
		if (item instanceof Equippable) {
			if (((Equippable) item).isEquipped())
				setButtonStatus(unequipButton, true);
			else
				setButtonStatus(equipButton, true);
		}
		setButtonStatus(dropButton, true);
		setButtonStatus(appraiseButton, true);
	}
	
	private GridPane getLayout() {
		GridPane layout = new GridPane();
		layout.setAlignment(Pos.CENTER);
		layout.setHgap(CENTER_SPACE_WIDTH);
		layout.add(leftSide, 0, 0);
		layout.add(rightSide, 1, 0);
		leftSide.setAlignment(Pos.TOP_RIGHT);
		leftSide.getChildren().addAll(itemTabs, itemPane);
		rightSide.setAlignment(Pos.TOP_LEFT);
		rightSide.getChildren().addAll(detailPane, itemButtons, weightBar);
		return layout;
	}

	private void setupWindow() {
		window.setScene(new Scene(layout, WIDTH, HEIGHT));
		window.setTitle(String.format(TITLE, player.getName()));
		window.setResizable(false);
		window.setOnHidden(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				window = null;
			}
		});
	}

}
