package main.lexer.automata;

import java.util.Set;

import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA INE - DEPARTAMENTO DE INFORM�TICA E
 * ESTAT�STICA LINGUAGENS FORMAIS E COMPILADORES
 * 
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES Copyright � 2015
 */

/*
 * This class is used to represent automatas. It should have at least 2
 * subclasses: deterministic automata and non deterministic automata. Its usage
 * is to check whether a given word is part of the language defined by it or
 * not.
 */
public interface Automata {

	/* Passes a String and checks whether the automata accepts the word or not. */
	boolean accepts(String string);

	/*
	 * Converts a Non Deterministic Automata to a Deterministic Automata. If the
	 * automata is already Deterministic, throws a DeterministicException
	 */
	Automata convert() throws DeterministicException;

	/* Returns the states of the automata. */
	public Set<AutomataState> getStates();
	
	/* Returns the states of the automata which aren't accept states. */
	public Set<AutomataState> notAcceptStates();
	
	public Set<Character> charForTransitions();

	/* Returns the initial state of the automata. */
	public AutomataState initialState();

	/* Returns the accept state of the automata. */
	public Set<AutomataState> acceptStates();

	/* Returns size of the automata. */
	public int size();

	/* Decomposes the automata into a Builder*/
	public void decomposeAutomataIntoBuilder(AutomataBuilder builder)
			throws InvalidStateException;

	/* Returns an automata which recognizes the union of the language recognized by this automata and the
	 * language recognized by other */
	Automata union(Automata other) throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException;

	/* Returns an automata which recognizes the concatenation of the language recognized by this automata and the
	 * language recognized by other */
	Automata concatenate(Automata other) throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException;

	/* Returns an automata which recognizes the kleene star of the language recognized by this automata */
	Automata kleene() throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException;
	
	/* Returns this automata minimized*/
	Automata minimize();

}
