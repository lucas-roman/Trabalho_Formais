package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

class REInterrogation extends RegularExpression{

	private RegularExpression regularExpression;

	public REInterrogation(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}

	@Override
	public String toString() {
		return regularExpression + "?";
	}

	@Override
	public Automata createAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		RegularExpression intermediate = regularExpression.alternation(RegularExpression.createRegularExpression('\0'));
		return intermediate.createAutomata();
	}

}
