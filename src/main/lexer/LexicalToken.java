/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer;

public class LexicalToken {

	private String tag;
	private String wordRead;

	public LexicalToken(String tag, String wordRead) {
		this.tag = tag;
		this.wordRead = wordRead;
	}

	public String getTag() {
		return tag;
	}

	public String getWord() {
		return wordRead;
	}
}
