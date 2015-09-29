package main.lexer.grammar;

import java.util.ArrayList;
import java.util.List;

import main.lexer.grammar.exceptions.SameProductionException;

public class NonTerminal implements GrammarSymbol {

	private String value;
	
	private List<Production> transitions = new ArrayList<>();

	public NonTerminal(String c) {
		value = c;
	}

	@Override
	public String getSymbolValue() {
		return value;
	}

	public void addTransition(List<GrammarSymbol> trans) throws SameProductionException {
		if(transitions.contains(trans)) {
			throw new SameProductionException();
		}
		transitions.add(new Production(trans));
	}

	@Override
	public boolean isTerminal() {
		return false;
	}
	
}
