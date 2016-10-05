package chars;

import java.util.HashMap;
import java.util.Map;

public class SkillSet {
	
	private Map<Skill, Integer> skills;
	
	public SkillSet() {
		skills = new HashMap<Skill, Integer>();
	}
	
	public int getLevel(Skill skillName) {
		return skills.getOrDefault(skillName, Skill.BASE_LEVEL);
	}
	
	public void modifyLevel(Skill skillName, int amount) {
		if (skills.containsKey(skillName)) {
			skills.compute(skillName, (skill, value) -> value + amount);
		} else {
			skills.put(skillName, amount);
		}
	}

}
