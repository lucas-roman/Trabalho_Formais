package main.lexer.automata.generalizednondeterministic;

import java.util.HashMap;
import java.util.Map;

import main.lexer.regularexpression.RegularExpression;


public class GeneralizedFiniteAutomataState implements GeneralizedFiniteAutomataStateInterface {


	private RegularExpression selfTransition;
	
	private Map<RegularExpression, GeneralizedFiniteAutomataState> nextStates = new HashMap<>();
	
	
	@Override
	public boolean accepts() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int stateID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateReferences() {
		// TODO Auto-generated method stub
		
	}

}
