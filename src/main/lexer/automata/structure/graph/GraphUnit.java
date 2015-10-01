package main.lexer.automata.structure.graph;

import java.util.Set;

/*
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */


/*
 * This represents a node.
 */
public interface GraphUnit {
	
	 //Counts how many neighbors this node has
	int countNeighborhood(Set<GraphUnit> visited);

	//Returns the next states of the graph.
	Set<GraphUnit> nextStatesOfGraph();

}
