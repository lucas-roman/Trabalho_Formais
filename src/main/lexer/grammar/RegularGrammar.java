package main.lexer.grammar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

public class RegularGrammar {

	private Set<NonTerminal> nonTerminalSymbols;

	private Set<Terminal> terminalSymbols;

	private NonTerminal startSymbol;

	private boolean emptyWord = false;

	// Takes an automata and creates a grammar. To implement.
	public static RegularGrammar convertAutomataToGrammar(Automata deterministic) {
		RegularGrammarBuilder builder = new RegularGrammarBuilder();
		return null;
	}

	RegularGrammar(Collection<NonTerminal> nonTerminalSymb,
			Collection<Terminal> terminalSymb, NonTerminal startSymbol) {
		nonTerminalSymbols = new HashSet<>(nonTerminalSymb);
		terminalSymbols = new HashSet<>(terminalSymb);
		this.startSymbol = startSymbol;
	}

	// Should convert this grammar to an automata which accepts the language. To
	// implement
	public Automata createAutomata() throws MissingStateException,
			OverrideInitialStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("Final");
		builder.markAcceptState("Final");
		for(NonTerminal state : nonTerminalSymbols) {
			builder.addState(state.getSymbolValue());
		}
		for (NonTerminal currentState : nonTerminalSymbols) {
			for (RegularProduction p : currentState.getProductions()) {
				if (p.lastProduction()) {
					builder.addTransition(currentState.getSymbolValue(),
							"Final", p.terminal());
				} else if (p.terminal() == 0) {
					builder.addEmptyTransition(currentState.getSymbolValue(), p
							.nonTerminal().getSymbolValue());
				} else {
					builder.addTransition(currentState.getSymbolValue(), p
							.nonTerminal().getSymbolValue(), p.terminal());
				}
			}
		}
		builder.markInitialState(startSymbol.getSymbolValue());
		if(emptyWord) {
			builder.markAcceptState(startSymbol.getSymbolValue());
		}
		return builder.build();
	}

	void emptyWordEnable(boolean emptyWord) {
		this.emptyWord  = emptyWord;
	}

}
