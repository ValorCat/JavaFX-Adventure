package dialog;

public class DialogParseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String line;
	private String special;
	private int lineNumber;
	
	public DialogParseException(String line, int lineNumber) {
		this(line, lineNumber, "<none>");
	}
	
	public DialogParseException(int lineNumber, String special) {
		this("<full line unavailable>", lineNumber, special);
	}
	
	public DialogParseException(String line, int lineNumber, String special) {
		this.line = line;
		this.special = special;
		this.lineNumber = lineNumber;
	}
	
	public String getLine() {
		return line;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	public String getSpecialMessage() {
		return special;
	}
	
	public String toString() {
		return String.format("line %d: %s (%s)", lineNumber, special, line);
	}

}
