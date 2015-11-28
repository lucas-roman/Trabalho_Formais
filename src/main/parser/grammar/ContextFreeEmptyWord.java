package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ContextFreeEmptyWord implements ContextFreeSymbol, ContextFreeTerminalSymbol {

	private static ContextFreeEmptyWord __instance;

	@Override
	public List<ContextFreeProduction> productionsForSymbol() {
		return new ArrayList<ContextFreeProduction>();
	}
	
	public static ContextFreeEmptyWord getInstance() {
		if(__instance == null) {
			__instance = new ContextFreeEmptyWord();
		}
		return __instance;
	}
	
	private ContextFreeEmptyWord() {
		
	}
	
	@Override
	public boolean equals(Object other) {
		return other == this;
	}
	
	@Override
	public String toString() {
		return "EPSLON";
	}

	@Override
	public Map<ContextFreeTerminalSymbol, ContextFreeProduction> first() {
		Map<ContextFreeTerminalSymbol, ContextFreeProduction> returnSet = new HashMap<>();
		returnSet.put(this, new ContextFreeProduction());
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

}
