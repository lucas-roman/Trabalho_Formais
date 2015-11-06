/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package test.lexer;

import java.util.ArrayList;
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
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

import org.junit.Test;

public class TestLanguage {

	@Test
	public void testLanguage() throws IllegalRegularExpressionException,
			MissingStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException,
			DeterministicException, NonDeterministicException {
		Lexema lex1 = new Lexema("KEYWORD", "if|end|begin|or|and");
		Lexema lex2 = new Lexema("NUMBER", "(0|1|2|3|4|5|6|7|8|9)+");
		Lexema lex3 = new Lexema("TYPE", "int|string|float");
		Lexema lex4 = new Lexema("ID",
				"(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)+");
		List<Lexema> myAwesomeList = new ArrayList<>();
		myAwesomeList.add(lex1);
		myAwesomeList.add(lex2);
		myAwesomeList.add(lex3);
		myAwesomeList.add(lex4);
		LanguageBuilder lBuilder = new LanguageBuilder(myAwesomeList);
		Automata recAutomata = lBuilder.lexicalAutomata();
		Assert.assertEquals("KEYWORD", recAutomata.tagOfWord("if"));
		Assert.assertEquals("ID", recAutomata.tagOfWord("iff"));
		Assert.assertEquals("NUMBER", recAutomata.tagOfWord("131"));
		Assert.assertEquals("TYPE", recAutomata.tagOfWord("int"));
		Assert.assertEquals("ERROR", recAutomata.tagOfWord("int321"));
	}

}