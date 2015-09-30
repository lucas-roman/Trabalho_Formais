package test.lexer.regularexpression;

import junit.framework.Assert;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.regularexpression.RegularExpression;

import org.junit.Test;

public class TestRegularExpression {

	private String regularExpressionValue1 = "(a|b)*(dª)";

	private String regularExpressionValue2 = "°";

	private RegularExpression rea = RegularExpression
			.createRegularExpression('a');
	private RegularExpression reb = RegularExpression
			.createRegularExpression('b');
	private RegularExpression red = RegularExpression
			.createRegularExpression('d');
	private RegularExpression reEmptyWord = RegularExpression
			.createRegularExpression('\0');
	private RegularExpression reEmpty = RegularExpression
			.emptyRegularExpression();
	

	@Test
	public void testStringValue() {
		RegularExpression val1 = rea.union(reb).brace().closure()
				.concatenate(red.concatenate(reEmptyWord).brace());
		Assert.assertEquals(regularExpressionValue1, val1.toString());
		Assert.assertEquals(regularExpressionValue2, reEmpty.toString());
	}

	@Test
	public void testSimpleAutomataRegularExpression()
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
	public void testSimpleAutomataUnionRegularExpression()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.union(RegularExpression.createRegularExpression('b'));
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
	public void testSimpleAutomataConcatRegularExpression()
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
	public void testSimpleAutomataClosureRegularExpression()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.closure();
		Automata aut = re.createAutomata();
		Assert.assertTrue(aut.accepts(""));
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertTrue(aut.accepts("aa"));
		Assert.assertTrue(aut.accepts("aaaaaaaaaa"));
		Assert.assertFalse(aut.accepts("b"));
	}

	@Test
	public void testSimpleAutomataEmptyRegularExpression()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.emptyRegularExpression();
		Automata aut = re.createAutomata();
		Assert.assertFalse(aut.accepts(""));
		Assert.assertFalse(aut.accepts("a"));
	}

	@Test
	public void testSimpleAutomataEmptyWordRegularExpression()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('\0');
		Automata aut = re.createAutomata();
		Assert.assertTrue(aut.accepts(""));
		Assert.assertFalse(aut.accepts("a"));
	}

	@Test
	public void testBracedRegularExceptionAutomata()
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException,
			OverrideInitialStateException {
		RegularExpression re = RegularExpression.createRegularExpression('a')
				.brace();
		Automata aut = re.createAutomata();
		Assert.assertFalse(aut.accepts(""));
		Assert.assertTrue(aut.accepts("a"));
		Assert.assertFalse(aut.accepts("aa"));
		Assert.assertFalse(aut.accepts("b"));
	}

}
