package main.lexer.automata.structure.graph;

/*
 * This class should not be used for anything but to test AutomataState, because it is a package private class. Other classes should access it by 
 * the classes which provide access to its interface
 */
public class ProvideTestService {
	
	public AutomataState createAutomataState(String stateName) {
		AutomataState state;
		if (stateName == null || stateName.equals("")) {
			state = InvalidState.getInstance();
		}
		else {
			state =  new AutomataStateImplementation(stateName);
		}
		return state;
	}

}
