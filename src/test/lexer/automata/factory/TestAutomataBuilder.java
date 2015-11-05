/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package test.lexer.automata.factory;

import main.lexer.automata.Automata;
import main.lexer.automata.DeterministicAutomata;
import main.lexer.automata.NonDeterministicAutomata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
public class TestAutomataBuilder {

	private AutomataBuilder deterministicAutomataBuilder;
	private AutomataBuilder nonDeterministicAutomataBuilder;

	/*
	 * Initialize empty automata builder.
	 */
	@Before
	public void init() {
		deterministicAutomataBuilder = new AutomataBuilder(new AutomataStructureGraphFactory());
		nonDeterministicAutomataBuilder = new AutomataBuilder(new AutomataStructureGraphFactory());
	}

	@After
	public void clean() {
		deterministicAutomataBuilder = null;
		nonDeterministicAutomataBuilder = null;
	}

	@Test(expected = MissingStateException.class)
	public void testValidState() throws MissingStateException, InvalidStateException {
		deterministicAutomataBuilder.addTransition("invalid1", "invalid2", 'a');
	}

	@Test
	public void testDeterministicReturn() throws InvalidStateException,
			InitialStateMissingException, MissingStateException,  OverrideInitialStateException, IllegalAutomataException {
		deterministicAutomataBuilder.addState("q0");
		deterministicAutomataBuilder.addState("q1").addState("q2");
		try {
			deterministicAutomataBuilder.addTransition("q0", "q1", 'a');
			deterministicAutomataBuilder.addTransition("q1", "q2", 'b');
			Automata builtAutomata = deterministicAutomataBuilder.build();
			if (!(builtAutomata instanceof DeterministicAutomata)) {
				Assert.fail("Expected DeterministicAutomata, actual: "
						+ builtAutomata.getClass().getName());
			}
		} catch (MissingStateException e) {
			Assert.fail("Failed to initialize states");
		}
	}

	@Test
	public void testNonDeterministicReturn() throws InvalidStateException,
			InitialStateMissingException, MissingStateException,OverrideInitialStateException, IllegalAutomataException {
		nonDeterministicAutomataBuilder.addState("q0");
		nonDeterministicAutomataBuilder.addState("q1").addState("q2");
		try {
			nonDeterministicAutomataBuilder.addTransition("q0", "q1", 'a');
			nonDeterministicAutomataBuilder.addEmptyTransition("q1", "q2");
			Automata builtAutomata = nonDeterministicAutomataBuilder.build();
			if (!(builtAutomata instanceof NonDeterministicAutomata)) {
				Assert.fail("Expected NonDeterministicAutomata, actual: "
						+ builtAutomata.getClass().getName());
			}
		} catch (MissingStateException e) {
			Assert.fail("Failed to initialize states.");
		}
	}

	@Test
	public void testTwoTransitionsBySameCharacterNonDeterministic()
			throws InvalidStateException, InitialStateMissingException,
			MissingStateException, OverrideInitialStateException, IllegalAutomataException {
		AutomataBuilder testSameCharTransition = new AutomataBuilder(new AutomataStructureGraphFactory());
		testSameCharTransition.addState("q0");
		testSameCharTransition.addState("q1").addState("q2");
		try {
			testSameCharTransition.addTransition("q0", "q1", 'c');
			testSameCharTransition.addTransition("q0", "q2", 'c');
			Automata builtAutomata = testSameCharTransition.build();
			if (!(builtAutomata instanceof NonDeterministicAutomata)) {
				Assert.fail("Expected NonDeterministicAutomata, actual: "
						+ builtAutomata.getClass().getName());
			}
		} catch (MissingStateException e) {
			Assert.fail("Failed to initialize states.");
		}

	}

	@Test
	public void testAddValidState() {
		try {
			deterministicAutomataBuilder.addState("q10");
		} catch (InvalidStateException e) {
			Assert.fail("State is valid.");
		}
	}


	@Test(expected=IllegalAutomataException.class)
	public void testConectivity() throws InvalidStateException,
			MissingStateException, InitialStateMissingException,  OverrideInitialStateException, IllegalAutomataException {
		AutomataBuilder testConectivity = new AutomataBuilder(new AutomataStructureGraphFactory());
		testConectivity.addState("q0");
		testConectivity.addState("q1").addState("q2").addState("q3");
		try {
			testConectivity.addTransition("q0", "q1", 'c');
			testConectivity.addTransition("q0", "q2", 'c');
			@SuppressWarnings("unused")
			Automata builtAutomata = testConectivity.build();
		} catch (MissingStateException e) {
			Assert.fail("Failed to initialize states.");
		}

	}


}
