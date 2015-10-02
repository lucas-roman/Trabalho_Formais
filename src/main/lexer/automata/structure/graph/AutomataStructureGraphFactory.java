package main.lexer.automata.structure.graph;

import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.factory.AutomataStructureFactory;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

/*
 * A factory method for a graph structure.
 */
public class AutomataStructureGraphFactory implements AutomataStructureFactory{

	@Override
	public AutomataStructure createAutomataStructure() {
		return new AutomataStructureGraphImplementation();
	}
}
