package main.lexer.automata.structure.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.AutomataStructure;

/*
 * A graph implementation for the structure.
 */
class AutomataStructureGraphImplementation implements AutomataStructure {
	
	private int size = 0;
	
	private AutomataState initialState = null;
	
	private Map<String, AutomataState> states;
	
	public AutomataStructureGraphImplementation() {
		states = new HashMap<>();
	}
	
	public void addTransition(String from, String to, char trans) throws InvalidStateException, MissingStateException, NonDeterministicException {
		AutomataState stateFrom = states.get(from);
		AutomataState stateTo = states.get(to);
		if(stateFrom == null || stateTo == null) {
			throw new MissingStateException();
		}
		stateFrom.addTransition(stateTo, trans);
		stateFrom.checkNonDetermininstic();
	}

	public AutomataState createState(String stateName) throws InvalidStateException {
		AutomataState state;
		if (stateName == null || stateName.equals("")) {
			state = InvalidState.getInstance();
		}
		else {
			state =  new AutomataStateImplementation(stateName);
		}
		state.checkValidState();
		size++;
		if(initialState == null) {
			initialState = state;
		}
		states.put(stateName, state);
		return state;
	}

	public boolean validateAutomata() throws InitialStateMissingException {
		if(initialState == null) {
			throw new InitialStateMissingException();
		}
		return checkGraph();
	}
	
	public void addEpslonTransition(String from, String to) throws InvalidStateException {
		AutomataState stateFrom = states.get(from);
		AutomataState stateTo = states.get(to);
		stateFrom.addEpslonTransition(stateTo);
	}
	
	public void markAcceptState(String string) throws InvalidStateException {
		if (!states.containsKey(string)) {
			throw new InvalidStateException();
		}
		AutomataState state = states.get(string);
		state.markAsAccept();
	}
	
	public void markInitialState(String s) throws MissingStateException  {
		AutomataState state = states.get(s);
		if(state == null) {
			throw new MissingStateException();
		}
		initialState = state;
	}

	public boolean empty() {
		return size == 0;
	}

	public boolean check(String string) {
		return initialState.process(string, 0);
	}
	

	private boolean checkGraph() {
		Set<GraphUnit> visited = new HashSet<>();
		return size == initialState.countNeighborhood(visited);
	}

	@Override
	public AutomataState automataInitialState() {
		return initialState;
	}

}
