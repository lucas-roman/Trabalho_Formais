package main.lexer.grammar;

import java.util.List;

public class Production {
	
	private List<GrammarSymbol> production;
	
	public Production(List<GrammarSymbol> symbols) {
		production = symbols;
	}
	
	public boolean lastProduction() {
		for(GrammarSymbol gs : production) {
			if(!gs.isTerminal()) {
				return false;
			}
		}
		return true;
	}

}
