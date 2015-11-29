/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.commandline.main;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import main.analyzer.Control;
import main.fileio.ParseTreeWriter;
import main.fileio.exceptions.IllegalOrderOfTextStructure;
import main.fileio.exceptions.IllegalStartOfText;
import main.fileio.exceptions.IllegalStructureOfText;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;
import main.parser.grammar.ParseTree;
import main.parser.grammar.exceptions.InvalidSentenceException;
import main.parser.grammar.exceptions.NonDeterministicGrammarException;
import main.parser.grammar.exceptions.NotLLLanguageException;
import main.parser.grammar.exceptions.TerminalMissingException;

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
		Control control;
		switch (args.length) {
		case 0:
		case 1:
			System.err
					.println("Should have at least two argument. Aborting...");
			System.exit(1);
			break;
		case 2:
			System.out.println("Reading language from default file: lang.aut");
			control = new Control(args[1]);
			try {
				ParseTree tree = control.analyze(args[0]);
				System.out.println("Printing result to analyze.out");
				ParseTreeWriter.writeTree(tree, "analyze.out");
			} catch (UnsupportedEncodingException e1) {
				System.err.println("Invalid file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (FileNotFoundException e1) {
				System.err.println("Couldn't find lang.aut. Please call this program with 3 arguments to analyze and create it.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalStructureOfText e1) {
				System.err.println("Invalid file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalRegularExpressionException e1) {
				System.err.println("Invalid language file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (MissingStateException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (InvalidStateException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (InitialStateMissingException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalAutomataException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (DeterministicException e1) {
				System.err.println("Unknown error.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalStartOfText e1) {
				System.err.println("Invalid text file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalOrderOfTextStructure e1) {
				System.err.println("Invalid text file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (NotLLLanguageException e1) {
				System.err.println("The grammar passed as argument is not LL.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (NonDeterministicGrammarException e1) {
				System.err.println("The grammar passed as argument is non deterministic.");
				System.err.println(e1.printerr());
				System.err.println("Aborting.");
				System.exit(1);
				System.exit(1);
			} catch (TerminalMissingException e1) {
				System.err.println("One of the terminals were missing from this grammar.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (InvalidSentenceException e1) {
				System.err.println("Invalid sentence on file " + args[0] + ".");
				System.err.println("Aborting.");
				System.exit(1);
			}
			break;
		default:
			System.out.println("Reading language from file : " + args[1]);
			System.out
					.println("Writing automata to : lang.aut. To use same language, please call this program with only "
							+ "one argument.");
			control = new Control(args[1], args[2]);
			try {
				ParseTree tree = control.analyze(args[0]);
				System.out.println("Printing result to analyze.out");
				ParseTreeWriter.writeTree(tree, "analyze.out");
			} catch (UnsupportedEncodingException e1) {
				System.err.println("Invalid file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (FileNotFoundException e1) {
				System.err.println("Couldn't find lang.aut. Please call this program with 3 arguments to analyze and create it.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalStructureOfText e1) {
				System.err.println("Invalid file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalRegularExpressionException e1) {
				System.err.println("Invalid language file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (MissingStateException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (InvalidStateException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (InitialStateMissingException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalAutomataException e1) {
				System.err.println("Invalid lang.aut");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (DeterministicException e1) {
				System.err.println("Unknown error.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalStartOfText e1) {
				System.err.println("Invalid text file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (IllegalOrderOfTextStructure e1) {
				System.err.println("Invalid text file.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (NotLLLanguageException e1) {
				System.err.println("The grammar passed as argument is not LL.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (NonDeterministicGrammarException e1) {
				System.err.println("The grammar passed as argument is non deterministic.");
				System.err.println(e1.printerr());
				System.err.println("Aborting.");
				System.exit(1);
				System.exit(1);
			} catch (TerminalMissingException e1) {
				System.err.println("One of the terminals were missing from this grammar.");
				System.err.println("Aborting.");
				System.exit(1);
			} catch (InvalidSentenceException e1) {
				System.err.println("Invalid sentence on file " + args[0] + ".");
				System.err.println("Aborting.");
				System.exit(1);
			}
		}
	}
}
