/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */
package main.lexer.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import main.lexer.Lexema;
import main.lexer.model.commandline.exceptions.IllegalStructureOfText;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

public class LangReader {

	private Scanner scan;
	private List<Lexema> lexemas;

	public LangReader(){
	}

	public List<Lexema> readFile (File file) throws IllegalStructureOfText, IllegalRegularExpressionException{
		File aux_file = file;
		try{
			this.scan = new Scanner(aux_file);
			String aux = scan.nextLine();
			aux.trim();
			String checker = aux.trim();
			while(scan.hasNextLine()){
				while(checker.length() == 0){
					aux = this.scan.nextLine();
					checker = aux.trim();
				}
				if (Character.isUpperCase(aux.charAt(0))){
					String tag = aux;
					aux = this.scan.nextLine();
					while (checker.length() == 0) {
						aux = this.scan.nextLine();
						checker = aux.trim();
					}
					aux.trim();
					String re = aux;
					lexemas.add(new Lexema(tag, re));
				} else {
					throw new IllegalStructureOfText();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found! Try again.");
			e.printStackTrace();
		}
		return this.lexemas;
	}

}
