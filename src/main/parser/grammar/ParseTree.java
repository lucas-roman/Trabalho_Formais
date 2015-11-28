package main.parser.grammar;

import java.util.ArrayList;
import java.util.List;

public class ParseTree {

	private ContextFreeSymbol symbol;

	private String tag = "";

	private List<ParseTree> children = new ArrayList<>();

	public ParseTree(ContextFreeSymbol symbol) {
		this.symbol = symbol;
	}

	public boolean addChildren(ContextFreeProduction production) {
		if (!children.isEmpty()) {
			for (ParseTree child : children) {
				if (child.isNonTerminal()) {
					if (child.addChildren(production)) {
						return true;
					}
				}
			}
		} else {
			for (ContextFreeSymbol symbol : production.getValue()) {
				children.add(new ParseTree(symbol));
			}
			return true;
		}
		return false;
	}

	public boolean isEmpty() {
		return symbol instanceof ContextFreeEmptyWord;
	}

	public boolean isTerminal() {
		return symbol instanceof ContextFreeTerminal;
	}

	public boolean isNonTerminal() {
		return symbol instanceof ContextFreeNonTerminal;
	}

	public String toString() {
		if (isEmpty()) {
			return "";
		} else if (isTerminal()) {
			String result = symbol.toString() + " -> " + tag + '\n';
			return result;
		}
		String result = symbol.toString() + " -> ";
		for (ParseTree child : children) {
			result += child.symbol + " ";
		}
		result += '\n';
		for (ParseTree child : children) {
			result += child.toString();
		}
		return result;
	}

	public boolean addTag(String tag) {
		if (isTerminal()) {
			if (!this.tag.equals("")) {
				return false;
			}
			else {
				this.tag = tag;
				return true;
			}
		}
		else {
			for(ParseTree node : children) {
				if(node.addTag(tag)) {
					return true;
				}
			}
		}
		return false;
	}

}
