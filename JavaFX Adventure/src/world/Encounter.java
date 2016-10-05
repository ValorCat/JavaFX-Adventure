package world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dialog.DialogTree;

public class Encounter implements InteractTarget {
	
	public static enum Trigger { PLR_LISTEN }
	
	private static final int DEFAULT_ATTR = 0;
	
	private String name;
	private String[] baseStates;
	private String[] examinedStates;
	private ActionView<Encounter> actions;
	private Map<String, Integer> attributes;
	private List<Trigger> triggers;
	private DialogTree dialog;
	private int state;
	
	public Encounter(String name, String dialogFileName) {
		this.name = name;
		this.actions = new ActionView<>(this);
		this.attributes = new HashMap<>();
		this.triggers = new ArrayList<>();
		this.dialog = new DialogTree(dialogFileName, this);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getBaseState() {
		return baseStates[state];
	}

	@Override
	public String getExaminedState() {
		return examinedStates[state];
	}

	@Override
	public void setState(int state) {
		this.state = state;
	}

	@Override
	public ActionView<Encounter> getActionView() {
		return actions;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setActionView(ActionView<? extends InteractTarget> actions) {
		this.actions = (ActionView<Encounter>) actions;
	}
	
	public int getAttribute(String name) {
		return attributes.getOrDefault(name, DEFAULT_ATTR);
	}
	
	public void setAttribute(String name, int value) {
		attributes.put(name, value);
	}
	
	public void addAttribute(String name, int value) {
		attributes.put(name, value);
	}
	
	public void addTrigger(Trigger... triggers) {
		this.triggers.addAll(Arrays.asList(triggers));
	}
	
	public boolean hasTrigger(Trigger trigger) {
		return triggers.contains(trigger);
	}
	
	public DialogTree getDialog() {
		return dialog;
	}
	
	public Encounter setBaseStates(String... states) {
		baseStates = states;
		return this;
	}
	
	public Encounter setExaminedStates(String... states) {
		examinedStates = states;
		return this;
	}
	
}
