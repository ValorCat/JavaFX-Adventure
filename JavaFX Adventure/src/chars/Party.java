package chars;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Boot;
import main.Game;
import ui.CharacterTracker;
import world.Area;

public class Party {
	
	private List<Player> party;
	private List<Player> currentlyInParty;
	private CharacterTracker ui;
	private Area currentRegion;
	
	public Party(CharacterTracker ui, Player... initParty) {
		List<Player> initPartyList = Arrays.asList(initParty);
		this.party = new ArrayList<>(initPartyList);
		this.currentlyInParty = new ArrayList<> (initPartyList);
		this.ui = ui;
		this.currentRegion = Game.START_REGION;
		String[] names = new String[initParty.length];
		for (int i = 0; i < names.length; i++) {
			names[i] = initParty[i].getName();
		}
		ui.updateTracker(names);
	}
	
	public Iterable<Player> getParty() {
		return party;
	}
	
	public Iterable<Player> getCurrentParty() {
		return currentlyInParty;
	}
	
	public int getCurrentPartyCount() {
		return currentlyInParty.size();
	}
	
	public void addMember(Player newMember) {
		party.add(newMember);
		ui.addCharacter(newMember.getName());
	}
	
	public void addToCurrentParty(Player player) {
		if (!currentlyInParty.contains(player))
			currentlyInParty.add(player);
	}
	
	public void removeFromCurrentParty(Player player) {
		currentlyInParty.remove(player);
	}
	
	public void disband() {
		currentlyInParty.clear();
		currentRegion = null;
		Boot.getUI().getActionBar().disablePartyBar();
		Boot.getUI().systemUpdate("The party has disbanded.");
	}
	
	public void reform(Area newRegion) {
		currentRegion = newRegion;
		Boot.getUI().getActionBar().enablePartyBar();
	}
	
	public Area getCurrentRegion() {
		return currentRegion;
	}
	
	public void setCurrentRegion(Area newRegion) {
		currentRegion = newRegion;
	}
	
	public boolean isDisbanded() {
		return currentlyInParty.size() == 0;
	}

}
