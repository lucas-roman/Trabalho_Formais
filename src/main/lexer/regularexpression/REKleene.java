package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

class REKleene extends RegularExpression {

	private RegularExpression regularExpression;

	public REKleene(RegularExpression regularExpression) {
		this.regularExpression = regularExpression;
	}
	
	public String toString() {
		return regularExpression + "*";
	}

	@Override
	public Automata createAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(new AutomataStructureGraphFactory());
		builder.addState("0");
		Automata aut = regularExpression.createAutomata();
		decomposeAutomataIntoBuilder(builder, aut);
		builder.markAcceptState("0");
		builder.addEmptyTransition("0", "1");
		for(AutomataState acceptState : aut.acceptStates()) {
			builder.markAcceptState(acceptState.stateID() + 1 + "");
			builder.addEmptyTransition(acceptState.stateID() + 1 + "", "1");
		}
		return builder.build();
	}



}
