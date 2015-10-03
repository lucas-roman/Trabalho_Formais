package main.model.commandline.fileutils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.grammar.NonTerminal;
import main.lexer.grammar.RegularGrammar;
import main.lexer.grammar.RegularProduction;
import main.lexer.regularexpression.RegularExpression;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA INE - DEPARTAMENTO DE INFORM�TICA E
 * ESTAT�STICA LINGUAGENS FORMAIS E COMPILADORES
 * 
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES Copyright � 2015
 */

public class Writer {

	private PrintWriter pw;
	private String fileName;

	public Writer(String fileName) {
		this.fileName = fileName;
	}

	public void writeRegularExpression(RegularExpression re)
			throws UnsupportedEncodingException, FileNotFoundException {
		pw = new PrintWriter(fileName, "UTF-8");
		pw.println("REGULAREXPRESSION");
		pw.println(re.toString());
		pw.close();
	}

	public void writeAutomata(Automata aut) throws FileNotFoundException,
			UnsupportedEncodingException {
		pw = new PrintWriter(fileName, "UTF-8");
		pw.println("STATES");
		pw.println(aut.initialState().stateID());
		for (AutomataState state : aut.getStates()) {
			if (state != aut.initialState()) {
				pw.println(state.stateID());
			}
		}
		pw.println("END");
		pw.println();
		pw.println();
		pw.println("FINALSTATES");
		for (AutomataState acceptState : aut.acceptStates()) {
			pw.println(acceptState.stateID());
		}
		pw.println("END");
		pw.println();
		pw.println();
		pw.println("TRANSITIONS");
		for (AutomataState state : aut.getStates()) {
			for (Entry<Character, Set<AutomataState>> trans : state
					.getTransitions()) {
				for (AutomataState reached : trans.getValue()) {
					pw.print(state.stateID());
					pw.print(" ");
					pw.print(reached.stateID());
					pw.print(" ");
					pw.print(trans.getKey());
					pw.print('\n');
				}
			}
			for (AutomataState epslonReached : state.epslonTransitions()) {
				pw.print(state.stateID());
				pw.print(" ");
				pw.print(epslonReached.stateID());
				pw.print('\n');
			}
		}
		pw.print("END");
		pw.close();
	}
	
	public void writeGrammar(RegularGrammar grammar) throws FileNotFoundException {
		pw = new PrintWriter(fileName);
		pw.println("INITIALSYMBOL");
		pw.println(grammar.initSymbol().getSymbolValue());
		pw.println("END");
		pw.println("SYMBOLS");
		for (NonTerminal t : grammar.nonTerminalSymbols()) {
			pw.println(t.getSymbolValue());
		}
		pw.println("END");
		pw.println("TRANSITIONS");
		for (NonTerminal t : grammar.nonTerminalSymbols()) {
			pw.print(t.getSymbolValue());
			pw.print("=");
			String partial = "";
			for (RegularProduction p : t.getProductions()) {
				partial += p + "|";
			}
			String toPut = partial = partial.substring(0, partial.length() - 1);
			pw.println(toPut);
		}
		pw.println("END");
		pw.close();
	}
}
