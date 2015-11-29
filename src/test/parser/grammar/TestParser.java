package test.parser.grammar;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import main.analyzer.Control;
import main.fileio.ContextFreeGrammarIO;
import main.fileio.exceptions.IllegalOrderOfTextStructure;
import main.fileio.exceptions.IllegalStartOfText;
import main.fileio.exceptions.IllegalStructureOfText;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;
import main.parser.grammar.ContextFreeGrammar;
import main.parser.grammar.exceptions.InvalidSentenceException;
import main.parser.grammar.exceptions.NonDeterministicGrammarException;
import main.parser.grammar.exceptions.NotLLLanguageException;
import main.parser.grammar.exceptions.TerminalMissingException;

import org.junit.Test;

public class TestParser {

	@Test
	public void testParser() throws FileNotFoundException,
			IllegalStructureOfText, UnsupportedEncodingException,
			IllegalRegularExpressionException, MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException, DeterministicException,
			IllegalStartOfText, IllegalOrderOfTextStructure,
			NotLLLanguageException, NonDeterministicGrammarException,
			TerminalMissingException, InvalidSentenceException {
		Control control = new Control("numbers/lex", "numbers/parse");
		control.analyze("numbers/numberexp");
	}

}
