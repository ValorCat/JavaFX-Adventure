package item;

import java.util.ArrayList;
import java.util.List;

import util.FloatScore;

public class ItemManager {
	
	public static class InventoryOverdrawException extends RuntimeException {

		private static final long serialVersionUID = 1L;
		
		private InvSlot slot;
		
		public InventoryOverdrawException(InvSlot slot) {
			this.slot = slot;
		}
		
		public InvSlot getSlot() {
			return slot;
		}
		
	}
	
	public class InventoryOverflowException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		private float itemWeight, currentWeight, maxWeight;
		
		public InventoryOverflowException(float itemWeight, float currentWeight, float maxWeight) {
			this.itemWeight = itemWeight;
			this.currentWeight = currentWeight;
			this.maxWeight = maxWeight;
		}

		public float getItemWeight() {
			return itemWeight;
		}
		
		public float getCurrentWeight() {
			return currentWeight;
		}

		public float getMaxWeight() {
			return maxWeight;
		}
		
	}
	
	private List<InvSlot> items;
	private FloatScore weight;
	private int count;
	
	public ItemManager(float maxCarryWeight) {
		this.items = new ArrayList<>();
		this.weight = new FloatScore(0, maxCarryWeight);
		this.count = 0;
	}
	
	public List<InvSlot> getItems() {
		return items;
	}
	
	public int getItemCount() {
		return count;
	}
	
	public int getUniqueItemCount() {
		return items.size();
	}
	
	public float getTotalWeight() {
		return weight.getValue();
	}
	
	public float getMaxWeight() {
		return weight.getMax();
	}
	
	public float getWeightPercent() {
		return weight.getPercent();
	}
	
	public void updateMaxWeight(float amount) {
		weight.updateMax(amount);
	}
	
	public void addItem(Item item) {
		float itemWeight = item.getWeight();
		if (itemWeight > weight.getRemaining())
			throw new InventoryOverflowException(itemWeight, weight.getValue(), weight.getMax());
		forceAddItem(item);
	}
	
	public void forceAddItem(Item item) {
		int uniqueCount = items.size();
		if (uniqueCount == 0) {
			items.add(new InvSlot(item));
		} else {
			boolean alreadyExists = false;
			for (InvSlot slot : items) {
				if (slot.getItem().equals(item)) {
					slot.changeCount(1);
					alreadyExists = true;
					break;
				}
			}
			if (!alreadyExists) {
				for (int i = 0; i < uniqueCount; i++) {
					if (item.compareTo(items.get(i).getItem()) > 0 || i == uniqueCount - 1) {
						items.add(i, new InvSlot(item));
						break;
					}
				}
			}
		}
		weight.update(item.getWeight());
		count++;
	}

}
