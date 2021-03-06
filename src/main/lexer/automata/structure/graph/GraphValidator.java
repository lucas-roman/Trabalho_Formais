/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata.structure.graph;

import java.util.Set;

/*
 * This class should be used to count the number of elements a graph has.
 */
public class GraphValidator {

	private Set<GraphUnit> visit;

	private int count;

	private Set<GraphUnit> nextStates;

	public GraphValidator(GraphUnit uni, Set<GraphUnit> visited) {
		count = 0;
		nextStates = uni.nextStatesOfGraph();
		visit = visited;
	}

	public int countNeighborhood() {
		for (GraphUnit unit : nextStates) {
			if (!visit.contains(unit)) {
				count += unit.countNeighborhood(visit);
			}
		}
		return count + 1;
	}

}
