package dialog;

public class Comparison {
	
	public static enum CompareOperator { EQUAL, INEQUAL, GREATER, LESSER, ADD, SUBTRACT, MULTIPLY, DIVIDE, POWER }
	
	private String attributeName;
	private CompareOperator operator;
	private String value;
	private int fromLineNumber;
	
	public Comparison(String attributeName, String operator, String value, int fromLineNumber) {
		this.attributeName = attributeName;
		this.operator = getOperator(operator);
		this.value = value;
		this.fromLineNumber = fromLineNumber;
	}

	public String getAttributeName() {
		return attributeName;
	}
	
	public CompareOperator getOperator() {
		return operator;
	}

	public String getValue() {
		return value;
	}
	
	public int getLineNumber() {
		return fromLineNumber;
	}
	
	private static CompareOperator getOperator(String oper) {
		switch (oper) {
		case "=":
			return CompareOperator.EQUAL;
		case "!=":
			return CompareOperator.INEQUAL;
		case ">":
			return CompareOperator.GREATER;
		case "<":
			return CompareOperator.LESSER;
		case "+":
			return CompareOperator.ADD;
		case "-":
			return CompareOperator.SUBTRACT;
		case "*":
			return CompareOperator.MULTIPLY;
		case "/":
			return CompareOperator.DIVIDE;
		case "^":
			return CompareOperator.POWER;
		default:
			throw new IllegalArgumentException("Bad comparison operator " + oper);
		}
	}

}
