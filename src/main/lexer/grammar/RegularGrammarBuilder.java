package main.lexer.grammar;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.lexer.grammar.exceptions.SameProductionException;
import main.lexer.grammar.exceptions.StartSymbolMissingException;

public class RegularGrammarBuilder {

	private Map<String, NonTerminal> nonTermMap = new HashMap<>();

	private Map<Character, Terminal> termMap = new HashMap<>();

	private NonTerminal startSymbol;

	public RegularGrammar createGrammar() throws StartSymbolMissingException {
		if(startSymbol == null) {
			throw new StartSymbolMissingException();
		}
		return new RegularGrammar(nonTermMap.values(), termMap.values(), 
				startSymbol);
	}

	public void markStartSymbol(NonTerminal nT) {
		startSymbol = nT;
	}

	public Terminal addTerminal(char value) {
		if (!termMap.containsKey(value)) {
			Terminal newT = new Terminal(value);
			termMap.put(value, newT);
		}
		return termMap.get(value);
	}

	public NonTerminal addNonTerminal(String c) {
		if (!nonTermMap.containsKey(c)) {
			NonTerminal newNT = new NonTerminal(c);
			nonTermMap.put(c, newNT);
		}
		return nonTermMap.get(c);
	}
	
	public Terminal getTerminalOf(char term) {
		return termMap.get(term);
	}
	
	public NonTerminal getNonTerminalOf(String term) {
		return nonTermMap.get(term);
	}

	public void addProduction(NonTerminal head, Terminal symbol1, NonTerminal symbol2)
			throws SameProductionException {
		head.addProduction(symbol1, symbol2);

	}
	
	public void addProduction(NonTerminal head, Terminal symbol1)
			throws SameProductionException {
		head.addProduction(symbol1);

	}
	
	public void addProduction(NonTerminal head, NonTerminal symbol1)
			throws SameProductionException {
		head.addProduction(symbol1);

	}


}
