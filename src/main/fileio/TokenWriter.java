/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.fileio;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import main.lexer.LexicalToken;

/*
 * This class is responsible for writing tokens to a given file.
 */
public class TokenWriter {

	private String fileName;

	public TokenWriter(String fileName) {
		this.fileName = fileName;
	}

	public void writeToken(List<LexicalToken> tokens) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(fileName);
		for(LexicalToken token : tokens) {
			pw.print("[" + token.getTag() + ", " + token.getWord() + "]");
			pw.print("\n");
		}
		pw.close();
	}

}
