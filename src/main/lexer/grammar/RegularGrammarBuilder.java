package main.lexer.grammar;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.grammar.exceptions.StartSymbolMissingException;

public class RegularGrammarBuilder {

	private Map<String, NonTerminal> nonTermMap = new HashMap<>();

	private Map<Character, Terminal> termMap = new HashMap<>();

	private NonTerminal startSymbol;

	private boolean emptyWord = false;

	public RegularGrammar createGrammar() throws StartSymbolMissingException {
		if(startSymbol == null) {
			throw new StartSymbolMissingException();
		}
		RegularGrammar rg = new RegularGrammar(nonTermMap.values(), termMap.values(),
				startSymbol);
		rg.emptyWordEnable(emptyWord);
		return rg;
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

	public void addProduction(NonTerminal head, Terminal symbol1, NonTerminal symbol2) {
		head.addProduction(symbol1, symbol2);

	}

	public void addProduction(NonTerminal head, Terminal symbol1) {
		head.addProduction(symbol1);

	}

	public void addProduction(NonTerminal head, NonTerminal symbol1) {
		head.addProduction(symbol1);

	}

    public void addProduction(AutomataBuilder builder, AutomataState current, Set<AutomataState> visited) {
		visited.add(nextState);
		for (Entry<Character, Set<AutomataState>> keyVal : result.initialState().getTransitions()) {
			for (AutomataState nextState : keyVal.getValue()) {
				if (!visited.contains(nextState)) {
					if (nextState.accepts()) {
						Terminal term = new Terminal(keyVal.getKey());
						builder.addProduction(head, symbol1);
					}
					Terminal term2 = new Terminal(keyVal.getKey());
					builder.addProduction(head, term2, nextState);

				}

			}
		}
	}

	//Let the automata recognize the empty word.
	public void addEmptyWord() {
		emptyWord  = true;
	}


}
