package main;

import chars.Player;
import chars.Skill;
import chars.Party;
import item.Apparel;
import item.EquipSlot;
import item.Equippable;
import item.Item;
import item.ItemType;
import item.Weapon;
import item.Weapon.WeaponClass;
import item.Weapon.WeaponFlag;
import world.*;
import world.InteractTarget.Action;
import world.InteractTarget.ActionView;
import world.SkillCheckResult.CheckAction;

public class Game {
	
	public static final Area START_REGION = new Area();
	
	private Party party;
	private Adventure quest;
	private ActionHandler handler = new ActionHandler();
	
	public void runGame() {
		setupQuest();
		setupParty();
		Boot.getUI().gameUpdate(quest.getRegion(0).getEnterText(0));
	}
	
	public Party getParty() {
		return party;
	}
	
	public Adventure getQuest() {
		return quest;
	}
	
	public ActionHandler getActionHandler() {
		return handler;
	}
	
	private void setupParty() {
		Player jozan = new Player("Jozan", 10);
		Player lorath = new Player("Lorath", 7);
		Player talion = new Player("Talion", 12);
		Item mace = new Weapon("Light Mace", 20, 4.5f)
				.setClasses(WeaponClass.CLUB, WeaponClass.LIGHT, WeaponClass.MELEE)
				.setFlags(WeaponFlag.LIGHT)
				.setOneHandDamage(1, 5)
				.setTwoHandDamage(2, 6);
		Item dagger = new Weapon("Dagger", 3, 0.5f)
				.setClasses(WeaponClass.DAGGER, WeaponClass.LIGHT, WeaponClass.MELEE)
				.setCritChance(0.1f)
				.setDamage(1, 5)
				.setFlags(WeaponFlag.LIGHT, WeaponFlag.THROWABLE)
				.setSkills(true, true, false)
				.setThrownRange(10, 40);
		Item longsword = new Weapon("Longsword", 25, 5f)
				.setClasses(WeaponClass.LIGHT, WeaponClass.MELEE, WeaponClass.SWORD)
				.setOneHandDamage(1, 8)
				.setSkills(true, false, true)
				.setStrPrereq(2)
				.setTwoHandDamage(2, 10);
		Item hood = new Apparel("Leather Hood", 1, 1.5f)
				.setValidSlots(new EquipSlot[] {EquipSlot.HEAD});
		
		jozan.getItems().addItem(mace);
		lorath.getItems().addItem(dagger);
		talion.getItems().addItem(longsword);
		talion.getItems().addItem(hood);
		talion.getItems().addItem(new Item("Key", 0, 0, ItemType.OTHER));
		
		jozan.getGear().equipItem(EquipSlot.MAIN_HAND, (Equippable) mace);
		lorath.getGear().equipItem(EquipSlot.MAIN_HAND, (Equippable) dagger);
		talion.getGear().equipItem(EquipSlot.HEAD, (Equippable) hood);
		talion.getGear().equipItem(EquipSlot.MAIN_HAND, (Equippable) longsword);
		
		party = new Party(Boot.getUI().getCharTracker(), jozan, lorath, talion);
	}
	
	private void setupQuest() {
		quest = new Adventure();
		buildCaveEntrance();
		buildCratesRoom();
	}
	
