/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package test.lexer.automata;

import java.util.ArrayList;
import java.util.List;

import main.lexer.automata.Automata;
import main.lexer.automata.DeterministicAutomata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.StringToRE;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

/*
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

public class TestAutomata {

	private Automata deterministicAutomata;
	private Automata nonDeterministicAutomata;

	/*
	 * Creates deterministic automata using AutomataBuilder class
	 */
	@Before
	public void initDeterministic() throws InvalidStateException,
			MissingStateException, OverrideInitialStateException,
			InitialStateMissingException, IllegalAutomataException {
		AutomataBuilder deterministicBuilder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		deterministicBuilder.addState("q0").addState("q1").addState("q2");
		deterministicBuilder.markAcceptState("q0");
		deterministicBuilder.addState("q3").addState("q4");
		deterministicBuilder.markAcceptState("q1");
		deterministicBuilder.addTransition("q0", "q1", 'a');
		deterministicBuilder.addTransition("q0", "q3", 'b');
		deterministicBuilder.addTransition("q1", "q1", 'a');
		deterministicBuilder.addTransition("q1", "q2", 'b');
		deterministicBuilder.addTransition("q2", "q1", 'a');
		deterministicBuilder.addTransition("q2", "q2", 'b');
		deterministicBuilder.addTransition("q3", "q4", 'a');
		deterministicBuilder.addTransition("q3", "q3", 'b');
		deterministicBuilder.addTransition("q4", "q4", 'a');
		deterministicBuilder.addTransition("q4", "q3", 'b');
		deterministicBuilder.markAcceptState("q3");
		deterministicAutomata = deterministicBuilder.build();
	}

	@Before
	public void initNonDeterministic() throws InvalidStateException,
			MissingStateException, OverrideInitialStateException,
			InitialStateMissingException, IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("q0").addState("S").addState("A").addState("B")
				.addState("F");
		builder.markAcceptState("q0");
		builder.markAcceptState("F");
		builder.addEmptyTransition("q0", "S");
		builder.addTransition("S", "A", 'a');
		builder.addTransition("S", "F", 'a');
		builder.addTransition("S", "F", 'b');
		builder.addTransition("S", "B", 'b');
		builder.addTransition("A", "A", 'a');
		builder.addTransition("A", "A", 'b');
		builder.addTransition("A", "F", 'a');
		builder.addTransition("B", "B", 'a');
		builder.addTransition("B", "B", 'b');
		builder.addTransition("B", "F", 'b');
		nonDeterministicAutomata = builder.build();

	}

	@After
	public void clean() {
		deterministicAutomata = null;
		nonDeterministicAutomata = null;
	}

	@Test
	public void testAcceptDeterministic() {
		Assert.assertTrue(deterministicAutomata.accepts("abba"));
		Assert.assertTrue(deterministicAutomata.accepts("baabbababbab"));
		Assert.assertTrue(deterministicAutomata.accepts("a"));
		Assert.assertTrue(deterministicAutomata.accepts("b"));
		Assert.assertTrue(deterministicAutomata.accepts("aa"));
		Assert.assertTrue(deterministicAutomata.accepts("bb"));
		Assert.assertFalse(deterministicAutomata.accepts("ab"));
		Assert.assertFalse(deterministicAutomata.accepts("ba"));
		Assert.assertFalse(deterministicAutomata.accepts("abababaaab"));
		Assert.assertFalse(deterministicAutomata.accepts("bababaaaaba"));
		Assert.assertTrue(deterministicAutomata.accepts(""));
	}

	@Test
	public void testAccept() {
		Assert.assertTrue(nonDeterministicAutomata.accepts(""));
		Assert.assertTrue(nonDeterministicAutomata.accepts("a"));
		Assert.assertTrue(nonDeterministicAutomata.accepts("b"));
		Assert.assertTrue(nonDeterministicAutomata.accepts("abba"));
		Assert.assertTrue(nonDeterministicAutomata.accepts("baabbababbab"));
		Assert.assertTrue(nonDeterministicAutomata.accepts("aa"));
		Assert.assertTrue(nonDeterministicAutomata.accepts("bb"));
		Assert.assertFalse(nonDeterministicAutomata.accepts("ab"));
		Assert.assertFalse(nonDeterministicAutomata.accepts("ba"));
		Assert.assertFalse(nonDeterministicAutomata.accepts("abababaaab"));
		Assert.assertFalse(nonDeterministicAutomata.accepts("bababaaaaba"));
	}

	@Test
	public void testNonDeterministicToDeterministic()
			throws DeterministicException {
		Automata det = nonDeterministicAutomata.convert();
		Assert.assertTrue(det instanceof DeterministicAutomata);
		Assert.assertTrue(det.accepts(""));
		Assert.assertTrue(det.accepts("a"));
		Assert.assertTrue(det.accepts("b"));
		Assert.assertTrue(det.accepts("abba"));
		Assert.assertTrue(det.accepts("baabbababbab"));
		Assert.assertTrue(det.accepts("aa"));
		Assert.assertTrue(det.accepts("bb"));
		Assert.assertFalse(det.accepts("ab"));
		Assert.assertFalse(det.accepts("ba"));
		Assert.assertFalse(det.accepts("abababaaab"));
		Assert.assertFalse(det.accepts("bababaaaaba"));
	}

	@Test(expected = DeterministicException.class)
	public void testFailDeterministicToDeterministicConvert()
			throws DeterministicException {
		deterministicAutomata.convert();
	}

	@Test
	public void testSize() throws InitialStateMissingException,
			IllegalAutomataException, MissingStateException,
			InvalidStateException, OverrideInitialStateException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("q0");
		builder.addState("q1");
		builder.addState("q2");
		builder.addState("q3");
		builder.addState("q4");
		builder.addState("q5");
		builder.addTransition("q0", "q1", 'a');
		builder.addTransition("q0", "q2", 'b');
		builder.addTransition("q1", "q2", 'a');
		builder.addTransition("q2", "q3", 'a');
		builder.addTransition("q3", "q4", 'a');
		builder.addTransition("q4", "q5", 'a');
		builder.addTransition("q5", "q1", 'a');
		Automata aut = builder.build();
		Assert.assertEquals(aut.getStates().size(), 6);
		builder.addState("q6");
		builder.addState("q7");
		builder.addState("q8");
		builder.addTransition("q0", "q6", 'a');
		builder.addEmptyTransition("q0", "q7");
		builder.addTransition("q7", "q8", 'a');
		aut = builder.build();
		Assert.assertEquals(aut.getStates().size(), 9);
	}

	@Test
	public void testMinimization() throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException, IllegalRegularExpressionException,
			DeterministicException {
		String stringToRE2 = "aab*(ab)?";
		RegularExpression re = StringToRE.stringToRE(stringToRE2);
		Automata test = re.createAutomata().convert();
		Assert.assertTrue(test.accepts("aabbbbb"));
		Assert.assertTrue(test.accepts("aabbbbab"));
		Assert.assertTrue(test.accepts("aaab"));
		Assert.assertTrue(test.accepts("aa"));
		Assert.assertFalse(test.accepts(""));
		Assert.assertFalse(test.accepts("aaabb"));
		Assert.assertFalse(test.accepts("aaabbab"));
		test = test.minimize();
		Assert.assertTrue(test.accepts("aabbbbb"));
		Assert.assertTrue(test.accepts("aabbbbab"));
		Assert.assertTrue(test.accepts("aaab"));
		Assert.assertTrue(test.accepts("aa"));
		Assert.assertFalse(test.accepts(""));
		Assert.assertFalse(test.accepts("aaabb"));
		Assert.assertFalse(test.accepts("aaabbab"));
	}

	@Test
	public void testTag() throws IllegalRegularExpressionException,
			MissingStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException, DeterministicException {
		RegularExpression re = StringToRE.stringToRE("if");
		List<String> order = new ArrayList<String>();
		order.add(0, "KEYWORD");
		order.add(1, "ID");
		Automata aut = re.createAutomata();
		aut = aut.convert();
		aut = aut.minimize();
		for(AutomataState acceptState : aut.acceptStates()) {
			acceptState.setTag("KEYWORD");
		}

		re = StringToRE.stringToRE("(a|b|c|d|e|f|g|h|i|j|k|l|m|n|o|p|q|r|s|t|u|v|w|x|y|z)+");
		Automata other = re.createAutomata();
		Assert.assertTrue(other.accepts("iff"));
		other = other.convert();
		other = other.minimize();
		Assert.assertTrue(other.accepts("iff"));
		for(AutomataState acceptState : other.acceptStates()) {
			acceptState.setTag("ID");
		}
		aut = aut.union(other);
		Assert.assertTrue(aut.accepts("iff"));
		aut.addTagOrder(order);
		aut = aut.convert();
		for(AutomataState state : aut.acceptStates()) {
			System.out.println(state.getTag());
		}
	}

}