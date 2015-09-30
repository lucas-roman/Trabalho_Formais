package main.lexer.regularexpression;

import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;

class EmptyRegularExpression extends RegularExpression {
	
	public String toString() {
		return "°";
	}

	@Override
	protected void processBuilder(AutomataBuilder builder)
			throws InvalidStateException, MissingStateException,
			OverrideInitialStateException {
		builder.addState("q0");
		builder.markInitialState("q0");
	}

}
