package util;

public class IntScore {
	
	private int value, min, max;
	
	public IntScore(int max) {
		this(max, 0, max);
	}
	
	public IntScore(int value, int max) {
		this(value, 0, max);
	}
	
	public IntScore(int value, int min, int max) {
		this.value = value;
		this.min = min;
		this.max = max;
	}
	
	public int getValue() {
		return value;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getPercent() {
		return (value - min) / (max - min);
	}
	
	public int getRemaining() {
		return max - value;
	}
	
	public boolean isMin() {
		return value == min;
	}
	
	public boolean isMax() {
		return value == max;
	}
	
	public boolean isZero() {
		return value == 0;
	}
	
	public boolean isOverfilled() {
		return value > max;
	}
	
	public void update(int amount) {
		value += amount;
		if (value > max)
			value = max;
	}
	
	public void updateNoLimit(int amount) {
		value += amount;
	}
	
	public void updateTo(int amount) {
		value = amount;
	}
	
	public void maximize() {
		value = max;
	}
	
	public void minimize() {
		value = min;
	}
	
	public void updateMax(int amount) {
		max += amount;
	}
	
	public void updateMaxTo(int amount) {
		max = amount;
	}
	
	public void updateMin(int amount) {
		min += amount;
	}
	
	public void updateMinTo(int amount) {
		min = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + min;
		result = prime * result + value;
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
		IntScore other = (IntScore) obj;
		if (max != other.max)
			return false;
		if (min != other.min)
			return false;
		if (value != other.value)
			return false;
		return true;
	}

}
