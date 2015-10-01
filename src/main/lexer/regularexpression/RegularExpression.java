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
			return new REEmptyString();
		}
		return new RELiteral(c);
	}

	// Returns a regular expression that does not accept any word.
	public static RegularExpression emptySetRegularExpression() {
		return new REEmptySet();
	}

	/*
	 * Public
	 */

	// Concatenates this regular expression with another regular expression
	// this.other
	public RegularExpression concatenate(RegularExpression other) {
		return new REConcatenation(this, other);
	}

	// Returns the union of this regular expression with the other regular
	// expression this | other
	public RegularExpression alternation(RegularExpression other) {
		return new REAlternation(this, other);
	}

	// Returns the closure of this regular expression RE*
	public RegularExpression kleene() {
		return new REKleene(this);
	}

	// Be this regular expression RE, returns (RE)
	public RegularExpression parenthesis() {
		return new REParenthesis(this);
	}
	
	// Returns RE? -> (RE | Epslon)
	public RegularExpression interrogation() {
		return new REInterrogation(this);
	}
	
	//Returns RE+  ->  RERE*
	public RegularExpression positive() {
		return new REPositive(this);
	}

	public abstract Automata createAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException;

	// Adds automata to builder.
	void decomposeAutomataIntoBuilder(AutomataBuilder builder, Automata automata)
			throws InvalidStateException {
		int index = builder.currentID();
		addStates(builder, automata, index);
		addTransitions(builder, automata, index);
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
		build.addState(aut.initialState().stateID() + lastID + "");
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
