package main.lexer.regularexpression;

import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;

//Defines basic structure of a regular expression
public abstract class RegularExpression {

	/*
	 * Static
	 */

	// Returns the regular expression which recognizes char c. If char c is 0,
	// returns a regular expression which recognizes the
	// empty word.
	public static RegularExpression createRegularExpression(char c) {
		if (c == 0) {
			return new EmptyWordRegularExpression();
		}
		return new NormalRegularExpression(c);
	}

	// Returns a regular expression that does not accept any word.
	public static RegularExpression emptyRegularExpression() {
		return new EmptyRegularExpression();
	}

	/*
	 * Public
	 */

	// Concatenates this regular expression with another regular expression
	// this.other
	public RegularExpression concatenate(RegularExpression other) {
		return new ConcatRegularExpresion(this, other);
	}

	// Returns the union of this regular expression with the other regular
	// expression this | other
	public RegularExpression union(RegularExpression other) {
		return new UnionRegularExpression(this, other);
	}

	// Returns the closure of this regular expression RE*
	public RegularExpression closure() {
		return new ClosureRegularExpression(this);
	}

	// Be this regular expression RE, returns (RE)
	public RegularExpression brace() {
		return new BracedRegularExpression(this);
	}

	public abstract Automata createAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException;

	// Returns a builder without the initial state nor the final state marked
	// for the given automata.
	void getBuilderValueOf(AutomataBuilder builder, Automata aut1, Automata aut2, int firstIndex)
			throws InvalidStateException {
		addStates(builder, aut1, firstIndex);
		// Here, builder's current id is one past last state value. So, it is
		// safe to add it with the state value from other automata.
		int lastID = builder.currentID();
		// We need to pass the lastID so we don't have conflicts regarding state
		// names.
		addStates(builder, aut2, lastID);
		addTransitions(builder, aut1, firstIndex);
		addTransitions(builder, aut2, lastID);
	}

	private void addTransitions(AutomataBuilder builder,
			Automata aut2, int lastID) throws InvalidStateException {
		for (AutomataState state : aut2.getStates()) {
			for(Entry<Character, Set<AutomataState>> keyPair : state.getTransitions()) {
				for(AutomataState other : keyPair.getValue()) {
					try {
						builder.addTransition(state.stateID() + lastID + "",
								other.stateID() + lastID + "", keyPair.getKey());
					} catch (MissingStateException e) {
						// All states already added. Safe to ignore.
					}
				}
			}
			for (AutomataState epslonReachable : state.epslonClosure()) {
				// Ignore empty transitions to same state
				if (epslonReachable != state) {
					try {
						builder.addEmptyTransition(state.stateID() + lastID
								+ "", epslonReachable.stateID() + lastID + "");
					} catch (MissingStateException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}


	private void addStates(AutomataBuilder build, Automata aut,
			int lastID) throws InvalidStateException {
		for (AutomataState state : aut.getStates()) {
			build.addState(state.stateID() + lastID + "");
		}
	}


	/*
	 * String representation
	 */

	// RE1 union RE2 = RE1|RE2
	// RE1 concat RE2 = RE1RE2
	// RE closure = RE1
	// RE brace = (RE)
	// Normal RE = a b c d e etc.
	// Empty word RE = ª
	// Empty RE = °

	// To implement :

	// RE?
	// RE+

}
