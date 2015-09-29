package main.lexer.automata.structure.factory;

import main.lexer.automata.structure.AutomataStructure;

//For each new structure for the automata, there should be a subclass of this that returns the correct structure.
public interface AutomataStructureFactory {
	
	//Should implement the structure to be implemented. For example, to create a graph automata, this should return a 
	//AutomataStructureGraphImplementation and be passed to the constructor of the builder.
	public AutomataStructure createAutomataStructure();

}
