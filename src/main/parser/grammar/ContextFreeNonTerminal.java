package main.parser.grammar;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeNonTerminal implements ContextFreeSymbol {

	private List<ContextFreeProduction> productions;
	
	@Override
	public List<ContextFreeProduction> productionsForSymbol() {
		return productions;
	}
	
	public ContextFreeNonTerminal() {
		productions = new ArrayList<>();
	}
	
	public void addProduction(ContextFreeProduction prod) {
		productions.add(prod);
	}
	
	public void addProduction(List<ContextFreeSymbol> symbols) {
		ContextFreeProduction prod = new ContextFreeProduction();
		for(ContextFreeSymbol symb: symbols) {
			prod.addSymbol(symb);
		}
		productions.add(prod);
	}

}
