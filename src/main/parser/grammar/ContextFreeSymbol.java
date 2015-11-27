package main.parser.grammar;

import java.util.List;
import java.util.Set;

public interface ContextFreeSymbol {
	
	public List<ContextFreeProduction> productionsForSymbol();

	public Set<ContextFreeTerminalSymbol> first();
	
}
