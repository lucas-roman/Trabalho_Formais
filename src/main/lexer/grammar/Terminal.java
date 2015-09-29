package main.lexer.grammar;

public class Terminal implements GrammarSymbol {

	private String value;

	public Terminal(String value2) {
		value = value2;
	}

	@Override
	public String getSymbolValue() {
		return value;
	}

	@Override
	public boolean isTerminal() {
		return true;
	}

}
