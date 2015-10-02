
package main.model.commandline.fileutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright � 2015
 */

public class Reader {

	private Scanner scan;

	public Reader(){
	}

	public void readFile(File file){
		try {
			File aux_file = file;
			this.scan = new Scanner(aux_file);
			String command = scan.nextLine();
			command.toLowerCase();
			command.trim();
			this.scan.close();
			switch(command){
			case "afndtoafd":
				readNFA(aux_file);
				break;
			case "afndepsolon":
				readNFAEpsolon(aux_file);
				break;
			case "grtoaf":
				readRGtoFA(aux_file);
				break;
			case "aftogr":
				readFAtoRG(aux_file);
				break;
			case "ertoaf":
				readREtoFA(aux_file);
				break;
			default:
				readFAtoRE(aux_file);
				break;
			}

		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found!");
			e.printStackTrace();
		}
	}

	private void readNFA(File aux_file) {
		// TODO Auto-generated method stub

	}

	private void readFAtoRE(File aux_file) {
		// TODO Auto-generated method stub

	}

	private void readREtoFA(File aux_file) {
		// TODO Auto-generated method stub

	}

	private void readFAtoRG(File aux_file) {
		// TODO Auto-generated method stub

	}

	private void readRGtoFA(File aux_file) {
		// TODO Auto-generated method stub

	}

	private void readNFAEpsolon(File aux_file) {
		// TODO Auto-generated method stub

	}
}
