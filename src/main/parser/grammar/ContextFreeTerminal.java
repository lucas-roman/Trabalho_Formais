package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContextFreeTerminal implements ContextFreeSymbol, ContextFreeTerminalSymbol {
	
	private String terminalValue;
	
	private static Map<String, ContextFreeTerminalSymbol> objectMap;
	
	static {
		objectMap = new HashMap<>();
		objectMap.put("", ContextFreeEmptyWord.getInstance());
	}

	@Override
	public List<ContextFreeProduction> productionsForSymbol() {
		List<ContextFreeProduction> returnList = new ArrayList<>();
		ContextFreeProduction prod = new ContextFreeProduction();
		prod.addSymbol(this);
		returnList.add(prod);
		return returnList;
	}
	
	private ContextFreeTerminal(String val) {
		terminalValue = val;
	}
	
	//Returns the terminal symbol which corresponds to the given String.
	//The String "" corresponds to the empty word.
	public static ContextFreeTerminalSymbol terminalForString(String val) {
		if(!objectMap.containsKey(val)) {
			objectMap.put(val, new ContextFreeTerminal(val));
		}
		return objectMap.get(val);
	}
	

}
