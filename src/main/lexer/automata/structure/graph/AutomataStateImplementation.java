package main.lexer.automata.structure.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.NonDeterministicException;

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

	private String name;

	private DecisionStrategyInterface _decisionType;

	AutomataStateImplementation(String name) {
		nextStates = new HashMap<>();
		statesByEpslon = selectSetType();
		this.name = name;
		_decisionType = new DecisionStrategyInterface.DecisionRejectStrategy();
	}

	@Override
	public void addTransition(AutomataState nextState, char transition)
			throws InvalidStateException {
		nextState.checkValidState();
		nextState(transition).add(nextState);
	}

	@Override
	public void addEpslonTransition(AutomataState to)
			throws InvalidStateException {
		to.checkValidState();
		statesByEpslon.add(to);
	}

	@Override
	public int countNeighborhood(Set<GraphUnit> visited) {
		return new GraphValidator(this, visited).countNeighborhood();
	}

	/*
	 * This method should remain empty
	 */
	@Override
	public void checkValidState() throws InvalidStateException {
	}

	@Override
	public boolean process(String string, int index) {
		if (isLastCharacterOfWord(string, index)) {
			return _decisionType.decide();
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

	@Override
	public void markAsAccept() {
		_decisionType = new DecisionStrategyInterface.DecisionAcceptStrategy();
	}

	@Override
	public Set<AutomataState> nextStateOfEpslonClosure(char c) {
		Set<AutomataState> returnSet = new HashSet<>();
		for (AutomataState aut : epslonClosure()) {
			returnSet.addAll(aut.nextState(c));
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
		return name;
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
	public Set<Character> getTransitions() {
		return nextStates.keySet();
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
			if(trans.size() > 1)
				return true;
		}
		return false;
	}







}