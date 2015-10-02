package main.lexer.automata.algorithms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.NonDeterministicAutomata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */


/* This class converts a non deterministic automata to a deterministic automata.
 * */
public class ConvertNonDeterministicDeterministic {

	/*
	 * Attibutes from this class.
	 */
	private Map<Set<AutomataState>, String> states;
	private Set<Set<AutomataState>> statesEvaluated;
	private AutomataBuilder builder;
	int id = 1;

	/* Calculates the new automata.
	 * @param nonDeterministic is the automata that is going to be converted to a deterministic automata.
	 * */
	public ConvertNonDeterministicDeterministic(NonDeterministicAutomata nonDeterministic) {
		this.states = new HashMap<>();
		this.statesEvaluated = new HashSet<>();
		builder = new AutomataBuilder(new AutomataStructureGraphFactory());
		Set<AutomataState> newInitialState = nonDeterministic.initialState().epslonClosure();
		calculateInitState(newInitialState);
	}

	/*
	 * It calculates the new states of the new deterministic automata.
	 * @param state Set of state of the nonDeterministicAutomata.
	 */
	private void calculateStates(Set<AutomataState> state) {
		try {
			statesEvaluated.add(state);
			NewStateCreator calculator = NewStateCreator.create(state);
			if (calculator.accepts()) {
				builder.markAcceptState(states.get(state));
			}
			Set<Set<AutomataState>> statesToCalculateTransition = new HashSet<>();
			for (Entry<Character, Set<AutomataState>> keyValue : calculator.getSymbolicStateTransitions().entrySet()) {
				addStateIfAbsent(keyValue.getValue());
				statesToCalculateTransition.add(keyValue.getValue());
				builder.addTransition(states.get(state), states.get(keyValue.getValue()), keyValue.getKey());
			}
			for (Set<AutomataState> newState : statesToCalculateTransition) {
				if (!statesEvaluated.contains(newState)) {
					calculateStates(newState);
				}
			}
		} catch (InvalidStateException e) {
		} catch (MissingStateException e) {
		}
	}

	/*
	 * It adds an missing state to the set of states.
	 * @param state Set of states that is going to receive the missing state.
	 */
	private void addStateIfAbsent(Set<AutomataState> state) throws InvalidStateException {
		if (!states.containsKey(state)) {
			builder.addState("q" + id);
			states.put(state, "q" + id);
			id++;
		}
	}

	/*
	 * It creates the initial state of the automata and it adds to the 'states' of this class.
	 * @param state Set of states of the nonDeterministicAutomata.
	 */
	private void calculateInitState(Set<AutomataState> state) {
		try {
			builder.addState("q0");
			states.put(state, "q0");
			calculateStates(state);
		} catch (InvalidStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* It returns the new deterministic automata.
	 */
	public Automata convert() throws InitialStateMissingException, MissingStateException, IllegalAutomataException {
		return builder.build();
	}
}
