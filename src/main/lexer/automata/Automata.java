package main.lexer.automata;

import main.lexer.automata.exceptions.DeterministicException;

/*
 * This class is used to represent automatas. It should have at least 2 subclasses: deterministic automata and non deterministic automata. Its usage is to 
 * check whether a given word is part of the language defined by it or not.
 */
public interface Automata {

	//Passes a String and checks whether the automata accepts the word or not.
	boolean accepts(String string);

	//Converts a Non Deterministic Automata to a Deterministic Automata. If the automata is already Deterministic, throws a DeterministicException
	Automata convert() throws DeterministicException;

}
