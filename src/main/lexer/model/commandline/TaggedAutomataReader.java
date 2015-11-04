/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */
package main.lexer.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.model.commandline.fileutils.exceptions.IllegalOrderOfTextStructure;
import main.model.commandline.fileutils.exceptions.IllegalStartOfText;

class TaggedAutomataReader {
	private Scanner scan;
	private AutomataBuilder automataBuilder;

	public Automata readTaggedAutomata(File file) throws InvalidStateException, IllegalStartOfText, IllegalOrderOfTextStructure, MissingStateException, InitialStateMissingException,IllegalAutomataException {
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
			e.printStackTrace();
		}
		this.scan.close();
		return automataBuilder.build();
	}
}
