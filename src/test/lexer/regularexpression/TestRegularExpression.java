package test.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.regularexpression.RegularExpression;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

public class TestRegularExpression {

	private String regularExpressionValue1 = "(a|b)*(dÂª)";

	private String regularExpressionValue2 = "Â°";

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
		RegularExpression val1 = rea.alternation(reb).parenthesis().kleene()
				.concatenate(red.concatenate(reEmptyWord).parenthesis());
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
	}

	@Test
	public void testParenthesisRegularExceptionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.parenthesis();
		Automata aut = re.createAutomata();
		Assert.assertFalse(aut.accepts(""));
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertFalse(aut.accepts("aa"));
		Assert.assertFalse(aut.accepts("b"));
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

}
