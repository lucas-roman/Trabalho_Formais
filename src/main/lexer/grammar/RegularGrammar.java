package main.lexer.grammar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
import main.lexer.grammar.exceptions.NonTerminalMissingException;
import main.lexer.grammar.exceptions.StartSymbolMissingException;
import main.lexer.grammar.exceptions.TerminalMissingException;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

public class RegularGrammar {

	private Set<NonTerminal> nonTerminalSymbols;

	private Set<Terminal> terminalSymbols;

	private NonTerminal startSymbol;

	private boolean emptyWord = false;

	@Override
	public String toString() {
		String result = "";
		result += "Terminals : \n \n";
		for (Terminal t : terminalSymbols) {
			result += t.getSymbolValue() + "\n";
		}
		result += "\n============================ \n \n";
		result += "Non Terminals : \n \n";
		for (NonTerminal t : nonTerminalSymbols) {
			result += t.getSymbolValue() + "\n";
		}
		result += "\n============================\n\n";
		result += "Start Symbol : \n\n" + startSymbol.getSymbolValue()
				+ "\n\n\n============================\n\nProductions : ";
		for (NonTerminal t : nonTerminalSymbols) {
			result += t.getSymbolValue() + "-> : ";
			for (RegularProduction p : t.getProductions()) {
				result += p + " | ";
			}
			result = result.substring(0, result.length() - 2);
			result += "\n";
		}
		return result;
	}

	// Takes an automata and creates a grammar. To implement.
	public static RegularGrammar convertAutomataToGrammar(Automata deterministic)
			throws NonTerminalMissingException, StartSymbolMissingException {
		RegularGrammarBuilder builder = new RegularGrammarBuilder();
		Set<AutomataState> visited = new HashSet<>();

		// Result will always be deterministic. (We always need a little hack
		// xD)
		Automata result;
		try {
			Automata determ = deterministic.convert();
			result = determ;
		} catch (DeterministicException e) {
			result = deterministic;
		}
		addStatesToGrammar(result, builder);
		addProduction(builder, result.initialState(), visited);

		return builder.createGrammar();
	}

	private static void addStatesToGrammar(Automata result,
			RegularGrammarBuilder builder) throws NonTerminalMissingException {
		for (AutomataState state : result.getStates()) {
			builder.addNonTerminal(state.toString());
		}
		if (result.initialState().epslonAccept()) {
			builder.addEmptyWord();
		}
		builder.markStartSymbol(builder.getNonTerminalOf(result.initialState()
				.toString()));

	}

	private static void addProduction(RegularGrammarBuilder builder,
			AutomataState current, Set<AutomataState> visited)
			throws NonTerminalMissingException {
		visited.add(current);
		for (Entry<Character, Set<AutomataState>> keyVal : current
				.getTransitions()) {
			for (AutomataState nextState : keyVal.getValue()) {

				if (nextState.accepts()) {
					Terminal term;
					try {
						term = builder.getTerminalOf(keyVal.getKey());
					} catch (TerminalMissingException e) {
						term = builder.addTerminal(keyVal.getKey());
					}
					builder.addProduction(
							builder.getNonTerminalOf(current.toString()), term);
				}
				Terminal term;
				try {
					term = builder.getTerminalOf(keyVal.getKey());
				} catch (TerminalMissingException e) {
					term = builder.addTerminal(keyVal.getKey());
				}
				builder.addProduction(
						builder.getNonTerminalOf(current.toString()), term,
						builder.getNonTerminalOf(nextState.toString()));
				if (!visited.contains(nextState)) {
					addProduction(builder, nextState, visited);
				}

			}

		}

	}

	/*
	 * Ideia: if(destino.accepts()) //É estado final, adiciona uma produção pelo
	 * terminal Terminal term = new Terminal(c); build.addProduction(state, c);
	 * else //Não é final, adiciona o terminal e não terminal (estado destino)
	 * Terminal term = new Terminal(c); NonTerminal nt = new
	 * NonTerminal(estadoDestino.getNome()); builder.addProduction(state, term,
	 * nt);
	 */

	RegularGrammar(Collection<NonTerminal> nonTerminalSymb,
			Collection<Terminal> terminalSymb, NonTerminal startSymbol) {
		nonTerminalSymbols = new HashSet<>(nonTerminalSymb);
		terminalSymbols = new HashSet<>(terminalSymb);
		this.startSymbol = startSymbol;
	}

	// Should convert this grammar to an automata which accepts the language.
	// (To implement)
	public Automata createAutomata() throws MissingStateException,
			OverrideInitialStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState(startSymbol.getSymbolValue());
		builder.addState("Final");
		builder.markAcceptState("Final");

		for (NonTerminal state : nonTerminalSymbols) {
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
		if (emptyWord) {
			builder.markAcceptState(startSymbol.getSymbolValue());
		}

		return builder.build();
	}

	void emptyWordEnable(boolean emptyWord) {
		this.emptyWord = emptyWord;
	}

}
