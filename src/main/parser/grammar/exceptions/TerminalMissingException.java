package main.parser.grammar.exceptions;

public class TerminalMissingException extends Exception {
	
	private String terminalValue;

	@Override
	public void printStackTrace() {
		System.err.println("Couldn't find terminal : " + terminalValue);
		super.printStackTrace();
	}

	public TerminalMissingException(String term) {
		terminalValue = term;
	}

}
