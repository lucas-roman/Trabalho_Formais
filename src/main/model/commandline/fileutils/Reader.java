package main.model.commandline.fileutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
import main.lexer.grammar.RegularGrammar;
import main.lexer.grammar.RegularGrammarBuilder;
import main.lexer.grammar.exceptions.NonTerminalMissingException;
import main.lexer.grammar.exceptions.StartSymbolMissingException;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.StringToRE;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;
import main.model.commandline.fileutils.exceptions.IllegalOrderOfTextStructure;
import main.model.commandline.fileutils.exceptions.IllegalStartOfText;
import main.model.commandline.fileutils.exceptions.IllegalTextStructure;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA INE - DEPARTAMENTO DE INFORMÁTICA E
 * ESTATÍSTICA LINGUAGENS FORMAIS E COMPILADORES
 * 
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES Copyright © 2015
 */

public class Reader {

	private Scanner scan;
	private AutomataBuilder automataBuilder;
	private RegularGrammarBuilder regularBuilder;

	public Reader() {
		this.automataBuilder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		this.regularBuilder = new RegularGrammarBuilder();
	}

	public void readFile(File file) {
		/*
		 * File aux_file = file; this.scan = new Scanner(aux_file); String
		 * command = scan.nextLine(); command.toLowerCase(); command.trim();
		 * this.scan.close(); switch(command){ case "afndtoafd":
		 * readAutomata(aux_file); break; case "afndepsolon":
		 * readNFAEpsolon(aux_file); break; case "grtoaf": readRGtoFA(aux_file);
		 * break; case "aftogr": readFAtoRG(aux_file); break; case "ertoaf":
		 * readREtoFA(aux_file); break; default: readFAtoRE(aux_file); break; }
		 * } catch (FileNotFoundException e) {
		 * System.out.println("Error: File not found!"); e.printStackTrace(); }
		 */
	}

	public Automata readAutomata(File file) throws InvalidStateException,
			IllegalStartOfText, IllegalOrderOfTextStructure,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		try {
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
						System.out.println("state : " + aux);
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
					 * Source of errors and bugs, correct later. The next line
					 * can be END or something else, but a state. Or it can be
					 * an state that does not belong to the automata.
					 */
					aux = this.scan.nextLine();
					while (!end) {
						aux.trim();
						System.out.println("accept : " + aux);
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
						 * If it has 4 items, it means that it has some char at
						 * the end (at least we hope). It would be good to have
						 * a code to check if that char belongs to the alphabet
						 * automata. PS: Whitespaces count.
						 */
						if (details.length == 3) {
							trans = details[2].charAt(0);
							this.automataBuilder.addTransition(from, to, trans);
						} else {
							System.out.println("from " + from);
							System.out.println("to " + to);
							this.automataBuilder.addEmptyTransition(from, to);
						}

						aux = this.scan.nextLine();
						if (aux.equals("END")) {
							end = true;
						}
					}
				} else {
					throw new IllegalOrderOfTextStructure();
				}
				theresMoreLine = false;

			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found! Try again.");
			e.printStackTrace();
		}
		this.scan.close();
		return automataBuilder.build();
	}

	public RegularExpression readRegularExpression(File file)
			throws IllegalStartOfText, IllegalRegularExpressionException {
		File aux_file = file;
		RegularExpression returnValue = null;
		try {
			this.scan = new Scanner(aux_file);
			String aux = scan.nextLine();
			aux.trim();
			String checker = aux.trim();
			while (checker.length() == 0) {
				aux = this.scan.nextLine();
				checker = aux.trim();
			}
			if (aux.equals("REGULAREXPRESSION")) {
				aux = scan.nextLine();
				while (checker.length() == 0) {
					aux = this.scan.nextLine();
					checker = aux.trim();
				}
				aux.trim();
				returnValue = StringToRE.stringToRE(aux);
			} else {
				throw new IllegalStartOfText();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found! Try again.");
			e.printStackTrace();
		}

		this.scan.close();
		return returnValue;
	}

	public RegularGrammar readRegularGrammar(File file)
			throws IllegalStartOfText, IllegalTextStructure,
			IllegalOrderOfTextStructure, StartSymbolMissingException,
			NonTerminalMissingException {
		File aux_file = file;
		try {
			this.scan = new Scanner(aux_file);
			String aux = this.scan.nextLine();

			if (aux.equals("INITIALSYMBOL")) {
				aux = this.scan.nextLine();
				this.regularBuilder.addNonTerminal(aux);
				regularBuilder.markStartSymbol(regularBuilder
						.getNonTerminalOf(aux));
				this.scan.nextLine();
				if (aux.equals("END")) {
					aux = this.scan.next();
				} else {
					throw new IllegalTextStructure();
				}
			} else {
				throw new IllegalStartOfText();
			}
			if (aux.equals("TRANSITIONS")) {
				boolean end = false;
				while (!end) {

				}
			} else {
				throw new IllegalOrderOfTextStructure();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found! Try again.");
			e.printStackTrace();
		}

		this.scan.close();
		return regularBuilder.createGrammar();
	}
}