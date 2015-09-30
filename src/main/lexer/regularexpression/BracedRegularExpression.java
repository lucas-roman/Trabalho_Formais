package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.automata.factory.AutomataBuilder;

class BracedRegularExpression extends RegularExpression {

	private RegularExpression regularExpression;

	public BracedRegularExpression(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}

	public String toString() {
		return "(" + regularExpression + ")";
	}

	
	//Override to forward to its child. 
	@Override
	public Automata createAutomata() throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		return regularExpression.createAutomata();
	}

}
