package main.lexer.automata.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import main.lexer.automata.structure.graph.AutomataState;

public class NewStateCreator {
	
	private Set<AutomataState> stateSet;
	
	private Map<Character, Set<AutomataState>> newStateMap;
	
	private boolean accepts;
	
	private static Map<Set<AutomataState>, NewStateCreator> instances = new HashMap<>();
	
	public static NewStateCreator create(Set<AutomataState> stateSet) {
		if(!instances.containsKey(stateSet)) {
			instances.put(stateSet, new NewStateCreator(stateSet));
		}
		return instances.get(stateSet);
	}

	private NewStateCreator(Set<AutomataState> stateSet) {
		this.stateSet = stateSet;
		newStateMap = new HashMap<>();
		calculateTransitions();
		calculateAccept();
	}
	
	private void calculateAccept() {
		for(AutomataState state : stateSet) {
			if(state.epslonAccept()) {
				accepts = true;
				return;
			}
		}
		accepts = false;
	}

	private void calculateTransitions() {
		for(AutomataState state : stateSet) {
			createTransitionsForState(state);
		}
	}
	
	private void createTransitionsForState(AutomataState state) {
		for(char c : state.getTransitions()) {
			if(!newStateMap.containsKey(c))
				newStateMap.put(c, state.nextStateOfEpslonClosure(c));
			if(state.nextStateOfEpslonClosure(c).size() > newStateMap.get(c).size()) {
				newStateMap.remove(c);
				newStateMap.put(c, state.nextStateOfEpslonClosure(c));
			}
		}
	}
	
	public Set<AutomataState> getSymbolicState() {
		return stateSet;
	}
	
	public Map<Character, Set<AutomataState>> getSymbolicStateTransitions() {
		return newStateMap;
	}
	
	public boolean accepts() {
		return accepts;
	}
	
	
}
