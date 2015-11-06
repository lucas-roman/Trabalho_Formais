/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.lexer.Lexema;
import main.lexer.model.commandline.exceptions.IllegalStructureOfText;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

/*
 * This class is responsible for read a language file and then creates the lexemas.
 */
public class LangReader {

	private Scanner scan;
	private List<Lexema> lexemas;

	public LangReader(){
		lexemas = new ArrayList<>();
	}

	public List<Lexema> readFile (File file) throws IllegalStructureOfText, IllegalRegularExpressionException{
		File aux_file = file;
		try{
			this.scan = new Scanner(aux_file);
			String aux = scan.nextLine();
			aux.trim();
			while(scan.hasNextLine()){
				while(aux.length() == 0){
					if(!scan.hasNextLine()) {
						break;
					}
					aux = this.scan.nextLine();
					aux = aux.trim();
				}
				String tag = aux;
				if(!scan.hasNextLine()) {
					throw new IllegalStructureOfText();
				}
				aux = this.scan.nextLine();
				aux = aux.trim();
				String re = aux;
				lexemas.add(new Lexema(tag, re));
				if(!scan.hasNextLine()) {
					break;
				}
				aux = scan.nextLine();
				aux = aux.trim();
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found! Try again.");
			e.printStackTrace();
		}
		this.scan.close();
		return this.lexemas;
	}

}
