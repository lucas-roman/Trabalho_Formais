package main.lexer.automata.factory;

import main.lexer.automata.Automata;
import main.lexer.automata.DeterministicAutomata;
import main.lexer.automata.NonDeterministicAutomata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.factory.AutomataStructureFactory;

/*
 * This class should be used to create automatas.
 */
public class AutomataBuilder {

	private boolean automataNonDeterministic;
	private boolean initStateMarked;
	private AutomataStructure structure;

	//Constructs a builder given a type for the structure. It should be passed the factory here, not the structure.
	public AutomataBuilder(AutomataStructureFactory fac) {
		automataNonDeterministic = false;
		initStateMarked = false;
		structure = fac.createAutomataStructure();
	}

	/*
	 * Adds a transition from a state to another. State should be added first
	 * with the addState method. Throws a MissingStateException if there is no such state, InvalidStateException if any state is invalid.
	 */
	public void addTransition(String from, String to, char trans)
			throws MissingStateException, InvalidStateException {
		try {
			structure.addTransition(from, to, trans);
		} 
		catch (NonDeterministicException e) {
			automataNonDeterministic = true;
		}
	}

	/*
	 * Adds a state to the automata to be built. If the state is invalid, throws
	 * an InvalidStateException
	 */
	public AutomataBuilder addState(String stateName)
			throws InvalidStateException {
		structure.createState(stateName);
		return this;
	}
	
	/*
	 * Builds the automata. If given the states and the transitions it is non deterministic, returns a non deterministic automata. Deterministic automata
	 * otherwise. If the automata is missing its initial state, throws a InitialStateMissingException. If some state is not reachable, throws a 
	 * IllegalAutomataException. 
	 */
	public Automata build() throws InitialStateMissingException, IllegalAutomataException {
		if (structure.empty()) {
			throw new InitialStateMissingException();
		}
		if(!structure.validateAutomata()) {
			throw new IllegalAutomataException();
		}
		if (automataNonDeterministic) {
			return new NonDeterministicAutomata(structure);
		}
		return new DeterministicAutomata(structure);
	}

	/*
	 * Adds a transition from state from to state to. If any state is missing, throws a MissingStateException.
	 */
	public void addEmptyTransition(String from, String to)
			throws MissingStateException {
		try {
			structure.addEpslonTransition(from ,to);
			automataNonDeterministic = true;
		} catch (InvalidStateException e) {
			e.printStackTrace();
		}
	}

	//Marks the initial state. If it's missing, throws a MissingStateException. If it was already marked, throws a OverrideInitialStateException.
	//As a consequence, initial state can be marked only once. (change?)
	public void markInitialState(String string) throws MissingStateException, OverrideInitialStateException  {
		if(initStateMarked) {
			throw new OverrideInitialStateException();
		}
		structure.markInitialState(string);
		initStateMarked = true;
	}

	//Marks the state as an accept state. If it is invalid, throws an InvalidStateException.
	public void markAcceptState(String string) throws InvalidStateException,
			InitialStateMissingException {
		structure.markAcceptState(string);
	}

}
