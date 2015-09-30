package main.lexer.automata;

import java.util.HashSet;
import java.util.Set;

import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.graph.AutomataState;

public abstract class AutomataSkeleton implements Automata {
	

	protected AutomataStructure stateImpl;
	
	public AutomataSkeleton(AutomataStructure stateManager) {
		stateImpl = stateManager;
	}
	
	public AutomataState initialState() {
		return stateImpl.automataInitialState();
	}
	
	@Override
	public boolean accepts(String string) {
		return stateImpl.check(string);
	}
	
	@Override
	public Set<AutomataState> getStates() {
		return stateImpl.states();
	}
	
	@Override
	public Set<AutomataState> acceptStates() {
		return stateImpl.acceptStates();
	}
	

}
