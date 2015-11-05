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

public class REPositive extends RegularExpression {

	private RegularExpression regularExpression;

	public REPositive(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}

	@Override
	public String toString() {
		return "(" + regularExpression + ")+";
	}

	@Override
	public Automata createAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		RegularExpression intermediate = regularExpression
				.concatenate(regularExpression.kleene());
		return intermediate.createAutomata();
	}

}
