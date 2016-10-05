package chars;

import item.EquippedItemManager;
import item.ItemManager;
import main.Boot;
import main.Game;
import util.IntScore;
import world.InteractTarget;
import world.Area;

public class Player implements InteractTarget {
	
	private String name;
	private IntScore health;
	private SkillSet skills;
	private ItemManager items;
	private EquippedItemManager gear;
	private Area region;
	private ActionView<Player> actionView;
	//private String appearance;
	private boolean withParty;
	
	public Player(String name, int health) {
		this.name = name;
		this.health = new IntScore(health);
		this.skills = new SkillSet();
		this.items = new ItemManager(30);
		this.gear = new EquippedItemManager();
		this.region = Game.START_REGION;
		this.withParty = true;
	}

	public void moveTo(Area newRegion, int enterID, boolean enterText) {
		region = newRegion;
		if (enterText)
			Boot.getUI().gameUpdate(region.getEnterText(enterID));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getBaseState() {
		return null;
	}

	@Override
	public String getExaminedState() {
		return null;
	}

	@Override
	public void setState(int state) {}

	public IntScore getHealth() {
		return health;
	}

	public SkillSet getSkills() {
		return skills;
	}

	public ItemManager getItems() {
		return items;
	}

	public EquippedItemManager getGear() {
		return gear;
	}
	
	public Area getCurrentRegion() {
		return region;
	}

	public ActionView<Player> getActionView() {
		return actionView;
	}

	@SuppressWarnings("unchecked")
	public void setActionView(ActionView<? extends InteractTarget> actionView) {
		try {
			this.actionView = (ActionView<Player>) actionView;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Expected type Player, got " + actionView.getClass().getTypeParameters()[0]);
		}
	}
	
	public boolean isWithParty() {
		return withParty;
	}
	
	public void rejoinParty() {
		withParty = true;
		Boot.getGame().getParty().addToCurrentParty(this);
		Boot.getUI().systemUpdate("%s has rejoined the party.", name);
	}
	
	public void leaveParty() {
		withParty = false;
		Party party = Boot.getGame().getParty();
		party.removeFromCurrentParty(this);
		if (party.getCurrentPartyCount() > 1) {
			Boot.getUI().systemUpdate("%s has left the party.", name);
		} else {
			party.disband();
		}
	}

}
