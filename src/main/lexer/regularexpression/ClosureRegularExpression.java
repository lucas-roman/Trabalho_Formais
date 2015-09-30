package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;

class ClosureRegularExpression extends RegularExpression {

	private RegularExpression regularExpression;

	public ClosureRegularExpression(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}
	
	public String toString() {
		return regularExpression + "*";
	}

	@Override
	public Automata createAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		// TODO Auto-generated method stub
		return null;
	}


}
