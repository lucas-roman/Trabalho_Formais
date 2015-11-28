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

	private ParseTree parseTree;

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
	
	public String getCurrentTokenTag() {
		return currentToken.getWord();
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

	public boolean analyze()
			throws NotLLLanguageException, NonDeterministicGrammarException,
			TerminalMissingException {
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
				return false;
			}
			List<ContextFreeSymbol> symbols = productionToApply.getValue();
			for (int i = symbols.size() - 1; i >= 0; i--) {
				stack.push(symbols.get(i));
			}

		}
		return true;
	}

	public void addToTree(ContextFreeProduction contextFreeProduction) {
		if (parseTree == null) {
			parseTree = new ParseTree(grammar.head());
		}
		parseTree.addChildren(contextFreeProduction);

	}

	public ParseTree getParseTree() throws NotLLLanguageException,
			NonDeterministicGrammarException, TerminalMissingException {
		if(parseTree == null)
			analyze();
		return parseTree;
	}

	public void addTagToTree(String tag) {
		parseTree.addTag(tag);
	}

}
