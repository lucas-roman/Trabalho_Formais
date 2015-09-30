package main.lexer.grammar;

import java.util.Collection;
import java.util.HashSet;
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

public class RegularGrammar {

	private Set<NonTerminal> nonTerminalSymbols;

	private Set<Terminal> terminalSymbols;

	private NonTerminal startSymbol;

	private boolean emptyWord = false;

	// Takes an automata and creates a grammar. To implement.
	public static RegularGrammar convertAutomataToGrammar(Automata deterministic) {
		RegularGrammarBuilder builder = new RegularGrammarBuilder();
		Set<AutomataState> visited = new HashSet<>();
		//Result will always be deterministic. (We always need a little hack xD)
		Automata result;
		try {
			Automata determ = deterministic.convert();
			result = determ;
		} catch (DeterministicException e) {
			result = deterministic;
		}

		for(char c : state.getTransitions()) {
			   for(AutomataState state : state.nextState(char c)) {
				        //aqui dentro só vai ser processado 1 estado
				        //faz alguma coisa com ele

				   /*
				    * Ideia:
				    * if(destino.accepts())	//É estado final, adiciona uma produção pelo terminal
				    * 	Terminal term = new Terminal(c);
				    * 	build.addProduction(state, c);
				    * else	//Não é final, adiciona o terminal e não terminal (estado destino)
				    * 	Terminal term = new Terminal(c);
				    * 	NonTerminal nt = new NonTerminal(estadoDestino.getNome());
				    * 	builder.addProduction(state, term, nt);
				    */
				}
			}

		return null;
	}

	/*
	    * Ideia:
	    * if(destino.accepts())	//Ã‰ estado final, adiciona uma produÃ§Ã£o pelo terminal
	    * 	Terminal term = new Terminal(c);
	    * 	build.addProduction(state, c);
	    * else	//NÃ£o Ã© final, adiciona o terminal e nÃ£o terminal (estado destino)
	    * 	Terminal term = new Terminal(c);
	    * 	NonTerminal nt = new NonTerminal(estadoDestino.getNome());
	    * 	builder.addProduction(state, term, nt);
	    */

	RegularGrammar(Collection<NonTerminal> nonTerminalSymb, Collection<Terminal> terminalSymb, NonTerminal startSymbol) {
		nonTerminalSymbols = new HashSet<>(nonTerminalSymb);
		terminalSymbols = new HashSet<>(terminalSymb);
		this.startSymbol = startSymbol;
	}

	// Should convert this grammar to an automata which accepts the language. (To implement)
	public Automata createAutomata() throws MissingStateException, OverrideInitialStateException, InvalidStateException, InitialStateMissingException, IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(new AutomataStructureGraphFactory());
		builder.addState(startSymbol.getSymbolValue());
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
		if(emptyWord) {
			builder.markAcceptState(startSymbol.getSymbolValue());
		}

		return builder.build();
	}

	void emptyWordEnable(boolean emptyWord) {
		this.emptyWord  = emptyWord;
	}

}
