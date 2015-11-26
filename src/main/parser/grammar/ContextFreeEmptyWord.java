package main.parser.grammar;

import java.util.ArrayList;
import java.util.List;

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

}
