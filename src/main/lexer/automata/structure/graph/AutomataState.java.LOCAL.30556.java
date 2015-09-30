package main.lexer.automata.structure.graph;

import java.util.Set;

import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.NonDeterministicException;

public interface AutomataState extends GraphUnit {

	
	// This method throws an exception if either the state that calls it or the state passed to it are invalid states.
	public void addTransition(AutomataState stateTo, char transition)
			throws InvalidStateException;

	
	// Adds a transition from this state to another using the empty word. If this or the other state is invalid, throws an exception.
	public void addEpslonTransition(AutomataState to)
			throws InvalidStateException;

	
	/* Checks if current state is valid. If not, throws an exception.
	 */
	public void checkValidState() throws InvalidStateException;
	
	
	/* This method should be used to check if the string is part of the language described by the automata.
	 * Note: Do not call this method on a state other than the initial state. (For automata implementer).
	 * To begin search, the word to be checked if part of the language or not should be passed as string, with its index equal to 0, to indicate the beginning of 
	 * the word.
	 */
	public boolean process(String string, int index);
	
	// Returns all the states which this state can reach by the character c, not counting epslon transitions.
	Set<AutomataState> nextState(char c);
	
	// Returns this state epslon closure, i.e all states reachable by the empty word.
	Set<AutomataState> epslonClosure();
	
	//Calculates the epslon closure.
	void epslonClosure(Set<AutomataState> sets);
	
	//Returns all the states reachable by the epslon closure by the character.
	Set<AutomataState> nextStateOfEpslonClosure(char c);
	
	//Marks this stage as an accept state.
	void markAsAccept();

	//Checks if this state makes the automata non deterministic. If it does, throws NonDeterministicException.
	public void checkNonDetermininstic() throws NonDeterministicException;
	
	//Get all possible transitions (by letter) from this state.
	public Set<Character> getTransitions();
	
	//Checks if any of epslon closure of this state is an accept state.
	public boolean epslonAccept();

	//Checks if this state is an accept state.
	public boolean accepts();
	

}