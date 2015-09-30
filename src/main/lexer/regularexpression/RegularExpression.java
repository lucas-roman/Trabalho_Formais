package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

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

	public abstract Automata createAutomata() throws MissingStateException, InvalidStateException, InitialStateMissingException, IllegalAutomataException;

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
