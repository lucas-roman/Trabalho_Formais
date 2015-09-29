package main.lexer.automata.structure.graph;

import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.factory.AutomataStructureFactory;

public class AutomataStructureGraphFactory implements AutomataStructureFactory{

	@Override
	public AutomataStructure createAutomataStructure() {
		return new AutomataStructureGraphImplementation();
	}
	
	

}
