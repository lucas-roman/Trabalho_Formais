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
		Set<AutomataState> returnValue = new HashSet<AutomataState>();
		calculateStates(initialState(), returnValue);
		return returnValue;
	}
	
	protected void calculateStates(AutomataState state,
			Set<AutomataState> returnValue) {
		returnValue.add(state);
		for(char c : state.getTransitions()) {
			for(AutomataState stat : state.nextState(c)) {
				if(!returnValue.contains(stat)) {
					calculateStates(stat, returnValue);
				}
			}
		}
	}

}
