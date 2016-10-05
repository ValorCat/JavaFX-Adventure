package item;

public class InvSlot {
	
	private Item item;
	private int count;
	private float weight;
	
	public InvSlot(Item item) {
		this(item, 1);
	}
	
	public InvSlot(Item item, int count) {
		this.item = item;
		this.count = count;
		this.weight = item.getWeight() * count;
	}
	
	public String getDisplayName() {
		if (count == 1) {
			return item.getName();
		} else {
			return String.format("%s (%d)", item.getName(), count);
		}
	}
	
	public Item getItem() {
		return item;
	}
	
	public int getCount() {
		return count;
	}
	
	public float getTotalWeight() {
		return weight;
	}
	
	public int getTotalValue() {
		return item.getBaseValue() * count;
	}
	
	public void changeCount(int amount) {
		count += amount;
		if (count < 0) {
			throw new ItemManager.InventoryOverdrawException(this);
		}
	}
	
	public String toString() {
		return getDisplayName();
	}
	
}