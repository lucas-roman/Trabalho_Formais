package main.lexer.automata.structure.graph;

import java.util.Set;

public interface GraphUnit {
	
	int countNeighborhood(Set<GraphUnit> visited);

	Set<GraphUnit> nextStatesOfGraph();

}
