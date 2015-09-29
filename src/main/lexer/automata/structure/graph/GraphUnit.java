package main.lexer.automata.structure.graph;

import java.util.Set;

/*
 * This represents a node.
 */
public interface GraphUnit {
	
	 //Counts how many neighbors this node has
	int countNeighborhood(Set<GraphUnit> visited);

	//Returns the next states of the graph.
	Set<GraphUnit> nextStatesOfGraph();

}
