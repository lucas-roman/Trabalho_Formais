package main.lexer.automata.structure;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */


import java.util.Set;

import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.graph.AutomataState;

/*
 * Implementations of this class should define a structure to hold states and transitions for finite state machines.
 */
public interface AutomataStructure {

	/*
	 * Adds a transition from state 'from' to state 'to' by the letter 'trans'.
	 * If any of the states is not valid, throws an InvalidStateException.
	 * If either of the states is missing, throws a MissingStateException.
	 * If the resulting automata is non deterministic, throws a NonDeterministicException,
	 * so callers up the chain can know which type of automata to return.
	 * @param from origin state.
	 * @param to destination state.
	 * @param trans character that makes the transition from the state 'from' to state 'to'.
	 */
	public void addTransition(int from, int to, char trans) throws InvalidStateException, MissingStateException, NonDeterministicException;

	/*
	 * Creates a state passing its name and returns it back to the caller.
	 * If the String can not be the name of a state, throws an InvalidStateException.
	 * @param stateName name of the state to be found.
	 */
	public AutomataState createState(int stateName) throws InvalidStateException;

	/*
	 * Checks if the automata contains an initial state and if all states are reachable.
	 * If it does not contain a initial state, it throws a InitialStateMissingException.
	 * If some state is not reachable, returns false.
	 */
	public boolean validateAutomata() throws InitialStateMissingException;

	/*
	 * Add an epslon transition from state 'from' to state 'to'.
	 * If either of the states is invalid, throws a InvalidStateException.
	 * @param from origin state.
	 * @param to destination state.
	 */
	public void addEpslonTransition(int from, int to) throws InvalidStateException;

	/*
	 * Marks the state which corresponds to the String as an accept state.
	 * If the state is not valid, throws an InvalidStateException.
	 * @param string name of the state to be marked.
	 */
	public void markAcceptState(int string) throws InvalidStateException;

	/* Marks the state which corresponds to the int as the initial one.
	 * If there is no such state, throws MissingStateException.
	 * @param s state to be marked as initial.
	 */
	public void markInitialState(int s) throws MissingStateException;

	/*
	 * Returns true if the automata contains none state.
	 * It returns false otherwise.
	 */
	public boolean empty();

	/*
	 * Checks if the word is in the language of the automata.
	 * @param string word to be checked if it is acceptable by the automata.
	 */
	public boolean check(String string);

	/*
	 * Returns the initial state of the automata.
	 */
	public AutomataState automataInitialState();

	/*
	 * Returns the states of the automata.
	 */
	public Set<AutomataState> states();

	/*
	 * Return the accept states.
	 */
	public Set<AutomataState> acceptStates();

	
	public void addTag(Integer stateValue, String tag) throws InvalidStateException;

	public String tagOf(String input);

}
