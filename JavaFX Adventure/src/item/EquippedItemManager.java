package item;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class EquippedItemManager {

	private Map<EquipSlot, Equippable> gear;
	
	public EquippedItemManager() {
		this.gear = new HashMap<EquipSlot, Equippable>();
	}
	
	public Equippable getItem(EquipSlot slot) {
		return gear.get(slot);
	}

	public Set<Entry<EquipSlot, Equippable>> getItems() {
		return gear.entrySet();
	}
	
	public void equipItem(EquipSlot slot, Equippable item) {
		gear.put(slot, item);
		item.onEquip();
	}

	public void unequipItem(EquipSlot slot) {
		gear.remove(slot).onUnequip();
	}
	
	public void switchItems(EquipSlot slot1, EquipSlot slot2) {
		Equippable temp = gear.replace(slot1, gear.get(slot2));
		gear.replace(slot2, temp);
	}
	
}
