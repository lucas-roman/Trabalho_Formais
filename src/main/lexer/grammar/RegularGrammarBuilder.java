package main.lexer.grammar;

import java.util.HashMap;
import java.util.Map;

import main.lexer.grammar.exceptions.NonTerminalMissingException;
import main.lexer.grammar.exceptions.StartSymbolMissingException;
import main.lexer.grammar.exceptions.TerminalMissingException;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

public class RegularGrammarBuilder {

	private Map<String, NonTerminal> nonTermMap = new HashMap<>();

	private Map<Character, Terminal> termMap = new HashMap<>();

	private NonTerminal startSymbol;

	private boolean emptyWord = false;

	public RegularGrammar createGrammar() throws StartSymbolMissingException {
		if(startSymbol == null) {
			throw new StartSymbolMissingException();
		}
		RegularGrammar rg = new RegularGrammar(nonTermMap.values(), termMap.values(),
				startSymbol);
		rg.emptyWordEnable(emptyWord);
		return rg;
	}

	public void markStartSymbol(NonTerminal nT) {
		startSymbol = nT;
	}

	public Terminal addTerminal(char value) {
		if (!termMap.containsKey(value)) {
			Terminal newT = new Terminal(value);
			termMap.put(value, newT);
		}
		return termMap.get(value);
	}

	public NonTerminal addNonTerminal(String c) {
		if (!nonTermMap.containsKey(c)) {
			NonTerminal newNT = new NonTerminal(c);
			nonTermMap.put(c, newNT);
		}
		return nonTermMap.get(c);
	}

	public Terminal getTerminalOf(char term) throws TerminalMissingException {
		if(!termMap.containsKey(term)) {
			throw new TerminalMissingException();
		}
		return termMap.get(term);
	}

	public NonTerminal getNonTerminalOf(String term) throws NonTerminalMissingException {
		if(!nonTermMap.containsKey(term)) {
			throw new NonTerminalMissingException();
		}
		return nonTermMap.get(term);
	}

	public void addProduction(NonTerminal head, Terminal symbol1, NonTerminal symbol2) {
		head.addProduction(symbol1, symbol2);

	}

	public void addProduction(NonTerminal head, Terminal symbol1) {
		head.addProduction(symbol1);

	}

	public void addProduction(NonTerminal head, NonTerminal symbol1) {
		head.addProduction(symbol1);

	}

	//Let the automata recognize the empty word.
	public void addEmptyWord() {
		emptyWord  = true;
	}


}
