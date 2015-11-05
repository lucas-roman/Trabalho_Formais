/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata.factory;

import java.util.HashMap;
import java.util.Map;

import main.lexer.automata.Automata;
import main.lexer.automata.DeterministicAutomata;
import main.lexer.automata.NonDeterministicAutomata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.factory.AutomataStructureFactory;

/*
 * This class should be used to create automatas.
 */
public class AutomataBuilder {

	/*
	 * Attributes of this class.
	 */
	private boolean automataNonDeterministic;
	private AutomataStructure structure;
	private Map<String, Integer> stateId = new HashMap<>();
	private int id = 0;

	/*
	 *  Constructs a builder given a type for the structure.
	 *  PS: It should be passed the factory here, not the structure!
	 */
	public AutomataBuilder(AutomataStructureFactory fac) {
		automataNonDeterministic = false;
		structure = fac.createAutomataStructure();
	}

	/*
	 * @return id returns de ID of the builder.
	 */
	public int currentID() {
		return id;
	}

	/*
	 * Adds a transition from a state to another.
	 * State should be added first with the addState method.
	 * Throws a MissingStateException if there is no
	 * such state, InvalidStateException if any state is invalid.
	 */
	public void addTransition(String from, String to, char trans) throws MissingStateException, InvalidStateException {
		if (!stateId.containsKey(from) || !stateId.containsKey(to)) {
			throw new MissingStateException();
		}
		try {
			structure.addTransition(stateId.get(from), stateId.get(to), trans);
		} catch (NonDeterministicException e) {
			automataNonDeterministic = true;
		}
	}

	/*
	 * Adds a state to the automata to be built. If the state is invalid, throws
	 * an InvalidStateException
	 */
	public AutomataBuilder addState(String stateName)
			throws InvalidStateException {
		if (!stateId.containsKey(stateName)) {
			stateId.put(stateName, id);
			id++;
			structure.createState(stateId.get(stateName));
		}
		return this;
	}

	/*
	 * Builds the automata. If given the states and the transitions and it is non
	 * deterministic, returns a non deterministic automata.
	 * It returns Deterministic automata otherwise.
	 * If the automata is missing its initial state, throws a InitialStateMissingException.
	 * If some state is not reachable, throws a IllegalAutomataException.
	 *
	 * @return it returns an automata
	 */
	public Automata build() throws InitialStateMissingException, IllegalAutomataException {
		if (structure.empty()) {
			throw new InitialStateMissingException();
		}
		if (!structure.validateAutomata()) {
			throw new IllegalAutomataException();
		}
		try {
			structure.markInitialState(0);
		} catch (MissingStateException e) {
		}
		if (automataNonDeterministic) {
			return new NonDeterministicAutomata(structure);
		}
		return new DeterministicAutomata(structure);
	}

	public void addTagToState(String state, String tag) throws InvalidStateException {
		Integer stateValue = stateId.get(state);
		if(stateValue == null) {
			throw new InvalidStateException();
		}
		structure.addTag(stateValue, tag);
	}

	/*
	 * Adds a transition from state from to state to. If any state is missing,
	 * throws a MissingStateException.
	 */
	public void addEmptyTransition(String from, String to)
			throws MissingStateException {
		if (!stateId.containsKey(from) || !stateId.containsKey(to)) {
			throw new MissingStateException();
		}
		try {
			structure.addEpslonTransition(stateId.get(from), stateId.get(to));
			automataNonDeterministic = true;
		} catch (InvalidStateException e) {
			e.printStackTrace();
		}
	}

	/*
	 *  Marks the state as an accept state. If it is invalid, throws an InvalidStateException.
	 */
	public void markAcceptState(String string) throws InvalidStateException, MissingStateException {
		if (!stateId.containsKey(string)) {
			throw new MissingStateException();
		}
		structure.markAcceptState(stateId.get(string));
	}
}
