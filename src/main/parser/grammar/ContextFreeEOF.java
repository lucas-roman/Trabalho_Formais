package main.parser.grammar;


public class ContextFreeEOF extends ContextFreeTerminal {

	private static ContextFreeEOF __instance;

	private ContextFreeEOF() {
		super("EOF");
	}
	
	public static ContextFreeEOF getInstance() {
		if(__instance == null) {
			__instance = new ContextFreeEOF();
		}
		return __instance;
	}


}
