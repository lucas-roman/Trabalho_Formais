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

	public boolean derivesAny(Set<ContextFreeSymbol> set) {
		for(ContextFreeSymbol symbol : productions) {
			if(set.contains(symbol)) {
				return true;
			}
		}
		return false;
	}

	public List<ContextFreeSymbol> getValue() {
		return productions;
	}

	public List<ContextFreeSymbol> commonSymbols(
			Set<ContextFreeSymbol> symbols) {
		List<ContextFreeSymbol> returnList = new ArrayList<>(productions);
		for(ContextFreeSymbol symbol : productions) {
			if(!symbols.contains(symbol)) {
				returnList.remove(symbol);
			}
		}
		return returnList;
	}
	
	@Override
	public String toString() {
		String result = "";
		for(ContextFreeSymbol symbol : productions) {
			result += symbol + " ";
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof ContextFreeProduction)) {
			return false;
		}
		ContextFreeProduction otherProduction = (ContextFreeProduction)other;
		if (productions.equals(otherProduction.productions)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return productions.hashCode();
	}

}
