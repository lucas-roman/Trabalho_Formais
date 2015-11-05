/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.grammar;

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
