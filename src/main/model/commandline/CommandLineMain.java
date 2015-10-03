package main.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.grammar.RegularGrammar;
import main.lexer.grammar.exceptions.NonTerminalMissingException;
import main.lexer.grammar.exceptions.StartSymbolMissingException;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;
import main.model.commandline.fileutils.Reader;
import main.model.commandline.fileutils.Writer;
import main.model.commandline.fileutils.exceptions.IllegalOrderOfTextStructure;
import main.model.commandline.fileutils.exceptions.IllegalStartOfText;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

public class CommandLineMain {


	public static void main(String[] args) {
		if(args.length < 3) {
			System.err.println("Invalid number of arguments. Expecting 3, was " + args.length + ".");
			System.err.println("Aborting...");
			System.exit(1);
		}
		String type = args[0];
		String in = args[1];
		String out = args[2];
		Reader reader = new Reader();
		Writer writer = new Writer(out);
		if(type.equals("aftoer")) {
			try {
				Automata af = reader.readAutomata(new File(in));
				RegularExpression re = RegularExpression.convertAutomataToRegularExpression(af);
				writer.writeRegularExpression(re);
			} catch (InvalidStateException e) {
				System.err.println("Attempted to mark a missing state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalStartOfText e) {
				System.err.println("Illegal start of text. Expected STATES.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalOrderOfTextStructure e) {
				System.err.println("Bad text format.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (MissingStateException e) {
				System.err.println("State missing.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (InitialStateMissingException e) {
				System.err.println("Invalid automata. No initial state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalAutomataException e) {
				System.err.println("Illegal automata. Unknown.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (UnsupportedEncodingException e) {
				System.err.println("Unsupported encoding.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
				System.err.println("Aborting...");
				System.exit(1);
			}
		}
		else if(type.equals("ertoaf")) {
			try {
				RegularExpression re = reader.readRegularExpression(new File(in));
				Automata toWrite = re.createAutomata();
				writer.writeAutomata(toWrite);
			} catch (IllegalStartOfText | IllegalRegularExpressionException e) {
				System.err.println("Bad format.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (MissingStateException e) {
				System.err.println("State missing.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (InvalidStateException e) {
				System.err.println("Attempted to mark a missing state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (InitialStateMissingException e) {
				System.err.println("Invalid automata. No initial state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalAutomataException e) {
				System.err.println("Illegal automata. Unknown.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (UnsupportedEncodingException e) {
				System.err.println("Unsupported encoding.");
				System.err.println("Aborting...");
				System.exit(1);
			}
		}
		else if(type.equals("afndtoaf")) {
			try {
				Automata inpu = reader.readAutomata(new File(in));
				Automata outp = inpu.convert();
				writer.writeAutomata(outp);
			} catch (InvalidStateException e) {
				System.err.println("Attempted to mark a missing state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalStartOfText e) {
				System.err.println("Bad format.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalOrderOfTextStructure e) {
				System.err.println("Bad format.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (MissingStateException e) {
				System.err.println("State missing.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (InitialStateMissingException e) {
				System.err.println("Invalid automata. No initial state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalAutomataException e) {
				System.err.println("Illegal automata. Unknown.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (DeterministicException e) {
				System.err.println("Bad type. Automata was already deterministic.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (UnsupportedEncodingException e) {
				System.err.println("Unsupported encoding.");
				System.err.println("Aborting...");
				System.exit(1);
			}
		}
		else if(type.equals("aftogr")) {
			try {
				Automata inpu = reader.readAutomata(new File(in));
				RegularGrammar gr = RegularGrammar.convertAutomataToGrammar(inpu);
				writer.writeGrammar(gr);
			} catch (InvalidStateException e) {
				System.err.println("Attempted to mark a missing state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalStartOfText e) {
				System.err.println("Bad format.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalOrderOfTextStructure e) {
				System.err.println("Bad format.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (MissingStateException e) {
				System.err.println("State missing.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (InitialStateMissingException e) {
				System.err.println("Invalid automata. No initial state.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (IllegalAutomataException e) {
				System.err.println("Illegal automata. Unknown.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (NonTerminalMissingException e) {
				System.err.println("Illegal grammar. Non terminal missing.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (StartSymbolMissingException e) {
				System.err.println("Illegal grammar. Missing start symbol.");
				System.err.println("Aborting...");
				System.exit(1);
			} catch (FileNotFoundException e) {
				System.err.println("File not found.");
				System.err.println("Aborting...");
				System.exit(1);
			}
		}
	}


}
