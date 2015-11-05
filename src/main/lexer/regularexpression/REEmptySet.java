/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

class REEmptySet extends RegularExpression {

	@Override
	public String toString() {
		return "";
	}

	@Override
	public Automata createAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("q0");
		return builder.build();
	}


	@Override
	public RegularExpression kleene() {
		return RegularExpression.createRegularExpression('\0');
	}

	@Override
	public RegularExpression concatenate(RegularExpression other) {
		return this;
	}

	@Override
	public RegularExpression interrogation() {
		return RegularExpression.createRegularExpression('\0');
	}

	@Override
	public RegularExpression positive() {
		return this;
	}

	@Override
	public RegularExpression alternation(RegularExpression other) {
		return other;
	}
}
