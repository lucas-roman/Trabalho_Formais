package main.parser.grammar;

import java.util.List;
import java.util.Map;
import java.util.Set;

import main.parser.grammar.exceptions.NonDeterministicGrammarException;
import main.parser.grammar.exceptions.TerminalMissingException;

public interface ContextFreeSymbol {
	
	public List<ContextFreeProduction> productionsForSymbol();

	public Map<ContextFreeTerminalSymbol, ContextFreeProduction> first() throws NonDeterministicGrammarException;

	public void calculateFollow(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeSymbol symbol) throws NonDeterministicGrammarException;

	public boolean addFollowOf(
			Map<ContextFreeNonTerminal, Set<ContextFreeTerminalSymbol>> returnMap,
			ContextFreeNonTerminal nt);

	public ContextFreeProduction consultTable(
			Map<ContextFreeNonTerminal, Map<ContextFreeTerminalSymbol, ContextFreeProduction>> table, Analyzer analyzer) throws TerminalMissingException;
	
}
