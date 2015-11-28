package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.parser.grammar.exceptions.TerminalMissingException;

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
	public Map<ContextFreeTerminalSymbol, ContextFreeProduction> first() {
    	Map<ContextFreeTerminalSymbol, ContextFreeProduction> returnSet = new HashMap<>();
		returnSet.put(this, productionsForSymbol().get(0));
		return returnSet;
	}

	@Override
	public void calculateFollow(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeSymbol symbol) {
		return;
	}

	@Override
	public boolean addFollowOf(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeNonTerminal nt) {
		return false;
	}

	@Override
	public ContextFreeProduction consultTable(
			Map<ContextFreeNonTerminal, Map<ContextFreeTerminalSymbol, ContextFreeProduction>> table,
			Analyzer analyzer) throws TerminalMissingException {
		if(analyzer.currentTerminalSymbol().equals(this)) {
			analyzer.advanceToken();
			return new ContextFreeProduction();
		}
		return new ProductionError();
	}



}
