package item;

public interface Equippable {
	
	void onEquip();
	void onUnequip();
	boolean isEquipped();
	EquipSlot[] getValidSlots();

}
