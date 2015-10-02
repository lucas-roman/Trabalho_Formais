package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

class REAlternation extends RegularExpression {

	private RegularExpression leftChild;
	private RegularExpression rightChild;

	public REAlternation(RegularExpression leftChild,
			RegularExpression rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	@Override
	public String toString() {
		return leftChild + "|" + rightChild;
	}

	@Override
	public Automata createAutomata() throws MissingStateException, InvalidStateException, InitialStateMissingException, IllegalAutomataException {
		Automata leftChildAutomata = leftChild.createAutomata();
		Automata rightChildAutomata = rightChild.createAutomata();
		AutomataBuilder builder = new AutomataBuilder(new AutomataStructureGraphFactory());
		builder.addState("0");
		decomposeAutomataIntoBuilder(builder, leftChildAutomata);
		decomposeAutomataIntoBuilder(builder, rightChildAutomata);
		int size1 = leftChildAutomata.size();
		for(AutomataState acceptState : leftChildAutomata.acceptStates()) {
			builder.markAcceptState(1 + acceptState.stateID() + "");
		}
		for(AutomataState acceptState : rightChildAutomata.acceptStates()) {
			builder.markAcceptState(size1 + acceptState.stateID() + 1 + "");
		}
		builder.addEmptyTransition("0", "1");
		builder.addEmptyTransition("0", size1 + 1 + "");
		return builder.build();
	}



}
