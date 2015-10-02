package main.lexer.automata.generalizednondeterministic;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;

public class GeneralizedFiniteAutomataStructure {
	
	
	
	public void removeState() {
		
	}
	
	private GeneralizedFiniteAutomataState randomState() {
		return null;
	}
	
	public GeneralizedFiniteAutomataStructure(Automata aut) {
		Automata result;
		try {
			result = aut.convert();
		} catch (DeterministicException e) {
			result = aut;
		}
		
	}

}
