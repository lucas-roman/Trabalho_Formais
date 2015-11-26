package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContextFreeGrammar {

	private Map<String, ContextFreeTerminalSymbol> terminalMap;
	private Map<String, ContextFreeNonTerminal> nonTerminalMap;
	private ContextFreeNonTerminal head;

	public ContextFreeGrammar() {
		terminalMap = new HashMap<>();
		terminalMap.put("", ContextFreeEmptyWord.getInstance());
		nonTerminalMap = new HashMap<>();
	}

	// Returns the terminal symbol which corresponds to the given String.
	// The String "" corresponds to the empty word.
	public ContextFreeTerminalSymbol createTerminalForString(String val) {
		if (!terminalMap.containsKey(val)) {
			terminalMap.put(val, new ContextFreeTerminal(val));
		}
		return terminalMap.get(val);
	}

	public Set<ContextFreeTerminalSymbol> terminalSet() {
		return new HashSet<>(terminalMap.values());
	}

	public ContextFreeNonTerminal createNonTerminalForString(String val) {
		if (!nonTerminalMap.containsKey(val)) {
			ContextFreeNonTerminal nt = new ContextFreeNonTerminal(val);
			if(head == null) {
				head = nt;
			}
			nonTerminalMap.put(val, nt);
		}
		return nonTerminalMap.get(val);
	}

	public Set<ContextFreeNonTerminal> nonTerminalSet() {
		return new HashSet<>(nonTerminalMap.values());
	}

	public void removeEmptyWord() {
		Set<ContextFreeSymbol> symbolsThatDeriveEmptyWord = symbolsThatDeriveEmptyWord();
		removeDerivationsByEmptyWord();
		reorganizeTransitions(symbolsThatDeriveEmptyWord);
		if(symbolsThatDeriveEmptyWord.contains(head)) {
			ContextFreeNonTerminal oldHead = head;
			head = createNonTerminalForString("NEWHEADEMPYWORD");
			ContextFreeProduction toOldHead = new ContextFreeProduction();
			toOldHead.addSymbol(oldHead);
			head.addProduction(toOldHead);
			ContextFreeProduction empty = new ContextFreeProduction();
			empty.addSymbol(ContextFreeEmptyWord.getInstance());
			head.addProduction(empty);
		}
	}
	
	private void reorganizeTransitions(
			Set<ContextFreeSymbol> symbolsThatDeriveEmptyWord) {
		for(ContextFreeNonTerminal nt : nonTerminalSet()) {
			nt.reorganizeTransitionsForEmpty(symbolsThatDeriveEmptyWord);
		}
		
	}

	private void removeDerivationsByEmptyWord() {
		Set<ContextFreeSymbol> emptyWord = new HashSet<ContextFreeSymbol>();
		emptyWord.add(ContextFreeEmptyWord.getInstance());
		for(ContextFreeNonTerminal nt : nonTerminalSet()) {
			if(nt.derivesOnly(emptyWord)) {
				List<ContextFreeSymbol> production = new ArrayList<>();
				production.add(ContextFreeEmptyWord.getInstance());
				nt.removeProduction(production);
			}
		}
	}
	
	private Set<ContextFreeSymbol> symbolsThatDeriveEmptyWord() {
		Set<ContextFreeSymbol> canDeriveEmpty = new HashSet<>();
		canDeriveEmpty.add(ContextFreeEmptyWord.getInstance());
		int oldSize = 0;
		int newSize = 0;
		do {
			oldSize = canDeriveEmpty.size();
			for (ContextFreeNonTerminal nt : nonTerminalSet()) {
				if (nt.derivesOnly(canDeriveEmpty)) {
					canDeriveEmpty.add(nt);
				}
			}
			newSize = canDeriveEmpty.size();
		} while (oldSize != newSize);
		canDeriveEmpty.remove(ContextFreeEmptyWord.getInstance());
		return canDeriveEmpty;
	}
	
	@Override
	public String toString() {
		String result = "";
		result += head + " -> ";
		for(ContextFreeProduction p : head.productionsForSymbol()) {
			result += p + " | ";
		}
		result = result.substring(0, result.length() - 2);
		result += '\n';
		for(ContextFreeNonTerminal nt : nonTerminalSet()) {
			if(nt != head) {
				result += nt + " -> ";
				for(ContextFreeProduction p : nt.productionsForSymbol()) {
					result += p + " | ";
				}
				result = result.substring(0, result.length() - 2);
				result += '\n';
			}
		}
		return result;
	}

}
