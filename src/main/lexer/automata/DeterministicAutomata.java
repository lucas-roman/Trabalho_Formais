/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata;

import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.structure.AutomataStructure;

/*
 * This class represents an deterministic automata.
 * PS: Prefer this over non deterministic automata for performance.
 */
public class DeterministicAutomata extends AutomataSkeleton{
	/* Creates a Deterministic Automata passing a structure to it.
	 * */
	public DeterministicAutomata(AutomataStructure structure) {
		super(structure);
	}

	@Override
	public Automata convert() throws DeterministicException {
		throw new DeterministicException();
	}

	@Override
	public String tagOfWord(String input) {
		return stateImpl.tagOf(input);
	}




}
