package main.lexer.automata;

import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.structure.AutomataStructure;



public class DeterministicAutomata implements Automata{
	
	
	private AutomataStructure stateImpl;

	public DeterministicAutomata(AutomataStructure stateManager) {
		stateImpl = stateManager;
	}
	
	@Override
	public boolean accepts(String string) {
		return stateImpl.check(string);
	}

	@Override
	public Automata convert() throws DeterministicException {
		throw new DeterministicException();
	}

}
