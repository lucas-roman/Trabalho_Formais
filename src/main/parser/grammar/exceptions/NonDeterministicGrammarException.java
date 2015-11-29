package main.parser.grammar.exceptions;

import main.parser.grammar.ContextFreeProduction;
import main.parser.grammar.ContextFreeTerminalSymbol;

public class NonDeterministicGrammarException extends Exception {

	private ContextFreeProduction production1;
	private ContextFreeProduction production2;
	private ContextFreeTerminalSymbol terminal;

	public NonDeterministicGrammarException(ContextFreeTerminalSymbol valToPut,
			ContextFreeProduction production,
			ContextFreeProduction contextFreeProduction) {
		this.production1 = production;
		this.production2 = contextFreeProduction;
		this.terminal = valToPut;
	}

	public String printerr() {
		return "Terminal of non determinism : " + terminal + "\n"
				+ "First production : " + production1 + '\n'
				+ "Second production : " + production2 + "\n";
	}

}
