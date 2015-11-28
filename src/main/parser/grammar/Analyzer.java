package main.parser.grammar;

import java.util.List;
import java.util.Stack;

import main.lexer.LexicalToken;
import main.parser.grammar.exceptions.NonDeterministicGrammarException;
import main.parser.grammar.exceptions.NotLLLanguageException;

public class Analyzer {
	
	private List<LexicalToken> tokens;
	
	private LexicalToken currentToken;
	
	private ContextFreeGrammar grammar;
	
	private int i = 0;
	
	public void advanceToken() {
		if(i < tokens.size()-1) {
			i++;
			currentToken = tokens.get(i);
		}
		else {
			currentToken = EOFToken.getInstance();
		}
	}
	
	public ContextFreeTerminalSymbol currentTerminalSymbol() {
		return grammar.getTerminalFor(currentToken.getTag());
	}
	
	public Analyzer(ContextFreeGrammar grammar, List<LexicalToken> tokenList) {
		this.grammar = grammar;
		tokens = tokenList;
		if(tokenList.isEmpty()) {
			currentToken = EOFToken.getInstance();
		}
		else {
			currentToken = tokenList.get(0);
		}
	}
	
	public boolean analyze() throws NotLLLanguageException, NonDeterministicGrammarException {
		//TODO
		//SHOULD USE GRAMMAR AND A STACK TO ANALYZE TOKEN LIST.
		LL1Table table = grammar.createTable();
		Stack<ContextFreeSymbol> stack = new Stack<>();
		stack.push(grammar.head());
		return false;
	}

}
