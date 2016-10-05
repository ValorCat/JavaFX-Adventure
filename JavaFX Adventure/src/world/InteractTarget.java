package world;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import main.Boot;

public interface InteractTarget {
	
	public static class Action {
		
		public static final String
				BASH = "Bash",
				ENTER = "Enter",
				EXAMINE = "Examine",
				EXIT = "Exit",
				HIDE = "Hide",
				LISTEN = "Listen",
				OPEN = "Open",
				SPEAK = "Speak",
				TAKE = "Take";
		
	}
	
	public static class ActionView<T extends InteractTarget> {
		
		private List<String> actions;
		protected T target;
		
		public ActionView(T target, String... actions) {
			this.target = target;
			this.actions = new ArrayList<>(Arrays.asList(actions));
			enableActions(Action.EXAMINE);
			target.setActionView(this);
		}
		
		public List<String> getActions() {
			return actions;
		}
		
		public void enableActions(String... actionArr) {
			actions.addAll(Arrays.asList(actionArr));
			Collections.sort(actions);
		}
		
		public void disableActions(String... actionArr) {
			actions.removeAll(Arrays.asList(actionArr));
		}
		
		public void onExamine() {
			Boot.getUI().gameUpdate(target.getExaminedState());
		}
		
		public void onSpeak() {
			Boot.getGame().getActionHandler().openDialog((Encounter) target);
		}
		
		public void onBash() {}
		public void onHide() {}
		public void onListen() {}
		public void onOpen() {}
		public void onTake() {}
		public void onTravel() {}
		
		public void onOtherAction(String action) {}
		
	}
	
	String getName();
	String getBaseState();
	String getExaminedState();
	void setState(int state);
	ActionView<? extends InteractTarget> getActionView();
	void setActionView(ActionView<? extends InteractTarget> actions);

}
