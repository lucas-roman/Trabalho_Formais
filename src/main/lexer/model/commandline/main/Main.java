/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.model.commandline.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

import main.lexer.LexicalAnalyzer;
import main.lexer.LexicalToken;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.model.commandline.TokenWriter;
import main.lexer.model.commandline.exceptions.IllegalOrderOfTextStructure;
import main.lexer.model.commandline.exceptions.IllegalStartOfText;
import main.lexer.model.commandline.exceptions.IllegalStructureOfText;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

/*
 * This is the main class.
 * This class receives at least one argument:
 * 	args[0]: This argument has to be the path of the source code to be analyzed or
 *  the path of the language file that will have your automata created.
 *  args[1]: This will be the path of the automata that represents the language.
 *
 * In order to make this work, some steps need to be followed:
 * 		1. The first argument has to be the path OF THE LANGUAGE and the second
 * 			argument will be the path of output for the automata of the language
 * 			that will be created. The automata file will have the .aut extension.
 * 		2. Now you can run the program with one argument. That argument has to be the
 * 			path of the source code that you want to analyze.
 *
 * Explanation: When we tell the program to read an file and send just one argument,
 * 	it will understand that we want to analyze the 'lang.aut'. But to a analyze that file,
 *  we need to create it first. In order to that, we have to call the program with two arguments
 *  instead of one.
 */
public class Main {

	public static void main(String[] args) {
		switch (args.length) {
		case 0:
			System.err.println("Should have at least one argument. Aborting...");
			System.exit(1);
			break;
		case 1:
			try {
				System.out.println("Reading language from default file: lang.aut");
				System.out.println("To build lang.aut, please provide path of language as second argument.");
				LexicalAnalyzer analyzer = new LexicalAnalyzer(args[0]);
				List<LexicalToken> tokens = analyzer.analyze();
				System.out.println("Printing analyze result to file: analyze.out");
				TokenWriter writer = new TokenWriter("analyze.out");
				writer.writeToken(tokens);
				System.exit(0);
			} catch (InvalidStateException | IllegalStartOfText
					| IllegalOrderOfTextStructure | MissingStateException
					| InitialStateMissingException | IllegalAutomataException e) {
				System.err.println("Unexpected error. Aborting...");
				System.exit(1);
			} catch (FileNotFoundException e) {
				System.out.println("Couldn't find file lang.aut. Please supply path to language file.");
				Scanner scan = new Scanner(System.in);
				try {
					LexicalAnalyzer analyzer = new LexicalAnalyzer(args[0], scan.nextLine());
					List<LexicalToken> tokens = analyzer.analyze();
					System.out.println("Printing analyze result to file: analyze.out");
					TokenWriter writer = new TokenWriter("analyze.out");
					writer.writeToken(tokens);
					System.exit(0);
				} catch (FileNotFoundException | UnsupportedEncodingException
						| IllegalStructureOfText
						| IllegalRegularExpressionException
						| MissingStateException | InvalidStateException
						| InitialStateMissingException
						| IllegalAutomataException | DeterministicException e1) {
					System.err.println("Unexpected error. Aborting...");
					System.exit(1);
				}
				finally {
					scan.close();
				}
			}
			break;
		default:
			System.out.println("Reading language from file : " + args[1]);
			System.out.println("Writing automata to : lang.aut. To use same language, please call this program with only " +
							"one argument.");
			try {
				LexicalAnalyzer analyzer = new LexicalAnalyzer(args[0], args[1]);
				List<LexicalToken> tokens = analyzer.analyze();
				System.out.println("Printing analyze result to file: analyze.out");
				TokenWriter writer = new TokenWriter("analyze.out");
				writer.writeToken(tokens);
				System.exit(0);
			} catch (UnsupportedEncodingException
					| IllegalStructureOfText
					| IllegalRegularExpressionException | MissingStateException
					| InvalidStateException | InitialStateMissingException
					| IllegalAutomataException | DeterministicException | FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Unexpected error. Aborting...");
				System.exit(1);
			}
		}
	}
}
