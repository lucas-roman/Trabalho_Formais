package test.lexer.automata.structure.graph;

import junit.framework.Assert;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.ProvideTestService;

import org.junit.Before;
import org.junit.Test;

public class TestGraphState {
	
	private ProvideTestService stateCreator;
	
	@Before
	public void init() {
		stateCreator = new ProvideTestService();
	}
	
	@Test
	public void testTransition() throws InvalidStateException {
		AutomataState state1 = stateCreator.createAutomataState("q0");
		AutomataState state2 = stateCreator.createAutomataState("q1");
		state1.addTransition(state2, 'a');
		Assert.assertEquals(1, state1.nextState('a').size());
		AutomataState state3 = stateCreator.createAutomataState("q2");
		state1.addTransition(state3, 'a');
		Assert.assertEquals(2, state1.nextState('a').size());
		AutomataState state4 = stateCreator.createAutomataState("q3");
		state1.addTransition(state4, 'b');
		Assert.assertEquals(2, state1.nextState('a').size());
		Assert.assertEquals(1, state1.epslonClosure().size());
		Assert.assertEquals(2, state1.getTransitions().size());
	}
	
	@Test(expected=InvalidStateException.class)
	public void testTransitionFail() throws InvalidStateException {
		AutomataState failState = stateCreator.createAutomataState("");
		failState.addTransition(stateCreator.createAutomataState("q1"), 'a');
	}
	
	@Test(expected=InvalidStateException.class)
	public void testTransitionFailValidToInvalid() throws InvalidStateException {
		AutomataState validState = stateCreator.createAutomataState("q1");
		validState.addTransition(stateCreator.createAutomataState(""), 'a');
	}
	
	@Test
	public void testEpslonTransition() throws InvalidStateException {
		AutomataState state1 = stateCreator.createAutomataState("q0");
		AutomataState state2 = stateCreator.createAutomataState("q1");
		AutomataState state3 = stateCreator.createAutomataState("q2");
		AutomataState state4 = stateCreator.createAutomataState("q3");
		state2.addEpslonTransition(state3);
		state1.addEpslonTransition(state2);
		state2.addTransition(state4, 'a');
		Assert.assertEquals(state1.epslonClosure().size(), 3);
		Assert.assertEquals(state2.epslonClosure().size(), 2);
		Assert.assertEquals(state3.epslonClosure().size(), 1);
		Assert.assertTrue(state1.nextStateOfEpslonClosure('a').contains(state4));
		Assert.assertEquals(state1.nextStateOfEpslonClosure('a').size(), 1);
		Assert.assertEquals(state3.nextStateOfEpslonClosure('a').size(), 0);
		try {
			state1.checkNonDetermininstic();
			Assert.fail("Should be non-deterministic.");
		} catch (NonDeterministicException e) {
		}
		
	}
	
	@Test(expected=NonDeterministicException.class)
	public void checkNonDeterministicBySameTransition() throws InvalidStateException, NonDeterministicException {
		AutomataState state1 = stateCreator.createAutomataState("q0");
		AutomataState state2 = stateCreator.createAutomataState("q1");
		AutomataState state3 = stateCreator.createAutomataState("q2");
		state1.addTransition(state2, 'a');
		state1.addTransition(state3, 'a');
		state1.checkNonDetermininstic();
	}
	

}
