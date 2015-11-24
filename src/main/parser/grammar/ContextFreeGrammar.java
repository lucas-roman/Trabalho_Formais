package main.parser.grammar;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ContextFreeGrammar {
	
	private Map<String, ContextFreeTerminalSymbol> terminalMap;
	private Map<String, ContextFreeNonTerminal> nonTerminalMap;

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
			nonTerminalMap.put(val, new ContextFreeNonTerminal(val));
		}
		return nonTerminalMap.get(val);
	}

	public Set<ContextFreeNonTerminal> nonTerminalSet() {
		return new HashSet<>(nonTerminalMap.values());
	}

	public void removeEmptyWord() {
		//TODO
	}

}
