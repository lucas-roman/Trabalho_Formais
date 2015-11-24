package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextFreeProduction {
	
	private List<ContextFreeSymbol> productions;

	public void addSymbol(ContextFreeSymbol contextFreeSymbol) {
		productions.add(contextFreeSymbol);
	}
	
	public ContextFreeProduction() {
		productions = new ArrayList<>();
	}

	public boolean derivesOnlyElementsOf(Set<ContextFreeSymbol> canDeriveEmpty) {
		Set<ContextFreeSymbol> symbolsInProduction = new HashSet<>(productions);
		symbolsInProduction.removeAll(canDeriveEmpty);
		return symbolsInProduction.isEmpty();
	}

	public boolean isProduction(List<ContextFreeSymbol> production) {
		if(production.size() != productions.size()) 
			return false;
		for(int i = 0; i < productions.size(); i++) {
			if(!productions.get(i).equals(production.get(i))) {
				return false;
			}
		}
		return true;
	}

	public boolean derivesAny(Set<ContextFreeSymbol> symbolsThatDeriveEmptyWord) {
		//TODO
		return false;
	}

	public List<ContextFreeSymbol> getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
