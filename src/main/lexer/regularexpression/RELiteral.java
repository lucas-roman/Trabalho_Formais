package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

class RELiteral extends RegularExpression {

	private char recognizedChar;

	RELiteral(char c) {
		recognizedChar = c;
	}

	@Override
	public String toString() {
		return "" + recognizedChar;
	}


	@Override
	public Automata createAutomata() throws MissingStateException, InvalidStateException, InitialStateMissingException, IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(new AutomataStructureGraphFactory());
		builder.addState("q0");
		builder.addState("q1");
		builder.addTransition("q0", "q1", recognizedChar);
		builder.markAcceptState("q1");
		return builder.build();
	}

}
