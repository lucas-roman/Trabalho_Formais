/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */
package main.lexer.model.commandline;

import java.util.Scanner;

import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
import main.lexer.grammar.RegularGrammarBuilder;

public class LangReader {

	private Scanner scan;
	private AutomataBuilder automataBuilder;
	private RegularGrammarBuilder regularBuilder;

	public LangReader(){
		this.automataBuilder = new AutomataBuilder(new AutomataStructureGraphFactory());
		this.regularBuilder = new RegularGrammarBuilder();
	}

}
