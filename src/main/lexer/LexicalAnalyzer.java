package main.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.model.commandline.AutomataIO;
import main.lexer.model.commandline.FileToString;
import main.lexer.model.commandline.LangReader;
import main.lexer.model.commandline.exceptions.IllegalOrderOfTextStructure;
import main.lexer.model.commandline.exceptions.IllegalStartOfText;
import main.lexer.model.commandline.exceptions.IllegalStructureOfText;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

public class LexicalAnalyzer {

	private List<String> fileContents;
	private Automata analyzer;

	public LexicalAnalyzer(String fileName, Automata analyzer) {
		this.fileContents = new FileToString().readFile(new File(fileName));
		this.analyzer = analyzer;
	}

	public LexicalAnalyzer(String fileName, String langSpecificationFile)
			throws IllegalStructureOfText, IllegalRegularExpressionException,
			MissingStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException,
			DeterministicException, FileNotFoundException,
			UnsupportedEncodingException {
		this.fileContents = new FileToString().readFile(new File(fileName));
		LangReader reader = new LangReader();
		List<Lexema> lex = reader.readFile(new File(langSpecificationFile));
		LanguageBuilder language = new LanguageBuilder(lex);
		this.analyzer = language.lexicalAutomata();
		AutomataIO autIO = new AutomataIO();
		autIO.writeAutomata(analyzer, "lang.aut");
	}

	public LexicalAnalyzer(String fileName) throws InvalidStateException,
			IllegalStartOfText, IllegalOrderOfTextStructure,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException, FileNotFoundException {
		this.fileContents = new FileToString().readFile(new File(fileName));
		AutomataIO autIO = new AutomataIO();
		this.analyzer = autIO.readAutomata(new File("lang.aut"));
	}

	public List<LexicalToken> analyze() {
		List<LexicalToken> returnList = new ArrayList<>();
		for (String word : fileContents) {
			try {
				returnList
						.add(new LexicalToken(analyzer.tagOfWord(word), word));
			} catch (NonDeterministicException e) {
				// It never happens
			}
		}
		return returnList;
	}

}
