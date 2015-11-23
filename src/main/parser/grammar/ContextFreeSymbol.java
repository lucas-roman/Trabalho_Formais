package main.parser.grammar;

import java.util.List;

public interface ContextFreeSymbol {
	
	public List<ContextFreeProduction> productionsForSymbol();

}
