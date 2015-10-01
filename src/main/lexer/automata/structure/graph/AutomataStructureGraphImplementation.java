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
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

/*
 * A graph implementation for the structure.
 */
class AutomataStructureGraphImplementation implements AutomataStructure {

	private int size = 0;

	private AutomataStateImplementation initialState = null;

	private Map<Integer, AutomataStateImplementation> states;

	public AutomataStructureGraphImplementation() {
		states = new HashMap<>();
	}

	@Override
	public void addTransition(int from, int to, char trans) throws InvalidStateException, MissingStateException, NonDeterministicException {
		AutomataStateImplementation stateFrom = states.get(from);
		AutomataStateImplementation stateTo = states.get(to);
		if(stateFrom == null || stateTo == null) {
			throw new MissingStateException();
		}
		stateFrom.addTransition(stateTo, trans);
		stateFrom.checkNonDetermininstic();
	}

	@Override
	public AutomataState createState(int stateName) {
		AutomataStateImplementation state =  new AutomataStateImplementation(stateName);
		size++;
		if(initialState == null) {
			initialState = state;
		}
		states.put(stateName, state);
		return state;
	}

	@Override
	public boolean validateAutomata() throws InitialStateMissingException {
		if(initialState == null) {
			throw new InitialStateMissingException();
		}
		return checkGraph();
	}

	@Override
	public void addEpslonTransition(int from, int to) throws InvalidStateException {
		AutomataStateImplementation stateFrom = states.get(from);
		AutomataStateImplementation stateTo = states.get(to);
		stateFrom.addEpslonTransition(stateTo);
	}

	@Override
	public void markAcceptState(int string) throws InvalidStateException {
		if (!states.containsKey(string)) {
			throw new InvalidStateException();
		}
		AutomataStateImplementation state = states.get(string);
		state.markAsAccept();
	}

	@Override
	public void markInitialState(int s) throws MissingStateException  {
		AutomataStateImplementation state = states.get(s);
		if(state == null) {
			throw new MissingStateException();
		}
		initialState = state;
	}

	@Override
	public boolean empty() {
		return size == 0;
	}

	@Override
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

	@Override
	public Set<AutomataState> states() {
		Set<AutomataState> retSet = new HashSet<>();
		for(AutomataStateImplementation stat : states.values()) {
			retSet.add(stat);
		}
		return retSet;
	}

	@Override
	public Set<AutomataState> acceptStates() {
		Set<AutomataState> retSet = new HashSet<>();
		for(AutomataState state : states()) {
			if(state.accepts()) {
				retSet.add(state);
			}
		}
		return retSet;
	}

}
