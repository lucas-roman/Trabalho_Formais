package test.lexer.automata;

import junit.framework.Assert;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.generalizednondeterministic.GeneralizedFiniteAutomataStructure;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
import main.lexer.regularexpression.RegularExpression;

import org.junit.Test;

public class TestGeneralizedAutomata {
	
	
	@Test
	public void testConstructor() throws InvalidStateException, MissingStateException, InitialStateMissingException, IllegalAutomataException {
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
		Automata deterministicAutomata = deterministicBuilder.build();
		GeneralizedFiniteAutomataStructure test = new GeneralizedFiniteAutomataStructure(deterministicAutomata);
		RegularExpression re = test.convertToRegularExpression();
		Automata back = re.createAutomata();
		Assert.assertTrue(back.accepts("abba"));
		Assert.assertTrue(back.accepts("baabbababbab"));
		Assert.assertTrue(back.accepts("a"));
		Assert.assertTrue(back.accepts("b"));
		Assert.assertTrue(back.accepts("aa"));
		Assert.assertTrue(back.accepts("bb"));
		Assert.assertFalse(back.accepts("ab"));
		Assert.assertFalse(back.accepts("ba"));
		Assert.assertFalse(back.accepts("abababaaab"));
		Assert.assertFalse(back.accepts("bababaaaaba"));
		Assert.assertTrue(back.accepts(""));
	}

}
