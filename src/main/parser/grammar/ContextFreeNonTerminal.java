package main.parser.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ContextFreeNonTerminal implements ContextFreeSymbol {

	private List<ContextFreeProduction> productions;
	
	private String val;
	
	@Override
	public List<ContextFreeProduction> productionsForSymbol() {
		return productions;
	}
	
	public ContextFreeNonTerminal(String val) {
		productions = new ArrayList<>();
		this.val = val;
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

	public boolean derivesOnly(Set<ContextFreeSymbol> canDeriveEmpty) {
		for(ContextFreeProduction production : productions) {
			if(production.derivesOnlyElementsOf(canDeriveEmpty)) {
				return true;
			}
		}
		return false;
	}

	public void removeProduction(List<ContextFreeSymbol> production) {
		List<ContextFreeProduction> newProductions = new ArrayList<>(productions);
		for(ContextFreeProduction prod : productions) {
			if(prod.isProduction(production)) {
				newProductions.remove(prod);
			}
		}
		productions = newProductions;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof ContextFreeNonTerminal)) {
			return false;
		}
		ContextFreeNonTerminal valOfOther = (ContextFreeNonTerminal)other;
		if(valOfOther.val.equals(val)) {
			return true;
		}
		return false;
	}

	public void reorganizeTransitionsForEmpty(
			Set<ContextFreeSymbol> symbolsThatDeriveEmptyWord) {
		for(ContextFreeProduction production : productions) {
			if(production.derivesAny(symbolsThatDeriveEmptyWord)) {
				List<ContextFreeSymbol> productionValue = new ArrayList<>(production.getValue());
				//TODO
			}
		}
	}

}
