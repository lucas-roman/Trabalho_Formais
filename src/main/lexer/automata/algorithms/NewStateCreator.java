/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.structure.graph.AutomataState;

/*
 * This class should be used by the convert to calculate the transitions for new states.
 * */
class NewStateCreator {

	/*
	 * Attributes of this class.
	 */
	private Set<AutomataState> stateSet;
	private Map<Character, Set<AutomataState>> newStateMap;
	private boolean accepts;


	/*
	 * @param stateSet set to calculate the transitions of new states.
	 */
	NewStateCreator(Set<AutomataState> stateSet) {
		this.stateSet = stateSet;
		newStateMap = new HashMap<>();
		calculateTransitions();
		calculateAccept();
	}

	/*
	 * This method set the final states (those that accept some word).
	 */
	private void calculateAccept() {
		for(AutomataState state : stateSet) {
			if(state.epslonAccept()) {
				accepts = true;
				return;
			}
		}
		accepts = false;
	}

	/*
	 * Calculates the transitions of the states.
	 */
	private void calculateTransitions() {
		for(AutomataState state : stateSet) {
			createTransitionsForState(state);
		}
	}

	/*
	 * This method creates the transitions of a given 'state'.
	 * @param state given state to create its transitions.
	 */
	private void createTransitionsForState(AutomataState state) {
		for(Entry<Character, Set<AutomataState>> keyPair : state.getTransitions()) {
			if(!newStateMap.containsKey(keyPair.getKey())) {
				//newStateMap.put(keyPair.getKey(), state.nextStateOfEpslonClosure(keyPair.getKey()));
				newStateMap.put(keyPair.getKey(), new HashSet<AutomataState>());
			}
			//if(state.nextStateOfEpslonClosure(keyPair.getKey()).size() > newStateMap.get(keyPair.getKey()).size()) {
			//	newStateMap.remove(keyPair.getKey());
			//	newStateMap.put(keyPair.getKey(), state.nextStateOfEpslonClosure(keyPair.getKey()));
			//}
			newStateMap.get(keyPair.getKey()).addAll(state.nextStateOfEpslonClosure(keyPair.getKey()));
		}
	}

	/*
	 * Returns the set of the states which represents the new state.
	 */
	public Set<AutomataState> getSymbolicState() {
		return stateSet;
	}

	/*
	 * Returns the transitions of the new state.
	 */
	public Map<Character, Set<AutomataState>> getSymbolicStateTransitions() {
		return newStateMap;
	}

	/*
	 * Returns true if the new state is an accept state. False otherwise.
	 */
	public boolean accepts() {
		return accepts;
	}


}
