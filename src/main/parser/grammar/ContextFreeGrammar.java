package main.parser.grammar;

import java.util.HashMap;
import java.util.Map;

public class ContextFreeGrammar {
	
	private Map<String, ContextFreeTerminalSymbol> objectMap;

	public ContextFreeGrammar() {
		objectMap = new HashMap<>();
		objectMap.put("", ContextFreeEmptyWord.getInstance());
	}
	
	// Returns the terminal symbol which corresponds to the given String.
	// The String "" corresponds to the empty word.
	public void createTerminalForString(String val) {
		if (!objectMap.containsKey(val)) {
			objectMap.put(val, new ContextFreeTerminal(val));
		}
	}

}
