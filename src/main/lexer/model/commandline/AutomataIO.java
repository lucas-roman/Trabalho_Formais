/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
import main.lexer.model.commandline.exceptions.IllegalOrderOfTextStructure;
import main.lexer.model.commandline.exceptions.IllegalStartOfText;

public class AutomataIO {

	private Scanner scan;
	private AutomataBuilder automataBuilder;

	public Automata readAutomata(File file) throws InvalidStateException,
			IllegalStartOfText, IllegalOrderOfTextStructure,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException, FileNotFoundException {
		automataBuilder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		File aux_file = file;
		this.scan = new Scanner(aux_file);
		String aux = this.scan.nextLine();
		boolean theresMoreLine = false;
		boolean end = false;

		if (!aux.isEmpty()) {
			theresMoreLine = true;
		}
		while (theresMoreLine) {
			if (aux.equals("STATES")) {
				aux = this.scan.nextLine();
				while (!end) {
					aux.trim();
					automataBuilder.addState(aux);
					aux = this.scan.nextLine();
					if (aux.equals("END")) {
						end = true;
					}
				}
			} else {
				throw new IllegalStartOfText();
			}
			aux = this.scan.nextLine();
			String checker = aux.trim();
			while (checker.length() == 0) {
				aux = this.scan.nextLine();
				checker = aux.trim();
			}
			end = false;
			if (aux.equals("FINALSTATES")) {
				/*
				 * Source of errors and bugs, correct later. The next line can
				 * be END or something else, but a state. Or it can be an state
				 * that does not belong to the automata.
				 */
				aux = this.scan.nextLine();
				while (!end) {
					aux.trim();
					this.automataBuilder.markAcceptState(aux);
					aux = this.scan.nextLine();
					if (aux.equals("END")) {
						end = true;
					}
				}
			} else {
				throw new IllegalOrderOfTextStructure();
			}

			aux = this.scan.nextLine();
			end = false;
			checker = aux.trim();
			while (checker.length() == 0) {
				aux = this.scan.nextLine();
				checker = aux.trim();
			}
			if (aux.equals("TRANSITIONS")) {
				aux = this.scan.nextLine();
				while (!end) {
					String from = "";
					String to = "";
					char trans;
					String[] details = aux.split(" ");
					if (details.length >= 2) {
						from = details[0];
						to = details[1];
					}
					/*
					 * If it has 4 items, it means that it has some char at the
					 * end (at least we hope). It would be good to have a code
					 * to check if that char belongs to the alphabet automata.
					 * PS: Whitespaces count.
					 */
					if (details.length == 3) {
						trans = details[2].charAt(0);
						this.automataBuilder.addTransition(from, to, trans);
					} else {
						this.automataBuilder.addEmptyTransition(from, to);
					}

					aux = this.scan.nextLine();
					if (aux.equals("END")) {
						end = true;
					}
				}
				aux = this.scan.nextLine();
				checker = aux.trim();
				while (checker.length() == 0) {
					aux = this.scan.nextLine();
					checker = aux.trim();
				}
				if (aux.equals("TAGS")) {
					aux = this.scan.nextLine();
					end = false;
					while (!end) {
						String state;
						String tag;
						String[] details = aux.split(" ");
						if (details.length == 2) {
							state = details[0];
							tag = details[1];
						} else {
							state = details[1];
							tag = "";
						}
						this.automataBuilder.addTagToState(state, tag);
						aux = this.scan.nextLine();
						if (aux.equals("END")) {
							end = true;
						}
					}
				}
			} else {
				throw new IllegalOrderOfTextStructure();
			}
			theresMoreLine = false;

		}

		this.scan.close();
		return automataBuilder.build();
	}

	public void writeAutomata(Automata aut, String fileName)
			throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter pw = new PrintWriter(fileName, "UTF-8");
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
		pw.println("END");
		pw.println("TAGS");
		for (AutomataState state : aut.getStates()) {
			pw.print(state);
			pw.print(" ");
			pw.print(state.getTag());
			pw.print('\n');
		}
		pw.print("END");
		pw.close();
	}

}
