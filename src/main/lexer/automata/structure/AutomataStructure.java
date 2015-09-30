package main.lexer.automata.structure;

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
	
	//Adds a transition from state from to state to by the letter trans. If any of the states is not valid, throws an InvalidStateException.
	//If either of the states is missing, throws a MissingStateException. If the resulting automata is non deterministic, throws a NonDeterministicException, so
	//callers up the chain can know which type of automata to return.
	public void addTransition(String from, String to, char trans) throws InvalidStateException, MissingStateException, NonDeterministicException;
 
	//Creates a state passing its name and returns it back to the caller. If the String can not be the name of a state, throws an InvalidStateException.
	public AutomataState createState(String stateName) throws InvalidStateException;

	//Checks if the automata contains a initial state and if all states are reachable. If it doesn't contain a initial state, it throws a
	//InitialStateMissingException. If some state is not reachable, returns false.
	public boolean validateAutomata() throws InitialStateMissingException;
	
	//Add an epslon transition from state from to state to. If either of the states is invalid, throws a InvalidStateException.
	public void addEpslonTransition(String from, String to) throws InvalidStateException;
	
	//Marks the state which corresponds to the String as an accept state. If the state is not valid, throws an InvalidStateException.
	public void markAcceptState(String string) throws InvalidStateException;
	
	//Marks the state which corresponds to the String as the initial one. If there is no such state, throws MissingStateException.
	public void markInitialState(String s) throws MissingStateException;

	//Returns true if the automata contains no state. False otherwise.
	public boolean empty();

	//Checks if the word is in the language of the automata.
	public boolean check(String string);

	//Returns the initial state of the automata.
	public AutomataState automataInitialState();

	//Returns the states of the automata.
	public Set<AutomataState> states();
	
	//Return the accept states.
	public Set<AutomataState> acceptStates();

}
