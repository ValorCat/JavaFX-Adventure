package item;

public enum EquipSlot {
	
	ARMS, BACK, CHEST, EYES, FEET, HANDS, HEAD, LEFT_FINGER, LEGS, MAIN_HAND, NECK, OFF_HAND, RIGHT_FINGER, SHOULDERS, WAIST, WRIST;

	public String toString() {
		StringBuilder name = new StringBuilder();
		boolean nextUpperCase = true;
		for (char c : name().toLowerCase().toCharArray()) {
			if (nextUpperCase) {
				c = Character.toUpperCase(c);
				nextUpperCase = false;
			} else if (c == '_') {
				c = ' ';
			}
			name.append(c);
		}
		return name.toString();
	}

}