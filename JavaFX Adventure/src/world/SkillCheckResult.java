package world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Boot;

public class SkillCheckResult {
	
	private class Result {
		
		private int[] actionIDs;
		private String message;
		
		public Result(String message, int[] actionIDs) {
			this.actionIDs = actionIDs;
			this.message = message;
		}

		public void execute() {
			if (message != null)
				Boot.getUI().gameUpdate(message);
			for (int actionID : actionIDs)
				actions.get(actionID).execute();
		}
		
	}
	
	public static interface CheckAction {
		
		void execute();
		
	}
	
	public static final int MIN_SCORE = -10;
	
	private Map<Integer, Result> results;
	private List<CheckAction> actions;
	
	public SkillCheckResult() {
		results = new HashMap<>();
		actions = new ArrayList<>();
	}
	
	public void performResult(int checkScore) {
		int thresh = checkScore;
		while (thresh >= MIN_SCORE) {
			if (results.containsKey(thresh)) {
				results.get(thresh).execute();
				break;
			} else {
				thresh--;
			}
		}
	}
	
	public SkillCheckResult addResult(int level, String message, int... actionIDs) {
		results.put(level, new Result(message, actionIDs));
		return this;
	}
	
	public SkillCheckResult addResult(int level, int... actionIDs) {
		results.put(level, new Result(null, actionIDs));
		return this;
	}
	
	public SkillCheckResult addMinResult(String message, int... actionIDs) {
		results.put(MIN_SCORE, new Result(message, actionIDs));
		return this;
	}
	
	public SkillCheckResult addMinResult(int... actionIDs) {
		results.put(MIN_SCORE, new Result(null, actionIDs));
		return this;
	}
	
	public SkillCheckResult addAction(CheckAction action) {
		actions.add(action);
		return this;
	}

}
