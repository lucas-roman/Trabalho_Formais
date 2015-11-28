package main.parser.grammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.parser.grammar.exceptions.NonDeterministicGrammarException;
import main.parser.grammar.exceptions.NotLLLanguageException;
import main.parser.grammar.exceptions.TerminalMissingException;

public class LL1Table {
	
	private Map<ContextFreeNonTerminal, Map<ContextFreeTerminalSymbol, ContextFreeProduction>> table;

	public LL1Table(
			Map<ContextFreeNonTerminal, Map<ContextFreeTerminalSymbol, ContextFreeProduction>> first,
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> follow) throws NotLLLanguageException, NonDeterministicGrammarException {
		for(ContextFreeNonTerminal nt : first.keySet()) {
			Set<ContextFreeTerminalSymbol> firstToCompare = new HashSet<>(first.get(nt).keySet());
			Set<ContextFreeTerminalSymbol> followToCompare = follow.get(nt);
			firstToCompare.retainAll(followToCompare);
			if(firstToCompare.size() > 0) {
				throw new NotLLLanguageException();
			}
		}
		table = new HashMap<>();
		for(ContextFreeNonTerminal nt : first.keySet()) {
			table.put(nt, new HashMap<ContextFreeTerminalSymbol, ContextFreeProduction>());
			for(Entry<ContextFreeTerminalSymbol, ContextFreeProduction> entry : first.get(nt).entrySet()) {
				if(!entry.getKey().equals(ContextFreeEmptyWord.getInstance())) {
					table.get(nt).put(entry.getKey(), entry.getValue());
				}
			}
		}
		ContextFreeProduction prod = new ContextFreeProduction();
		prod.addSymbol(ContextFreeEmptyWord.getInstance());
		for(ContextFreeNonTerminal nt : follow.keySet()) {
			if(nt.first().containsKey(ContextFreeEmptyWord.getInstance())) {
				for(ContextFreeTerminalSymbol symb : follow.get(nt)) {
					table.get(nt).put(symb, prod);
				}
			}
		}
	}
	
	public ContextFreeProduction consult(ContextFreeSymbol symbolToConsult, Analyzer analyzer) throws TerminalMissingException {
		return symbolToConsult.consultTable(table, analyzer);
	}

}
