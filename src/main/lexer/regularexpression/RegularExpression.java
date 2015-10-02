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

/*
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

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
