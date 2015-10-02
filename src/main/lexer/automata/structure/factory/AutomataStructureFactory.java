package main.lexer.automata.structure.factory;

import main.lexer.automata.structure.AutomataStructure;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

/*
 * For each new structure for the automata, there should be a subclass
 * of this one that returns the correct structure.
 */
public interface AutomataStructureFactory {
	/*
	 * This interface should implement the structure to be implemented.
	 * For example, to create a graph automata, this interface should return an
	 * AutomataStructureGraphImplementation and be passed to the constructor of the builder.
	 */
	public AutomataStructure createAutomataStructure();
}
