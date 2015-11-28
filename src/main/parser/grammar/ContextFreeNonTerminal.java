package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.parser.grammar.exceptions.NonDeterministicGrammarException;

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
		if(!productions.contains(prod))
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
	
	@Override
	public int hashCode() {
		return val.hashCode();
	}

	public void reorganizeTransitionsForEmpty(
			Set<ContextFreeSymbol> symbolsThatDeriveEmptyWord) {
		List<ContextFreeProduction> productionsCopy = new ArrayList<>(productions);
		for(ContextFreeProduction production : productionsCopy) {
			if(production.derivesAny(symbolsThatDeriveEmptyWord)) {
				List<ContextFreeSymbol> commonSymbols = production.commonSymbols(symbolsThatDeriveEmptyWord);
				List<ContextFreeSymbol> productionValue = new ArrayList<>(production.getValue());
				createNewProductions(commonSymbols, productionValue);
			}
		}
	}

	private void createNewProductions(List<ContextFreeSymbol> commonSymbols,
			List<ContextFreeSymbol> productionValue) {
		List<List<ContextFreeSymbol>> powerSet = SetUtils.powerSet(commonSymbols);
		for(List<ContextFreeSymbol> elements : powerSet) {
			List<ContextFreeSymbol> toAdd = new ArrayList<>(productionValue);
			for(ContextFreeSymbol symbol : elements) {
				toAdd.remove(symbol);
			}
			if(!toAdd.isEmpty())
				
				addProduction(toAdd);
		}
	}
	
	@Override
	public String toString() {
		return val;
	}

	@Override
	public Map<ContextFreeTerminalSymbol, ContextFreeProduction> first() throws NonDeterministicGrammarException {
		Map<ContextFreeTerminalSymbol, ContextFreeProduction> returnMap = new HashMap<>();
		for(ContextFreeProduction production: productions) {
			List<ContextFreeSymbol> productionList = production.getValue();
			for(ContextFreeSymbol symbol : productionList) {
				for(ContextFreeTerminalSymbol valToPut : symbol.first().keySet()) {
					if(returnMap.containsKey(valToPut)) {
						throw new NonDeterministicGrammarException();
					}
					returnMap.put(valToPut,production);
				}
				if(!symbol.first().containsValue(ContextFreeEmptyWord.getInstance()))
					break;
			}
		}
		return returnMap;
	}

	@Override
	public void calculateFollow(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeSymbol symbol) throws NonDeterministicGrammarException {
		if(returnMap.containsKey(this)) {
			for(ContextFreeTerminalSymbol symbol2 : symbol.first().keySet()) {
				if(!symbol2.equals(ContextFreeEmptyWord.getInstance())) {
					returnMap.get(this).add(symbol2);
				}
			}
		}
		
	}

	@Override
	public boolean addFollowOf(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeNonTerminal nt) {
		int oldSize = returnMap.get(this).size();
		returnMap.get(this).addAll(returnMap.get(nt));
		int newSize = returnMap.get(this).size();
		return oldSize != newSize;
	}


	@Override
	public ContextFreeProduction consultTable(
			Map<ContextFreeNonTerminal, Map<ContextFreeTerminalSymbol, ContextFreeProduction>> table,
			Analyzer analyzer) {
		ContextFreeTerminalSymbol terminal = analyzer.currentTerminalSymbol();
		if(!table.get(this).containsKey(terminal)) {
			return new ProductionError();
		}
		return table.get(this).get(terminal);
	}


}
