package main.lexer.grammar.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.lexer.grammar.Grammar;
import main.lexer.grammar.GrammarSymbol;
import main.lexer.grammar.NonTerminal;
import main.lexer.grammar.Terminal;

public class GrammarBuilder {
	
	private Map<Character, NonTerminal> nonTermMap = new HashMap<>();
	
	private Map<Character, Terminal> termMap = new HashMap<>();
	
	private Map<NonTerminal, List<List<GrammarSymbol>>> productions = new HashMap<>();

	public Grammar createGrammar() {
		return null;
	}

	public Terminal addTerminal(char c) {
		if(!termMap.containsKey(c)) {
			Terminal newT = new Terminal(c);
			termMap.put(c, newT);
		}
		return termMap.get(c);
	}

	public NonTerminal addNonTerminal(char c) {
		if(!nonTermMap.containsKey(c)) {
			NonTerminal newNT = new NonTerminal(c);
			nonTermMap.put(c, newNT);
		}
		return nonTermMap.get(c);
	}

	public void addProduction(NonTerminal head, List<GrammarSymbol> production) {
		if(!productions.containsKey(head)) {
			productions.put(head, new ArrayList<List<GrammarSymbol>>());
		}
		productions.get(head).add(production);
	}

}
