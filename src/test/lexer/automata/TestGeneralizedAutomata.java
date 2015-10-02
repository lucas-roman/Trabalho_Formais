package test.lexer.automata;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.generalizednondeterministic.GeneralizedFiniteAutomataStructure;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

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
	}

}
