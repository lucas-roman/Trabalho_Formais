/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

package main.lexer.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

class FileToString {
	private Scanner scan;
	private Set<String> stringsSet;

	public FileToString(){

	}

	protected Set<String> readFile (File file){
		File aux_file = file;
		try{
			this.scan = new Scanner(aux_file);

			while(scan.hasNextLine()){
				Scanner aux_scan = new Scanner(this.scan.nextLine());
				while(aux_scan.hasNext()){
					String newWord = aux_scan.next();
					this.stringsSet.add(newWord);
				}
				aux_scan.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found! Try again.");
			e.printStackTrace();
		}
		this.scan.close();
		return this.stringsSet;
	}
}
