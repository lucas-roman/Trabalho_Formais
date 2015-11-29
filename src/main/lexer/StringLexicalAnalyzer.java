package main.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import main.fileio.AutomataIO;
import main.fileio.FileToString;
import main.fileio.LangReader;
import main.fileio.exceptions.IllegalOrderOfTextStructure;
import main.fileio.exceptions.IllegalStartOfText;
import main.fileio.exceptions.IllegalStructureOfText;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

public class StringLexicalAnalyzer implements ILexicalAnalyzer {

	private String fileContents;
	private Automata analyzer;

	public StringLexicalAnalyzer(String fileName, Automata analyzer)
			throws FileNotFoundException {
		this.fileContents = new FileToString().readFile(fileName);
		this.analyzer = analyzer;
	}

	public StringLexicalAnalyzer(String fileName, String langSpecificationFile)
			throws IllegalStructureOfText, IllegalRegularExpressionException,
			MissingStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException,
			DeterministicException, FileNotFoundException,
			UnsupportedEncodingException {
		this.fileContents = new FileToString().readFile(fileName);
		LangReader reader = new LangReader();
		List<Lexema> lex = reader.readFile(new File(langSpecificationFile));
		LanguageBuilder language = new LanguageBuilder(lex);
		this.analyzer = language.lexicalAutomata();
		AutomataIO autIO = new AutomataIO();
		autIO.writeAutomata(analyzer, "lang.aut");
	}

	public StringLexicalAnalyzer(String fileName) throws InvalidStateException,
			IllegalStartOfText, IllegalOrderOfTextStructure,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException, FileNotFoundException {
		this.fileContents = new FileToString().readFile(fileName);
		AutomataIO autIO = new AutomataIO();
		this.analyzer = autIO.readAutomata(new File("lang.aut"));
	}

	@Override
	public List<LexicalToken> analyze() {
		int previous = 0;
		List<LexicalToken> returnList = new ArrayList<>();
		String prevToken = "";
		String read = "";
		for (int i = 0; i < fileContents.length(); i++) {
			String toAnalyze = fileContents.substring(previous, i);
			String token = "";

			try {
				token = analyzer.tagOfWord(toAnalyze);
			} catch (NonDeterministicException e) {
			}
			if (token.equals("NOTRANSITION")) {
				LexicalToken lexToken = new LexicalToken(prevToken, read);
				previous = i - 1;
				i--;
				returnList.add(lexToken);
			}
			read = fileContents.substring(previous, i);
			prevToken = token;
		}
		if (!prevToken.equals("ERROR") && !prevToken.equals("NOTRANSITION")) {
			LexicalToken lexToken = new LexicalToken(prevToken, read);
			returnList.add(lexToken);
		}
		return returnList;
	}

}
