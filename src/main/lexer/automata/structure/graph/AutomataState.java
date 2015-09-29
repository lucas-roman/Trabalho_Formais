package main.lexer.automata.structure.graph;

import java.util.Set;

import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.NonDeterministicException;

public interface AutomataState extends GraphUnit {

	
	/* This method throws an exception if either the state that calls it or the state passed to it are invalid states. Also, as a means to signal if 
	 * this state is part of a non deterministic finite automata or a deterministic finite automata, this method returns false for non deterministic automatas and true for deterministic
	 * automatas. Note, however, that an arbitrary state of the automata has no way of knowing about other states of the automata, and it is the automata's implementer responsability
	 * to treat is as so.
	 */
	public void addTransition(AutomataState stateTo, char transition)
			throws InvalidStateException;

	
	/* Adds a transition from this state to another using the empty word.
	 */
	public void addEpslonTransition(AutomataState to)
			throws InvalidStateException;

	
	/* Checks if current state is valid. If not, throws an exception.
	 */
	public void checkValidState() throws InvalidStateException;
	
	
	/* This method should be used to check if the string is part of the language described by the automata.
	 * Note: Do not call this method on a state other than the initial state. (For automata implementer).
	 * To begin search, the word to be checked if part of the language or not should be passed as string, with its index equal to 0,
	 * to indicate the beginning of the word.
	 */
	public boolean process(String string, int index);
	
	/* Returns all the states which this state can reach by the character c.
	 */
	Set<AutomataState> nextState(char c);
	
	/* Returns this state epslon closure, i.e all states reachable by the empty word.
	 */
	Set<AutomataState> epslonClosure();
	
	void epslonClosure(Set<AutomataState> sets);
	
	Set<AutomataState> nextStateOfEpslonClosure(char c);
	
	void markAsAccept();


	/* Should be called on the initial state to update references on all states from a state to a new state.
	 * Should be used like this: instance.updateReference(state, newState, null)
	 */

	public void checkNonDetermininstic() throws NonDeterministicException;
	
	public Set<Character> getTransitions();
	
	public boolean epslonAccept();

	public boolean accepts();
	

}
