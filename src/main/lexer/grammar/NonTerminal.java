/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.grammar;

import java.util.ArrayList;
import java.util.List;

public class NonTerminal implements GrammarSymbol {

	private String value;

	private List<RegularProduction> transitions = new ArrayList<>();

	public NonTerminal(String c) {
		value = c;
	}

	@Override
	public String getSymbolValue() {
		return value;
	}

	public Iterable<RegularProduction> getProductions() {
		return transitions;
	}

	public void addProduction(NonTerminal symbol1) {
		RegularProduction rp = new RegularProduction(symbol1);
		transitions.add(rp);
	}

	public void addProduction(Terminal symbol1) {
		RegularProduction rp = new RegularProduction(symbol1);
		transitions.add(rp);
	}

	public void addProduction(Terminal term, NonTerminal symbol1) {
		RegularProduction rp = new RegularProduction(term, symbol1);
		transitions.add(rp);
	}

}
