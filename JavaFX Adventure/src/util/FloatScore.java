package util;

public class FloatScore {
	
	private float value, min, max;
	
	public FloatScore(float max) {
		this(max, 0, max);
	}
	
	public FloatScore(float value, float max) {
		this(value, 0, max);
	}
	
	public FloatScore(float value, float min, float max) {
		this.value = value;
		this.min = min;
		this.max = max;
	}
	
	public float getValue() {
		return value;
	}
	
	public float getMin() {
		return min;
	}
	
	public float getMax() {
		return max;
	}
	
	public float getPercent() {
		return (value - min) / (max - min);
	}
	
	public float getRemaining() {
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
	
	public void update(float amount) {
		value += amount;
		if (value > max)
			value = max;
	}
	
	public void updateNoLimit(float amount) {
		value += amount;
	}
	
	public void updateTo(float amount) {
		value = amount;
	}
	
	public void maximize() {
		value = max;
	}
	
	public void minimize() {
		value = min;
	}
	
	public void updateMax(float amount) {
		max += amount;
	}
	
	public void updateMaxTo(float amount) {
		max = amount;
	}
	
	public void updateMin(float amount) {
		min += amount;
	}
	
	public void updateMinTo(float amount) {
		min = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(max);
		result = prime * result + Float.floatToIntBits(min);
		result = prime * result + Float.floatToIntBits(value);
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
		FloatScore other = (FloatScore) obj;
		if (Float.floatToIntBits(max) != Float.floatToIntBits(other.max))
			return false;
		if (Float.floatToIntBits(min) != Float.floatToIntBits(other.min))
			return false;
		if (Float.floatToIntBits(value) != Float.floatToIntBits(other.value))
			return false;
		return true;
	}

}
