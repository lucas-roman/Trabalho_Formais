/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package test.lexer;

import java.io.FileNotFoundException;

import main.fileio.FileToString;

import org.junit.Test;

public class TestReader {

	
	@Test
	public void testRead() throws FileNotFoundException {
		FileToString fil = new FileToString();
		String result = fil.readFile("test_file");
		System.out.println(result);
	}

}
