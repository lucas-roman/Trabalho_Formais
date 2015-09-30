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
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

//Converts a non deterministic automata to a deterministic automata.
public class ConvertNonDeterministicDeterministic {

	private Map<Set<AutomataState>, String> states;

	private Set<Set<AutomataState>> statesEvaluated;

	private AutomataBuilder builder;
	int id = 1;

	//Calculates the new automata.
	public ConvertNonDeterministicDeterministic(
			NonDeterministicAutomata nonDeterministic) {
		this.states = new HashMap<>();
		this.statesEvaluated = new HashSet<>();
		builder = new AutomataBuilder(new AutomataStructureGraphFactory());
		Set<AutomataState> newInitialState = nonDeterministic.initialState()
				.epslonClosure();
		calculateInitState(newInitialState);
	}

	private void calculateStates(Set<AutomataState> state) {
		try {
			statesEvaluated.add(state);
			NewStateCreator calculator = NewStateCreator.create(state);
			if (calculator.accepts()) {
				builder.markAcceptState(states.get(state));
			}
			Set<Set<AutomataState>> statesToCalculateTransition = new HashSet<>();
			for (Entry<Character, Set<AutomataState>> keyValue : calculator
					.getSymbolicStateTransitions().entrySet()) {
				addStateIfAbsent(keyValue.getValue());
				statesToCalculateTransition.add(keyValue.getValue());
				builder.addTransition(states.get(state),
						states.get(keyValue.getValue()), keyValue.getKey());
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

	private void addStateIfAbsent(Set<AutomataState> state)
			throws InvalidStateException {
		if (!states.containsKey(state)) {
			builder.addState("q" + id);
			states.put(state, "q" + id);
			id++;
		}
	}

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

	//Returns the new deterministic automata.
	public Automata convert() throws InitialStateMissingException, MissingStateException, IllegalAutomataException {
		return builder.build();
	}

}
