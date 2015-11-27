package main.parser.grammar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
	public Set<ContextFreeTerminalSymbol> first() {
		Set<ContextFreeTerminalSymbol> returnSet = new HashSet<>();
		returnSet.add(this);
		return returnSet;
	}

}
