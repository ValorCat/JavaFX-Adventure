package main;

import java.util.ArrayList;
import java.util.List;

import chars.Party;
import chars.Player;
import chars.Skill;
import ui.DialogViewer;
import ui.InteractPrompt;
import ui.Inventory;
import world.Feature;
import world.InteractTarget;
import world.InteractTarget.Action;
import world.InteractTarget.ActionView;
import world.Area;
import world.Encounter;
import world.SkillCheckResult;

public class ActionHandler {
	
	private Player actor = null;
	
	public void partyTravel() {
		List<Feature> features = Boot.getGame().getParty().getCurrentRegion().getFeatures();
		List<InteractTarget> exits = new ArrayList<>();
		for (Feature feature : features) {
			List<String> actions = feature.getActionView().getActions();
			if (actions.contains(Action.ENTER) || actions.contains(Action.EXIT))
				exits.add(feature);
		}
		InteractPrompt prompt = new InteractPrompt("Move", exits);
		InteractTarget target = prompt.getTarget();
		if (target != null)
			doAction(target, Action.ENTER);
	}
	
	public void partyInspect() {
		List<InteractTarget> allTargets = new ArrayList<>();
		List<Area> scannedRegions = new ArrayList<>();
		for (Player player : Boot.getGame().getParty().getParty()) {
			Area region = player.getCurrentRegion();
			if (!scannedRegions.contains(region)) {
				allTargets.addAll(InteractPrompt.getTargetList(region));
				scannedRegions.add(region);
			}
		}
		InteractPrompt prompt = new InteractPrompt("Examine", allTargets);
		InteractTarget target = prompt.getTarget();
		if (target != null)
			doAction(target, Action.EXAMINE);
	}
	
	public void interact() {
		InteractPrompt prompt = new InteractPrompt("Interact", actor.getCurrentRegion());
		InteractTarget target = prompt.getTarget();
		String action = prompt.getAction();
		if (target != null && prompt != null)
			doAction(target, action);
	}
	
	public void openInventory() {
		new Inventory(actor);
	}
	
	public void openDialog(Encounter target) {
		new DialogViewer(target.getDialog());
	}
	
	public void travel(int newRegionID, int enterID) {
		Player activePlayer = Boot.getGame().getActionHandler().getActivePlayer();
		Area newRegion = Boot.getGame().getQuest().getRegion(newRegionID);
		Party party = Boot.getGame().getParty();
		if (activePlayer != null) {
			// one player travels
			if (activePlayer.isWithParty())
				activePlayer.leaveParty();
			activePlayer.moveTo(newRegion, enterID, true);
			if (newRegion == party.getCurrentRegion())
				activePlayer.rejoinParty();
			else if (party.isDisbanded()) {
				List<Player> playersInRegion = new ArrayList<>();
				for (Player player : party.getParty()) {
					if (player.getCurrentRegion() == newRegion)
						playersInRegion.add(player);
				}
				if (playersInRegion.size() > 1) {
					party.reform(newRegion);
					for (Player player : playersInRegion) {
						player.rejoinParty();
					}
				}
			}
		} else {
			// whole party travels
			for (Player player : party.getCurrentParty()) {
				player.moveTo(newRegion, enterID, false);
			}
			party.setCurrentRegion(newRegion);
			Boot.getUI().gameUpdate(newRegion.getEnterText(enterID));
			for (Player player : party.getParty()) {
				if (!player.isWithParty() && player.getCurrentRegion() == newRegion) {
					player.rejoinParty();
				}
			}
		}
	}
	
	private void doAction(InteractTarget target, String action) {
		ActionView<?> actionView = target.getActionView();
		switch (action) {
		case Action.BASH:
			actionView.onBash();
			break;
		case Action.ENTER: case Action.EXIT:
			actionView.onTravel();
			break;
		case Action.EXAMINE:
			actionView.onExamine();
			break;
		case Action.HIDE:
			actionView.onHide();
			break;
		case Action.LISTEN:
			actionView.onListen();
			break;
		case Action.OPEN:
			actionView.onOpen();
			break;
		case Action.SPEAK:
			actionView.onSpeak();
			break;
		case Action.TAKE:
			actionView.onTake();
			break;
		default:
			System.out.printf("Custom action '%s' on target '%s'\n", action, target.getName());
			actionView.onOtherAction(action);
			break;
		}
	}
	
	public void doSkillCheck(Skill skill, int randomFactor, SkillCheckResult effect) {
		int playerLevel = actor.getSkills().getLevel(skill);
		int random = (int) (Math.random() * (2 * randomFactor + 2)) - randomFactor;
		effect.performResult(playerLevel + random);
	}
	
	public Player getActivePlayer() {
		return actor;
	}
	
	public void setActivePlayer(Player player) {
		actor = player;
		if (player != null) {
			Boot.getUI().getActionBar().showPlayerBar();
		} else {
			Boot.getUI().getActionBar().showPartyBar();
		}
	}
	
}
