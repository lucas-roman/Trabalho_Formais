package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

class NormalRegularExpression extends RegularExpression {

	private char recognizedChar;

	NormalRegularExpression(char c) {
		recognizedChar = c;
	}

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
