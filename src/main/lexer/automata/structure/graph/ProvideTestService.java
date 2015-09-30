package main.lexer.automata.structure.graph;

import main.lexer.automata.exceptions.InvalidStateException;

/*
 * This class should not be used for anything but to test AutomataState, because it is a package private class. Other classes should access it by 
 * the classes which provide access to its interface
 */
public class ProvideTestService {
	
	public AutomataState createAutomataState(String stateName) throws InvalidStateException {
		AutomataState state;
		if (stateName == null || stateName.equals("")) {
			throw new InvalidStateException();
		}
		else {
			state =  new AutomataStateImplementation(stateName);
		}
		return state;
	}

}
