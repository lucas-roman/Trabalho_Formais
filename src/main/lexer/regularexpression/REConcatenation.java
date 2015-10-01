package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

/*
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

class REConcatenation extends RegularExpression {

	private RegularExpression leftChild;

	private RegularExpression rightChild;

	public REConcatenation(RegularExpression leftChild,
			RegularExpression rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public String toString() {
		return "" + leftChild + rightChild;
	}


	public Automata createAutomata() throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		Automata leftChildAutomata = leftChild.createAutomata();
		Automata rightChildAutomata = rightChild.createAutomata();
		AutomataBuilder build = new AutomataBuilder(new AutomataStructureGraphFactory());
		decomposeAutomataIntoBuilder(build, leftChildAutomata);
		decomposeAutomataIntoBuilder(build, rightChildAutomata);
		int aut1Size = leftChildAutomata.size();
		for (AutomataState acceptState : leftChildAutomata.acceptStates()) {
			build.addEmptyTransition(acceptState.stateID() + "",
					rightChildAutomata.initialState().stateID() + aut1Size + "");
		}
		for (AutomataState acceptState : rightChildAutomata.acceptStates()) {
			build.markAcceptState(acceptState.stateID() + aut1Size + "");
		}
		return build.build();
	}
	
		


}
