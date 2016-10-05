package util;

public class IntRange {
	
	private int min, max;
	
	public IntRange(int max) {
		this(0, max);
	}
	
	public IntRange(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public int getRandom() {
		return (int) (Math.random() * (max - min) + min + 1);
	}
	
	public boolean inRange(int value) {
		return value >= min && value <= max;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + max;
		result = prime * result + min;
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
		IntRange other = (IntRange) obj;
		if (max != other.max)
			return false;
		if (min != other.min)
			return false;
		return true;
	}

}
