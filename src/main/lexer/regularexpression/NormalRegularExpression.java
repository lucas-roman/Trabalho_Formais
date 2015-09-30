package main.lexer.regularexpression;

import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;

class NormalRegularExpression extends RegularExpression {

	private char recognizedChar;

	NormalRegularExpression(char c) {
		recognizedChar = c;
	}

	public String toString() {
		return "" + recognizedChar;
	}


	@Override
	protected void processBuilder(AutomataBuilder builder) throws InvalidStateException, MissingStateException, OverrideInitialStateException {
		builder.addState("q0");
		builder.addState("q1");
		builder.addTransition("q0", "q1", recognizedChar);
		builder.markAcceptState("q1");
		builder.markInitialState("q0");
	}

}
