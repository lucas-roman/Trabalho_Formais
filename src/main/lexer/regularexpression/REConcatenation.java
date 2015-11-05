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

class REConcatenation extends RegularExpression {

	private RegularExpression leftChild;

	private RegularExpression rightChild;

	public REConcatenation(RegularExpression leftChild,
			RegularExpression rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	@Override
	public String toString() {
		return "(" + leftChild + rightChild + ")";
	}

	@Override
	public Automata createAutomata() throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		Automata leftChildAutomata = leftChild.createAutomata();
		Automata rightChildAutomata = rightChild.createAutomata();
		return leftChildAutomata.concatenate(rightChildAutomata);
	}

}
