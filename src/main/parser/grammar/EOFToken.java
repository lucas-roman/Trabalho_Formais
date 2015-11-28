package main.parser.grammar;

import main.lexer.LexicalToken;

public class EOFToken extends LexicalToken {

	private static EOFToken __instance;

	public static EOFToken getInstance() {
		if(__instance == null) {
			__instance = new EOFToken();
		}
		return __instance;
	}
	
	private EOFToken() {
		super("EOF", "EOF");
	}
	
}