	private void buildCaveEntrance() {
		Area room = START_REGION;
		Feature caveOpening = new Feature("Cave Entrance")
				.setExaminedStates("This is the opening through which you first entered. Sunlights shines through partially illuminates the cavern.");
		Feature darkPassage = new Feature("Dark Passage")
				.setExaminedStates("This unlit crevice in the cave wall extends no more than 15 feet before turning sharply to the left. Beyond, the ground gently slopes downward into the dark.");
		Feature largeRocks = new Feature("Large Boulders")
				.setBaseStates("Several large, mossy boulders stand in the center of the chamber.")
				.setExaminedStates("Several 10 foot columns of gray stone rise from the floor between the two passages. The rocks are cracked and covered in moss and ivy. Standing behind them, they completely block your view of the dark passage.");
		Encounter merchant = new Encounter("Merchant", "test")
				.setBaseStates("An old man bearing a large sack stands in the center of the room.")
				.setExaminedStates("The man is hunched over the drawstring sack, his back to you.");
		Entrance fromCaveOpening = new Entrance(
				"You squeeze through the narrow cave until it widens into a small cavern.",
				"A dark passage in the far wall leads onwards.");
		Entrance fromDarkPassage = new Entrance(
				"The crevice slopes upwards and opens into a small cavern.",
				"Daylight streams through the cave entrance in the far wall.");
		new ActionView<Feature>(caveOpening, Action.EXIT) {
			public void onTravel() {
				Boot.getUI().systemUpdate("You can't leave yet! Return when your mission is complete.");
			}};
		new ActionView<Feature>(darkPassage, Action.ENTER, Action.HIDE, Action.LISTEN) {
			public void onListen() {
				// perception check
				// on success, language check
			}
			public void onTravel() {
				handler.travel(1, 0);
			}};
		new ActionView<Encounter>(merchant, Action.LISTEN, Action.SPEAK) {
			public void onListen() {
				Boot.getUI().gameUpdate("The man mutters to himself under his breath unintelligibly while sifting through the sack.");
			}};
		room.setBaseDescriptor("%s %s", largeRocks, merchant);
		room.addFeatures(caveOpening, darkPassage, largeRocks);
		room.addEncounters(merchant);
		room.addEntrances(fromCaveOpening, fromDarkPassage);
		quest.addRegion(room);
	}
	
	private void buildCratesRoom() {
		Feature narrowCrevice = new Feature("Narrow Crevice")
				.setExaminedStates("The opening appears to be an immense crack in the stone wall. Inside, the ground slopes up and the walls edge to the right.");
		Feature torch = new Feature("Torch")
				.setBaseStates("A mounted torch on the wall provides light.")
				.setExaminedStates("A single, burning torch rests in an iron sconce on the wall.");
		Feature woodCrates = new Feature("Wood Crates")
				.setBaseStates(
						"Several wooden crates lie in a corner.",
						"Several destroyed wooden crates lie in a pile in a corner.")
				.setExaminedStates(
						"Four wooden boxes, perhaps eight cubic feet each, rest in a corner of the room. Their lids appear to be sealed tightly.",
						"Several wooden boxes rest in pieces in a corner of the room.");
		Feature woodDoor = new Feature("Wooden Door")
				.setBaseStates("This thin wooden door looks out of place in the middle of the stone brick wall.");
		Entrance fromCrevice = new Entrance(
				"The dark passage leads to a narrow crevice in the wall of a small, barely-lit chamber. The floor and walls are built of aging stone bricks.",
				"A worn wooden door stands in the right wall.");
		Entrance fromDoor = new Entrance(
				"<!>",
				"A narrow crevice opens in the unworked left wall.");
		new ActionView<Feature>(narrowCrevice, Action.ENTER, Action.HIDE, Action.LISTEN) {
			public void onHide() {
				// add torch to equipment set offhand, check state to determine if lit or unlit
			}
			public void onListen() {
				// perception check
			}
			public void onTravel() {
				handler.travel(0, 1);
			}};
		new ActionView<Feature>(torch, "Extinguish", Action.TAKE) {
			public void onTake() {
				// add torch to equipment set offhand, check state to determine if lit or unlit
			}
			public void onOtherAction(String action) {
				if (action.equals("Extinguish")) {
					// change description, update light level
				}
			}};
		new ActionView<Feature>(woodCrates, Action.BASH, Action.OPEN) {
			public void onBash() {
				// strength check
				SkillCheckResult result = new SkillCheckResult()
						.addMinResult("The crates survive completely undamaged.", 0)
						.addResult(1, "You smash through the flimsy wood.", 0, 1)
						.addAction(new CheckAction() {
							public void execute() {
								// sound event
							}})
						.addAction(new CheckAction() {
							public void execute() {
								target.setState(1);
							}});
				Boot.getGame().getActionHandler().doSkillCheck(Skill.STRENGTH, 1, result);
			}
			public void onOpen() {
				// strength check to open
			}};
		new ActionView<Feature>(woodDoor, Action.BASH, Action.LISTEN, Action.OPEN) {
			public void onBash() {
				// strength check
			}
			public void onListen() {
				// perception check
			}
			public void onOpen() {
				// ?
			}};
		Area room = new Area("%s %s", woodCrates, torch);
		room.addFeatures(narrowCrevice, torch, woodCrates, woodDoor);
		room.addEntrances(fromCrevice, fromDoor);
		quest.addRegion(room);
	}

}
