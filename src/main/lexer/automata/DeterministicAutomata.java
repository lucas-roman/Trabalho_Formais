package main.lexer.automata;

import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.structure.AutomataStructure;


/*
 * Deterministic automata. Prefer this over non deterministic automata for performance.
 */
public class DeterministicAutomata extends AutomataSkeleton{
	
	
	

	//Creates a Deterministic Automata passing a structure to it.
	public DeterministicAutomata(AutomataStructure structure) {
		super(structure);
	}
	


	@Override
	public Automata convert() throws DeterministicException {
		throw new DeterministicException();
	}



}
