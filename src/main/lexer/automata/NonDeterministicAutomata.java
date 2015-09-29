package main.lexer.automata;

import main.lexer.automata.algorithms.ConvertNonDeterministicDeterministic;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.graph.AutomataState;



public class NonDeterministicAutomata implements Automata, Converteable {

	private AutomataStructure stateImpl;

	public NonDeterministicAutomata(AutomataStructure stateManager)  {
		stateImpl = stateManager;
	}

	@Override
	public boolean accepts(String string) {
		return stateImpl.check(string);
	}

	//Returns a deterministic automata which accepts the same language as this non deterministic automata. If it fails, it returns itself (NonDeterministic).
	@Override
	public Automata convert() throws DeterministicException {
		ConvertNonDeterministicDeterministic converter = new ConvertNonDeterministicDeterministic(this);
		try {
			return converter.convert();
		} catch (InitialStateMissingException | MissingStateException
				| IllegalAutomataException e) {
			return this;
		}
	}

	public AutomataState initialState() {
		return stateImpl.automataInitialState();
	}

}
