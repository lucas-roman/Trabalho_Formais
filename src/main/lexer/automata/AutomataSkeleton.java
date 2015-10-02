package main.lexer.automata;

import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.graph.AutomataState;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

/*
 * This class represents part of the structure of an automata.
 */
public abstract class AutomataSkeleton implements Automata {


	protected AutomataStructure stateImpl;

	public AutomataSkeleton(AutomataStructure stateManager) {
		stateImpl = stateManager;
	}

	@Override
	public AutomataState initialState() {
		return stateImpl.automataInitialState();
	}

	@Override
	public boolean accepts(String string) {
		return stateImpl.check(string);
	}

	@Override
	public Set<AutomataState> getStates() {
		return stateImpl.states();
	}

	@Override
	public Set<AutomataState> acceptStates() {
		return stateImpl.acceptStates();
	}

	@Override
	public int size() {
		return getStates().size();
	}

	@Override
	public String toString() {
		String result ="States : ";
		for(AutomataState state : getStates()) {
			result += "\n";
			result += "ID -> ";
			result += state + "\n";
			result += "Transitions : ";
			result += "\n";
			for(Entry<Character, Set<AutomataState>> trans : state.getTransitions()) {
				for(AutomataState transState : trans.getValue()) {
					result += trans.getKey() + " -> " + transState + "\n";
				}
			}
			result += "\n";
			result += "Epslon : ";
			result += "\n";
			for(AutomataState stateByEpslon : state.epslonClosure()) {
				if(stateByEpslon != state) {
					result += stateByEpslon + "\n";
				}
			}
		}
		result += "\n";
		result += "Accept state : ";
		result += "\n";
		for(AutomataState state : acceptStates()) {
			result += state + "\n";
		}
		return result;

	}

}
