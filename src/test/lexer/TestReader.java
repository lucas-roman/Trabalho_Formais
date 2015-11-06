/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package test.lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import junit.framework.Assert;
import main.lexer.LanguageBuilder;
import main.lexer.Lexema;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.model.commandline.AutomataIO;
import main.lexer.model.commandline.LangReader;
import main.lexer.model.commandline.exceptions.IllegalOrderOfTextStructure;
import main.lexer.model.commandline.exceptions.IllegalStartOfText;
import main.lexer.model.commandline.exceptions.IllegalStructureOfText;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

import org.junit.Test;

public class TestReader {

	@Test
	public void testLanguageReader() throws IllegalStructureOfText,
			IllegalRegularExpressionException, MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException, DeterministicException,
			FileNotFoundException, UnsupportedEncodingException,
			IllegalStartOfText, IllegalOrderOfTextStructure, NonDeterministicException {
		LangReader lang = new LangReader();
		List<Lexema> myAdorableList = lang
				.readFile(new File("lang/pikachu.ash"));
		LanguageBuilder builder = new LanguageBuilder(myAdorableList);
		Automata retAut = builder.lexicalAutomata();
		AutomataIO iO = new AutomataIO();
		iO.writeAutomata(retAut, "lang.OUT");
		retAut = iO.readAutomata(new File("lang.OUT"));
		Assert.assertEquals("ARRAY", retAut.tagOfWord("abacate[dinheiro]"));
		Assert.assertEquals("SYMBOL", retAut.tagOfWord("="));
		Assert.assertEquals("KEYWORD", retAut.tagOfWord("program"));
		Assert.assertEquals("KEYWORD", retAut.tagOfWord("end"));
		Assert.assertEquals("ID", retAut.tagOfWord("_acerolar"));
		Assert.assertEquals("ID", retAut.tagOfWord("acerola"));
		Assert.assertEquals("ERROR", retAut.tagOfWord("312a"));
	}

}
