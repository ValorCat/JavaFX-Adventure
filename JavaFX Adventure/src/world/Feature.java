package world;

public class Feature implements InteractTarget {
	
	private String name;
	private String[] baseStates;
	private String[] examinedStates;
	private ActionView<Feature> actions;
	private int state;
	
	public Feature(String name) {
		this.name = name;
		this.actions = new ActionView<>(this);
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
	public ActionView<Feature> getActionView() {
		return actions;
	}
	
	public Feature setBaseStates(String... states) {
		baseStates = states;
		return this;
	}
	
	public Feature setExaminedStates(String... states) {
		examinedStates = states;
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void setActionView(ActionView<? extends InteractTarget> actions) {
		this.actions = (ActionView<Feature>) actions;
	}
	
}
