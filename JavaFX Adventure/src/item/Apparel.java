package item;

public class Apparel extends Item implements Equippable {

	private EquipSlot[] validSlots;
	private boolean equipped;
	
	public Apparel(String name, int baseValue, float weight) {
		super(name, baseValue, weight, ItemType.GEAR);
		this.validSlots = new EquipSlot[0];
		this.equipped = false;
	}

	public void onEquip() {
		equipped = true;
	}

	public void onUnequip() {
		equipped = false;
	}

	public boolean isEquipped() {
		return equipped;
	}

	public EquipSlot[] getValidSlots() {
		return validSlots;
	}
	
	public Apparel setValidSlots(EquipSlot[] slots) {
		validSlots = slots;
		return this;
	}

}
