package main.analyzer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

import main.fileio.ContextFreeGrammarIO;
import main.fileio.exceptions.IllegalOrderOfTextStructure;
import main.fileio.exceptions.IllegalStartOfText;
import main.fileio.exceptions.IllegalStructureOfText;
import main.lexer.LexicalToken;
import main.lexer.StringLexicalAnalyzer;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;
import main.parser.grammar.Analyzer;
import main.parser.grammar.ContextFreeGrammar;
import main.parser.grammar.ParseTree;
import main.parser.grammar.exceptions.InvalidSentenceException;
import main.parser.grammar.exceptions.NonDeterministicGrammarException;
import main.parser.grammar.exceptions.NotLLLanguageException;
import main.parser.grammar.exceptions.TerminalMissingException;

public class Control {

	private String langSpecificationFile = "";
	private String grammarSpecificationFile;

	public Control(String langSpecificationFile, String grammarSpecificationFile) {
		this.langSpecificationFile = langSpecificationFile;
		this.grammarSpecificationFile = grammarSpecificationFile;
	}

	public Control(String grammarSpecificationFile) {
		this.grammarSpecificationFile = grammarSpecificationFile;
	}

	public ParseTree analyze(String path) throws UnsupportedEncodingException,
			IllegalStructureOfText, IllegalRegularExpressionException,
			MissingStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException,
			DeterministicException, FileNotFoundException, IllegalStartOfText,
			IllegalOrderOfTextStructure, NotLLLanguageException, NonDeterministicGrammarException, TerminalMissingException, InvalidSentenceException {
		StringLexicalAnalyzer lexical;
		if (langSpecificationFile.equals("")) {
			try {
				lexical = new StringLexicalAnalyzer(path);
			} catch (FileNotFoundException e) {
				System.out
						.println("Couldn't find file lang.aut. Please supply path to language file.");
				Scanner scan = new Scanner(System.in);
				String scanned = scan.nextLine();
				scan.close();
				lexical = new StringLexicalAnalyzer(path, scanned);
			}
		} else {
			lexical = new StringLexicalAnalyzer(path, langSpecificationFile);
		}
		List<LexicalToken> tokens = lexical.analyze();
		ContextFreeGrammar grammar = new ContextFreeGrammarIO().readGrammar(grammarSpecificationFile);
		Analyzer analyzer = new Analyzer(grammar, tokens);
		if(!analyzer.analyze()) {
			throw new InvalidSentenceException();
		}
		return analyzer.getParseTree();
	}

}
