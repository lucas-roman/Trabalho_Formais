package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

class UnionRegularExpression extends RegularExpression {

	private RegularExpression leftChild;
	private RegularExpression rightChild;

	public UnionRegularExpression(RegularExpression leftChild,
			RegularExpression rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}
	
	public String toString() {
		return leftChild + "|" + rightChild;
	}

	@Override
	public Automata createAutomata() throws MissingStateException, InvalidStateException, InitialStateMissingException, IllegalAutomataException {
		Automata leftChildAutomata = leftChild.createAutomata();
		Automata rightChildAutomata = rightChild.createAutomata();
		AutomataBuilder builder = new AutomataBuilder(new AutomataStructureGraphFactory());
		getBuilderValueOf(builder, leftChildAutomata, rightChildAutomata);
		return builder.build();
	}

	private void getBuilderValueOf(AutomataBuilder builder, Automata leftChildAutomata,
			Automata rightChildAutomata) {
	}


}
