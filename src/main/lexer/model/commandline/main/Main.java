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

import main.lexer.StringLexicalAnalyzer;
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
 * 	args[0]: This argument has to be the path of the source code to be analyzed.
 *  args[1]: This will be the path of the file that represents the language.
 *
 *	After running the AnalisadorLexico.jar, the program will create a lang.aut file and
 * and a analyze.out file. The 'lang.aut' file represents the automata of the language.
 * It is created for a better performance for future executions of the program.
 * The analyze.out has the tokens of the source code.
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
				StringLexicalAnalyzer analyzer = new StringLexicalAnalyzer(args[0]);
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
					StringLexicalAnalyzer analyzer = new StringLexicalAnalyzer(args[0], scan.nextLine());
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
				StringLexicalAnalyzer analyzer = new StringLexicalAnalyzer(args[0], args[1]);
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
