package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContextFreeTerminal implements ContextFreeSymbol, ContextFreeTerminalSymbol {
	
	private String terminalValue;
	
	@Override
	public List<ContextFreeProduction> productionsForSymbol() {
		List<ContextFreeProduction> returnList = new ArrayList<>();
		ContextFreeProduction prod = new ContextFreeProduction();
		prod.addSymbol(this);
		returnList.add(prod);
		return returnList;
	}
	
    ContextFreeTerminal(String val) {
		terminalValue = val;
	}
	
    
    @Override
	public boolean equals(Object other) {
		if(!(other instanceof ContextFreeTerminal)) {
			return false;
		}
		ContextFreeTerminal valOfOther = (ContextFreeTerminal)other;
		if(valOfOther.terminalValue.equals(terminalValue)) {
			return true;
		}
		return false;
	}
    
    @Override
	public int hashCode() {
		return terminalValue.hashCode();
	}
    
    @Override
	public String toString() {
		return terminalValue;
	}
    
    @Override
	public Set<ContextFreeTerminalSymbol> first() {
		Set<ContextFreeTerminalSymbol> returnSet = new HashSet<>();
		returnSet.add(this);
		return returnSet;
	}

}
