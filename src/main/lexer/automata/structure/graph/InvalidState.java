package main.lexer.automata.structure.graph;

import java.util.HashSet;
import java.util.Set;

import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.NonDeterministicException;


/* A class for an InvalidState, a state that is not valid for an automata and is only used to signal errors.
 */
class InvalidState implements AutomataState {

	private static InvalidState instance = null;

	/*
	 * As instances of this class should only report errors and be used for that
	 * purpose only, we can leave it as a singleton to save space
	 */
	static InvalidState getInstance() {
		if (instance == null) {
			instance = new InvalidState();
		}
		return instance;
	}

	private InvalidState() {
	}

	@Override
	public void addTransition(AutomataState nextState, char transition)
			throws InvalidStateException {
		throw new InvalidStateException();
	}

	@Override
	public void addEpslonTransition(AutomataState to) throws InvalidStateException {
		throw new InvalidStateException();
	}

	@Override
	public void checkValidState() throws InvalidStateException {
		throw new InvalidStateException();
	}

	@Override
	public boolean process(String string, int index) {
		return false;
	}

	@Override
	public Set<AutomataState> nextState(char c) {
		Set<AutomataState> loneSet = new HashSet<>();
		return loneSet;
	}

	@Override
	public void epslonClosure(
			Set<AutomataState> visited) {
		return;
	}

	@Override
	public Set<AutomataState> nextStateOfEpslonClosure(char c) {
		Set<AutomataState> loneSet = new HashSet<>();
		return loneSet;
	}

	@Override
	public void checkNonDetermininstic() throws NonDeterministicException {
	}

	@Override
	public void markAsAccept() {
		// TODO Auto-generated method stub

	}

	@Override
	public int countNeighborhood(Set<GraphUnit> visited) {
		return 0;
	}

	@Override
	public Set<GraphUnit> nextStatesOfGraph() {
		return new HashSet<>();
	}

	@Override
	public Set<AutomataState> epslonClosure() {
		return new HashSet<>();
	}

	@Override
	public Set<Character> getTransitions() {
		return new HashSet<>();
	}

	@Override
	public boolean epslonAccept() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accepts() {
		// TODO Auto-generated method stub
		return false;
	}
}
