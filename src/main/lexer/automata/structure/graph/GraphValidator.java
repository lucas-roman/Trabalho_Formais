package main.lexer.automata.structure.graph;

import java.util.Set;

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
				visit.add(unit);
				count += unit.countNeighborhood(visit);
			}
		}
		return count + 1;
	}

}
