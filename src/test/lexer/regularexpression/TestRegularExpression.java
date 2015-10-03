package test.lexer.regularexpression;

import junit.framework.Assert;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.StringToRE;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

import org.junit.Test;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

public class TestRegularExpression {

	private String regularExpressionValue1 = "(((a|b))*d)";

	private String regularExpressionValue2 = "";

	private RegularExpression rea = RegularExpression
			.createRegularExpression('a');
	private RegularExpression reb = RegularExpression
			.createRegularExpression('b');
	private RegularExpression red = RegularExpression
			.createRegularExpression('d');
	private RegularExpression reEmptyWord = RegularExpression
			.createRegularExpression('\0');
	private RegularExpression reEmpty = RegularExpression
			.emptySetRegularExpression();

	@Test
	public void testStringValue() {
		RegularExpression val1 = rea.alternation(reb).kleene()
				.concatenate(red.concatenate(reEmptyWord));
		Assert.assertEquals(regularExpressionValue1, val1.toString());
		Assert.assertEquals(regularExpressionValue2, reEmpty.toString());
	}

	@Test
	public void testLiteralRegularExpressionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a');
		Automata aut = re.createAutomata();
		Assert.assertFalse(aut.accepts(""));
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertFalse(aut.accepts("aa"));
		Assert.assertFalse(aut.accepts("b"));
	}

	@Test
	public void testAlternationRegularExpressionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.alternation(RegularExpression.createRegularExpression('b'));
		Automata aut = re.createAutomata();
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertTrue(aut.accepts("b"));
		Assert.assertFalse(aut.accepts("bb"));
		Assert.assertFalse(aut.accepts("aa"));
		Assert.assertFalse(aut.accepts("ba"));
		Assert.assertFalse(aut.accepts("ab"));
		Assert.assertFalse(aut.accepts(""));
	}

	@Test
	public void testConcatenateRegularExpressionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.concatenate(RegularExpression.createRegularExpression('b'));
		Automata aut = re.createAutomata();
		Assert.assertFalse(aut.accepts(""));
		Assert.assertTrue(aut.accepts("ab"));
		Assert.assertFalse(aut.accepts("a"));
		Assert.assertFalse(aut.accepts("b"));
		Assert.assertFalse(aut.accepts("ba"));
	}

	@Test
	public void testKleeneRegularExpressionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.kleene();
		Automata aut = re.createAutomata();
		Assert.assertTrue(aut.accepts(""));
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertTrue(aut.accepts("aa"));
		Assert.assertTrue(aut.accepts("aaaaaaaaaa"));
		Assert.assertFalse(aut.accepts("b"));
	}

	@Test
	public void testEmptySetRegularExpressionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.emptySetRegularExpression();
		Automata aut = re.createAutomata();
		Assert.assertFalse(aut.accepts(""));
		Assert.assertFalse(aut.accepts("a"));
	}

	@Test
	public void testEmptyStringRegularExpressionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('\0');
		Automata aut = re.createAutomata();
		Assert.assertTrue(aut.accepts(""));
		Assert.assertFalse(aut.accepts("a"));
		re = re.concatenate(RegularExpression.createRegularExpression('a'));
		aut = re.createAutomata();
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertFalse(aut.accepts(""));
		Assert.assertFalse(aut.accepts("aa"));
		re = RegularExpression.createRegularExpression('a');
		re = re.concatenate(RegularExpression.createRegularExpression('\0'));
		aut = re.createAutomata();
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertFalse(aut.accepts(""));
		Assert.assertFalse(aut.accepts("aa"));
		re = RegularExpression.createRegularExpression('\0');
		re = re.alternation(RegularExpression.createRegularExpression('a'));
		aut = re.createAutomata();
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertTrue(aut.accepts(""));
		Assert.assertFalse(aut.accepts("aa"));
		re = RegularExpression.createRegularExpression('a');
		re = re.alternation(RegularExpression.createRegularExpression('\0'));
		aut = re.createAutomata();
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertTrue(aut.accepts(""));
		Assert.assertFalse(aut.accepts("aa"));
		re = RegularExpression.createRegularExpression('\0');
		re = re.kleene();
		aut = re.createAutomata();
		Assert.assertTrue(aut.accepts(""));
		Assert.assertFalse(aut.accepts("a"));
		
	}


	@Test
	public void testInterrogationRegularExpression()
			throws MissingStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.interrogation();
		Automata aut = re.createAutomata();
		Assert.assertTrue(aut.accepts(""));
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertFalse(aut.accepts("aa"));
		Assert.assertFalse(aut.accepts("b"));
	}

	@Test
	public void testPositiveRegularExpression() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.positive();
		Automata aut = re.createAutomata();
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertTrue(aut.accepts("aa"));
		Assert.assertTrue(aut.accepts("aaaaaaaaa"));
		Assert.assertFalse(aut.accepts("b"));
		Assert.assertFalse(aut.accepts(""));
	}

	@Test
	public void testStringToRe() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException, IllegalRegularExpressionException, DeterministicException {
		String stringToRE2 = "aab*(ab)?";
		String stringToRE1 = "ab*";
		RegularExpression re = StringToRE.stringToRE(stringToRE1);
		Automata test = re.createAutomata().convert();
		Assert.assertTrue(test.accepts("ab"));
		Assert.assertTrue(test.accepts("abb"));
		Assert.assertTrue(test.accepts("abbbb"));
		Assert.assertFalse(test.accepts(""));
		Assert.assertFalse(test.accepts("abab"));
		re = StringToRE.stringToRE(stringToRE2);
		test = re.createAutomata().convert();
		Assert.assertTrue(test.accepts("aabbbbb"));
		Assert.assertTrue(test.accepts("aabbbbab"));
		Assert.assertTrue(test.accepts("aaab"));
		Assert.assertTrue(test.accepts("aa"));
		Assert.assertFalse(test.accepts(""));
		Assert.assertFalse(test.accepts("aaabb"));
		Assert.assertFalse(test.accepts("aaabbab"));
		String stringToRe3 = "ab*c|d";
		re = StringToRE.stringToRE(stringToRe3);
		test = re.createAutomata().convert();
		Assert.assertTrue(test.accepts("ac"));
		Assert.assertTrue(test.accepts("abc"));
		Assert.assertTrue(test.accepts("abbbbbbbc"));
		Assert.assertTrue(test.accepts("d"));
		Assert.assertFalse(test.accepts(""));
		Assert.assertFalse(test.accepts("abcd"));
		Assert.assertFalse(test.accepts("dd"));
		
	}
	
	@Test
	public void testStringToREEpslon()
			throws IllegalRegularExpressionException, MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException, DeterministicException {
		String stringToRE = "a(a|´)b´";
		RegularExpression re = StringToRE.stringToRE(stringToRE);
		Automata test = re.createAutomata();
		Assert.assertTrue(test.accepts("aab"));
		Assert.assertTrue(test.accepts("ab"));
		Assert.assertFalse(test.accepts("abb"));
		Assert.assertFalse(test.accepts(""));
		test =test.convert();
		Assert.assertTrue(test.accepts("aab"));
		Assert.assertTrue(test.accepts("ab"));
		Assert.assertFalse(test.accepts("abb"));
		Assert.assertFalse(test.accepts(""));
	}

}
