package item;

public class Item implements Comparable<Item> {
	
	protected String name;
	protected int baseValue;
	protected float weight;
	protected ItemType type;
	
	public Item(String name, int baseValue, float weight, ItemType type) {
		this.name = name;
		this.baseValue = baseValue;
		this.weight = weight;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public int getBaseValue() {
		return baseValue;
	}

	public float getWeight() {
		return weight;
	}
	
	public ItemType getType() {
		return type;
	}
	
	@Override
	public int compareTo(Item other) {
		return name.compareTo(other.getName());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + baseValue;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(weight);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (baseValue != other.baseValue)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(weight) != Float.floatToIntBits(other.weight))
			return false;
		return true;
	}

}
