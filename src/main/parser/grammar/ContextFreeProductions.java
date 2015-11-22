package main.parser.grammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContextFreeProductions {

	private Map<ContextFreeNonTerminal, Set<List<ContextFreeSymbol>>> map;
	
	public ContextFreeProductions() {
		map = new HashMap<>();
	}
	
	public void addProduction(ContextFreeNonTerminal from, List<ContextFreeSymbol> to) {
		if(!map.containsKey(from)) {
			map.put(from, new HashSet<List<ContextFreeSymbol>>());
		}
		map.get(from).add(to);
	}
	
	public boolean removeProduction(ContextFreeNonTerminal head, List<ContextFreeSymbol> prod) {
		assert(map.containsKey(head));
		Set<List<ContextFreeSymbol>> proSet = map.get(head);
		if(proSet.remove(prod)) {
			if(proSet.size() == 0) {
				map.remove(head);
			}
			return true;
		}
		return false;
	}
	
	public Set<List<ContextFreeSymbol>> getProductionsFor(ContextFreeNonTerminal head) {
		assert(map.containsKey(head));
		return map.get(head);
	}
	
}
