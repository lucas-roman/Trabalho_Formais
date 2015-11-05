package test.lexer;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import main.lexer.LexicalAnalyzer;
import main.lexer.LexicalToken;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.model.commandline.TokenWriter;
import main.lexer.model.commandline.exceptions.IllegalStructureOfText;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

import org.junit.Test;

public class TestLexer {

	@Test
	public void testLexicalAnalyses() throws FileNotFoundException,
			UnsupportedEncodingException, IllegalStructureOfText,
			IllegalRegularExpressionException, MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException, DeterministicException {
		LexicalAnalyzer analyzer = new LexicalAnalyzer("test",
				"lang/pikachu.ash");
		List<LexicalToken> outputList = analyzer.analyze();
		TokenWriter writer = new TokenWriter("analyzed.out");
		writer.writeToken(outputList);
	}

}
