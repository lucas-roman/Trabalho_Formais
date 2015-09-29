package main.lexer.grammar;

public class Terminal implements GrammarSymbol {

	private char value;

	public Terminal(char value2) {
		value = value2;
	}

	@Override
	public String getSymbolValue() {
		return "" + value;
	}

}
