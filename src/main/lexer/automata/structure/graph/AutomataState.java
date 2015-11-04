package main.lexer.automata.structure.graph;

import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.exceptions.NonDeterministicException;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

/*
 * This is an interface to be implement by the AutomataStateImplementation.
 */
public interface AutomataState extends GraphUnit {
	/*
	 * This method should be used to check if the string is part of the language described by the automata.
	 * Note: Do not call this method on a state other than the initial state (for automata implementer).
	 * To begin the search, the word to be checked if it is part of the language or not should
	 * be passed as string, with its index equal to 0, to indicate the beginning of
	 * the word.
	 * @param string word to be checked.
	 * @param index the index of beginning of the word.
	 */
	public boolean process(String string, int index);

	/*
	 *  Returns all the states which this state can reach by the character c, not counting epslon transitions.
	 *  If no such transition exists, returns an empty set.
	 *  @param c character that will help to find all the reacheable states by it.
	 */
	Set<AutomataState> nextState(char c);

	/*
	 *  Returns this state epslon closure, i.e all states reachable by the empty word.
	 */
	Set<AutomataState> epslonClosure();

	/*
	 * Calculates the epslon closure.
	 * @param sets set os states to calculate the epsolon closure.
	 */
	void epslonClosure(Set<AutomataState> sets);
	
	Set<AutomataState> epslonTransitions();

	/*
	 * Returns all the states reachable by the epslon closure by the character.
	 */
	Set<AutomataState> nextStateOfEpslonClosure(char c);

	/*
	 * Checks if this state makes the automata non deterministic.
	 * If it does, throws NonDeterministicException.
	 */
	public void checkNonDetermininstic() throws NonDeterministicException;

	/*
	 * Get all possible transitions (by letter) from this state.
	 */
	public Set<Entry<Character, Set<AutomataState>>> getTransitions();

	/*
	 * Returns true if any state of the epslon closure accepts.
	 */
	public boolean epslonAccept();

	/*
	 * Checks if this state is an acceptable state.
	 */
	public boolean accepts();

	/*
	 * Returns the ID of the state.
	 */
	public int stateID();
	
	/* Sets the tag of the state.
	 */
	public void setTag(String tag);
	
	/* Returns the tag which was set by the method setTag of this state. If no such tag was set, returns an empty String.
	 */
	public String getTag();
}
