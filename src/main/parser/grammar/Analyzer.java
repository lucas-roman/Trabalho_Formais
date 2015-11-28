package main.parser.grammar;

import java.util.List;
import java.util.Stack;

import main.lexer.LexicalToken;
import main.parser.grammar.exceptions.InvalidSentenceException;
import main.parser.grammar.exceptions.NonDeterministicGrammarException;
import main.parser.grammar.exceptions.NotLLLanguageException;
import main.parser.grammar.exceptions.TerminalMissingException;

public class Analyzer {

	private List<LexicalToken> tokens;

	private LexicalToken currentToken;

	private ContextFreeGrammar grammar;

	private int i = 0;

	public void advanceToken() {
		if (i < tokens.size() - 1) {
			i++;
			currentToken = tokens.get(i);
		} else {
			currentToken = EOFToken.getInstance();
		}
	}

	public ContextFreeTerminalSymbol currentTerminalSymbol()
			throws TerminalMissingException {
		return grammar.getTerminalFor(currentToken.getTag());
	}

	public Analyzer(ContextFreeGrammar grammar, List<LexicalToken> tokenList) {
		this.grammar = grammar;
		tokens = tokenList;
		if (tokenList.isEmpty()) {
			currentToken = EOFToken.getInstance();
		} else {
			currentToken = tokenList.get(0);
		}
	}

	public Stack<ContextFreeProduction> analyze()
			throws NotLLLanguageException, NonDeterministicGrammarException,
			TerminalMissingException, InvalidSentenceException {
		LL1Table table = grammar.createTable();
		Stack<ContextFreeProduction> resultStack = new Stack<>();
		ContextFreeProduction production = new ContextFreeProduction();
		production.addSymbol(grammar.head());
		resultStack.push(production);
		Stack<ContextFreeSymbol> stack = new Stack<>();
		stack.push(grammar.head());
		while (!stack.isEmpty()) {
			ContextFreeSymbol symbol = stack.pop();
			ContextFreeProduction productionToApply = table.consult(symbol,
					this);
			if (!productionToApply.getValue().isEmpty()) {
				resultStack.push(productionToApply);
			}
			if (!productionToApply.checkValid()) {
				throw new InvalidSentenceException();
			}
			List<ContextFreeSymbol> symbols = productionToApply.getValue();
			for (int i = symbols.size() - 1; i >= 0; i--) {
				stack.push(symbols.get(i));
			}

		}
		return resultStack;
	}

}
