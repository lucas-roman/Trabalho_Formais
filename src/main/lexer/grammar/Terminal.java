package main.lexer.grammar;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

public class Terminal implements GrammarSymbol {

	private char value;

	public Terminal(char value2) {
		value = value2;
	}

	@Override
	public String getSymbolValue() {
		return "" + value;
	}

}
