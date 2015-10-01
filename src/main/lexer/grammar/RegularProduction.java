package main.lexer.grammar;

/*
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

public class RegularProduction {

	private Terminal terminal;
	private NonTerminal nonTerminal;

	public RegularProduction(Terminal term, NonTerminal symbol1) {
		this.terminal = term;
		this.nonTerminal = symbol1;
	}

	public RegularProduction(Terminal symbol1) {
		terminal = symbol1;
	}

	public RegularProduction(NonTerminal symbol1) {
		nonTerminal = symbol1;
	}

	public char terminal() {
		if (terminal != null) {
			return terminal.getSymbolValue().charAt(0);
		}
		return 0;
	}

	public NonTerminal nonTerminal() {
		return nonTerminal;
	}

	public boolean lastProduction() {
		return nonTerminal == null;
	}
	
	public String toString() {
		String result = "";
		if(terminal != null) {
			result += terminal.getSymbolValue();
		}
		if(nonTerminal != null) {
			result += nonTerminal.getSymbolValue();
		}
		return result;
	}


}
