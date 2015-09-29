package main.lexer.automata.structure.graph;


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
