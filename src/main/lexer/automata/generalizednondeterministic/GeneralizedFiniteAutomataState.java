package main.lexer.automata.generalizednondeterministic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.lexer.regularexpression.RegularExpression;


public class GeneralizedFiniteAutomataState implements GeneralizedFiniteAutomataStateInterface {


	private RegularExpression selfTransition;
	
	private Map<GeneralizedFiniteAutomataState, RegularExpression> nextStates = new HashMap<>();
	
	private Set<GeneralizedFiniteAutomataState> predecessors = new HashSet<>();
	
	private int stateID;
	
	
	public GeneralizedFiniteAutomataState(int stateID) {
		this.stateID = stateID;
	}


	@Override
	public boolean accepts() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public int stateID() {
		return stateID;
	}

	@Override
	public void updateReferences() {
		// TODO Auto-generated method stub
		
	}
	
	public void addStateBy(RegularExpression re, GeneralizedFiniteAutomataState other) {
		nextStates.put(other, re);
	}


	public void markAsAccept() {
		// TODO Auto-generated method stub
		
	}

}
