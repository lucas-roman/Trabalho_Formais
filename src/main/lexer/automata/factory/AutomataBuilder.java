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
import main.lexer.automata.structure.graph.AutomataState;

public class AutomataBuilder {

	private boolean automataNonDeterministic;
	private boolean initStateMarked;
	private AutomataStructure structure;

	public AutomataBuilder(AutomataStructureFactory fac) {
		automataNonDeterministic = false;
		initStateMarked = false;
		structure = fac.createAutomataStructure();
	}

	/*
	 * Adds a transition from a state to another. State should be added first
	 * with the addState method. Exception can be safely ignored, because we
	 * already make sure that we are adding a valid state at the addState
	 * method.
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
	

	public Automata build() throws InitialStateMissingException, MissingStateException, IllegalAutomataException {
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
	 * Exception can be ignored, since it is already treated in the addState
	 * method, we can be sure that we are not adding invalid states.
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

	public void markInitialState(String string) throws MissingStateException, OverrideInitialStateException  {
		if(initStateMarked) {
			throw new OverrideInitialStateException();
		}
		structure.markInitialState(string);
		initStateMarked = true;
	}

	public void markAcceptState(String string) throws InvalidStateException,
			InitialStateMissingException {
		structure.markAcceptState(string);
	}

}
