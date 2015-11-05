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

public class FileToString {
	private Scanner scan;
	private List<String> returnList = new ArrayList<>();

	public List<String> readFile (File file){
		File aux_file = file;
		try{
			this.scan = new Scanner(aux_file);

			while(scan.hasNextLine()){
				String[] thisLine = this.scan.nextLine().split(" ");
				for(String newWord : thisLine) {
					newWord = newWord.trim();
					if(newWord.length() > 0)
						this.returnList.add(newWord);
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found! Try again.");
			e.printStackTrace();
		}
		this.scan.close();
		return this.returnList;
	}
}
