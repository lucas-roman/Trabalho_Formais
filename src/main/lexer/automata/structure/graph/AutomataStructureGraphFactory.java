/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata.structure.graph;

import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.factory.AutomataStructureFactory;
/*
 * A factory method for a graph structure.
 */
public class AutomataStructureGraphFactory implements AutomataStructureFactory{

	@Override
	public AutomataStructure createAutomataStructure() {
		return new AutomataStructureGraphImplementation();
	}
}
