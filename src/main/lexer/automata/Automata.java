package main.lexer.automata;

import java.util.Set;

import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.structure.graph.AutomataState;

/*
 * This class is used to represent automatas. It should have at least 2 subclasses: deterministic automata and non deterministic automata. Its usage is to 
 * check whether a given word is part of the language defined by it or not.
 */
public interface Automata {

	//Passes a String and checks whether the automata accepts the word or not.
	boolean accepts(String string);

	//Converts a Non Deterministic Automata to a Deterministic Automata. If the automata is already Deterministic, throws a DeterministicException
	Automata convert() throws DeterministicException;
	
	//Returns the states of the automata.
	public Set<AutomataState> getStates();
	
	//Returns the initial state of the automata.
	public AutomataState initialState();

}
