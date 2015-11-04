package test.lexer;

import java.io.File;
import java.util.List;

import main.lexer.Lexema;
import main.lexer.model.commandline.LangReader;
import main.lexer.model.commandline.exceptions.IllegalStructureOfText;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

import org.junit.Test;

public class TestReader {

	@Test
	public void testLanguageReader() throws IllegalStructureOfText, IllegalRegularExpressionException {
		LangReader lang = new LangReader();
		List<Lexema> myAdorableList = lang.readFile(new File("lang/pikachu.ash"));
	}
	
}
