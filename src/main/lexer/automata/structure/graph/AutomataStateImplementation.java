package main.lexer.automata.structure.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.exceptions.NonDeterministicException;


/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

/*
 * This class represents a state as a graph unit.
 * Please check interface documentation.
 */
class AutomataStateImplementation implements AutomataState {

	/*
	 * Please modify this function to change the type of all the sets for the
	 * values of nextStates.
	 */
	private static Set<AutomataState> selectSetType() {
		return new HashSet<>();
	}

	private Set<AutomataState> statesByEpslon;
	private Map<Character, Set<AutomataState>> nextStates;
	private int name;
	private DecisionStrategyInterface _decisionType;
	private String tag;

	AutomataStateImplementation(int name) {
		tag = "";
		nextStates = new HashMap<>();
		statesByEpslon = selectSetType();
		this.name = name;
		_decisionType = new DecisionStrategyInterface.DecisionRejectStrategy();
	}

	//Adds transition from this state to nextState by char transition.
	public void addTransition(AutomataStateImplementation nextState, char transition) {
		nextState(transition).add(nextState);
	}

	//Adds an epslon transition from this state to the other.
	public void addEpslonTransition(AutomataStateImplementation to) {
		statesByEpslon.add(to);
	}

	@Override
	public int countNeighborhood(Set<GraphUnit> visited) {
		visited.add(this);
		return new GraphValidator(this, visited).countNeighborhood();
	}

	@Override
	public boolean process(String string, int index) {
		if (isLastCharacterOfWord(string, index)) {
			return epslonAccept();
		}
		return processRestOfWordByEpslonClosure(string, index);
	}

	@Override
	public Set<AutomataState> nextState(char c) {
		if (!nextStates.containsKey(c)) {
			Set<AutomataState> value = AutomataStateImplementation
					.selectSetType();
			nextStates.put(c, value);
		}
		return nextStates.get(c);
	}

	@Override
	public Set<AutomataState> epslonClosure() {
		Set<AutomataState> newVisit = new HashSet<>();
		epslonClosure(newVisit);
		return newVisit;
	}

	@Override
	public void epslonClosure(Set<AutomataState> visited) {
		visited.add(this);
		recursivelyGetEpslonClosure(visited);
	}

	//Marks this state as an accept state
	public void markAsAccept() {
		_decisionType = new DecisionStrategyInterface.DecisionAcceptStrategy();
	}

	@Override
	public Set<AutomataState> nextStateOfEpslonClosure(char c) {
		Set<AutomataState> returnSet = new HashSet<>();
		for (AutomataState aut : epslonClosure()) {
			for(AutomataState epslonReachableFromNext : aut.nextState(c)) {
				returnSet.addAll(epslonReachableFromNext.epslonClosure());
			}
		}
		return returnSet;
	}

	@Override
	public void checkNonDetermininstic() throws NonDeterministicException {
		if (moreThanOneStateBySameTransition() || hasEpslonTransition()) {
			throw new NonDeterministicException();
		}
	}

	@Override
	public String toString() {
		return "" + name;
	}

	@Override
	public Set<GraphUnit> nextStatesOfGraph() {
		Set<GraphUnit> returnSet = new HashSet<>();
		for(Set<AutomataState> set : nextStates.values()) {
			for(AutomataState state : set) {
				returnSet.add(state);
			}
		}
		for(AutomataState state : statesByEpslon) {
			returnSet.add(state);
		}
		return returnSet;
	}

	@Override
	public boolean epslonAccept() {
		Set<AutomataState> epslon = epslonClosure();
		for(AutomataState state : epslon) {
			if(state.accepts()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Entry<Character,  Set<AutomataState>>> getTransitions() {
		return nextStates.entrySet();
	}

	@Override
	public boolean accepts() {
		return _decisionType.decide();
	}


	/*
	 * =================================================================================================================================
	 * Private Methods
	 * =================================================================================================================================
	 */
	private boolean processRestOfWordByEpslonClosure(String word, int index) {
		for (AutomataState possibility : nextStateOfEpslonClosure(word
				.charAt(index++))) {
			if (possibility.process(word, index)) {
				return true;
			}
		}
		return false;
	}

	private boolean isLastCharacterOfWord(String word, int index) {
		return index == word.length();
	}

	private void recursivelyGetEpslonClosure(Set<AutomataState> visited) {
		for (AutomataState reachableByEp : statesByEpslon) {
			if (!visited.contains(reachableByEp)) {
				reachableByEp.epslonClosure(visited);
			}
		}
	}

	private boolean hasEpslonTransition() {
		return statesByEpslon.size() > 0;
	}

	private boolean moreThanOneStateBySameTransition() {
		for(Set<AutomataState> trans : nextStates.values()) {
			if(trans.size() > 1) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int stateID() {
		return name;
	}

	public Set<AutomataState> epslonTransitions() {
		return statesByEpslon;
	}

	@Override
	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String getTag() {
		return tag;
	}

	public String tagOf(String input, int i) {
		if(isLastCharacterOfWord(input, i)) {
			return this.tag;
		}
		AutomataState nextState = null;
		if(nextState(input.charAt(i)).size() == 0) {
			return "ERROR";
		}
		for(AutomataState magic : nextState(input.charAt(i))) {
			nextState = magic;
			break;
		}
		i++;
		return nextState.tagOf(input, i);
	}







}