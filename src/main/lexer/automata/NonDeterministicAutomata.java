/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata;

import main.lexer.automata.algorithms.ConvertNonDeterministicDeterministic;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.AutomataStructure;

/*
 * This class represents an non deterministic automata.
 * This is what you get when you convert a grammar, for example.
 * Should always become a Deterministic Automata.
 */
public class NonDeterministicAutomata extends AutomataSkeleton {




	/* Creates a Non Deterministic Automata from the given structure.
	 * */
	public NonDeterministicAutomata(AutomataStructure struct)  {
		super(struct);
	}

	/* Returns a deterministic automata which accepts the same language as this non deterministic automata.
	 * If it fails, it returns itself (NonDeterministic).
	 * */
	@Override
	public Automata convert() throws DeterministicException {
		try {
			return createConverter().convert();
		} catch (InitialStateMissingException | MissingStateException
				| IllegalAutomataException e) {
			e.printStackTrace();
			return this;
		}
	}

	private ConvertNonDeterministicDeterministic createConverter() {
		if(tagOrder.isEmpty())
			return new ConvertNonDeterministicDeterministic(this);
		return new ConvertNonDeterministicDeterministic(this, tagOrder);
	}


	@Override
	public String tagOfWord(String input) throws NonDeterministicException {
		throw new NonDeterministicException();
	}



}
