/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata.generalizednondeterministic;

public interface GeneralizedFiniteAutomataStateInterface {
	/*
	 * Checks if this state is an acceptable state.
	 */
	public boolean accepts();

	public void updateReferences();

	/*
	 * Returns the ID of the state.
	 */
	public int stateID();


}
