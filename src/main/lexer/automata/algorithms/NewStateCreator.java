package main.lexer.automata.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.structure.graph.AutomataState;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */


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
	private static Map<Set<AutomataState>, NewStateCreator> instances = new HashMap<>();

	/*
	 * @param stateSet set to be added to 'this.instances' if is not contained in instances.
	 * @return set of instances of this class.
	 */
	public static NewStateCreator create(Set<AutomataState> stateSet) {
		if(!instances.containsKey(stateSet)) {
			instances.put(stateSet, new NewStateCreator(stateSet));
		}
		return instances.get(stateSet);
	}

	/*
	 * @param stateSet set to calculate the transitions of new states.
	 */
	private NewStateCreator(Set<AutomataState> stateSet) {
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
				newStateMap.put(keyPair.getKey(), state.nextStateOfEpslonClosure(keyPair.getKey()));
			}
			if(state.nextStateOfEpslonClosure(keyPair.getKey()).size() > newStateMap.get(keyPair.getKey()).size()) {
				newStateMap.remove(keyPair.getKey());
				newStateMap.put(keyPair.getKey(), state.nextStateOfEpslonClosure(keyPair.getKey()));
			}
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
