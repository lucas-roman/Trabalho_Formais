package main.parser.grammar;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ContextFreeSymbol {
	
	public List<ContextFreeProduction> productionsForSymbol();

	public Set<ContextFreeTerminalSymbol> first();

	public void calculateFollow(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeSymbol symbol);

	public boolean addFollowOf(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeNonTerminal nt);
	
}
