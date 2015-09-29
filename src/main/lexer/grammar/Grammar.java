package main.lexer.grammar;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;


public class Grammar {

	private Set<NonTerminal> nonTerminalSymbols;
	
	private Set<Terminal> terminalSymbols;

	private NonTerminal startSymbol;
	
	//Takes an automata and creates a grammar. To implement.
	public static Grammar convertAutomataToGrammar(Automata deterministic) {
		GrammarBuilder builder = new GrammarBuilder();
		return null;
	}

	Grammar(Collection<NonTerminal> nonTerminalSymb,
			Collection<Terminal> terminalSymb,
			NonTerminal startSymbol) {
		nonTerminalSymbols = new HashSet<>(nonTerminalSymb);
		terminalSymbols = new HashSet<>(terminalSymb);
		this.startSymbol = startSymbol;
	}

	//Should convert this grammar to an automata which accepts the language. To implement
	public Automata createAutomata() {
		try {
			AutomataBuilder builder = new AutomataBuilder(new AutomataStructureGraphFactory());
			builder.addState(startSymbol.getSymbolValue());
			
		} catch (InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
