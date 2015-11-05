/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata.generalizednondeterministic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.regularexpression.RegularExpression;

public class GeneralizedFiniteAutomataState implements GeneralizedFiniteAutomataStateInterface {
	/*
	 * Attributes of this class
	 */
	private RegularExpression selfTransition;
	Map<GeneralizedFiniteAutomataState, RegularExpression> nextStates = new HashMap<>();
	Set<GeneralizedFiniteAutomataState> predecessors = new HashSet<>();
	private int stateID;
	private boolean accepts = false;

	public GeneralizedFiniteAutomataState(int stateID) {
		this.stateID = stateID;
	}

	/*
	 * Checks if this state is an acceptable state.
	 */
	@Override
	public boolean accepts() {
		return accepts;
	}

	/*
	 * Returns the ID of the state.
	 */
	@Override
	public int stateID() {
		return stateID;
	}

	@Override
	public void updateReferences() {
		for (GeneralizedFiniteAutomataState pred : predecessors) {
			RegularExpression predToThis = pred.nextStates.get(this);
			for (Entry<GeneralizedFiniteAutomataState, RegularExpression> keyVal : nextStates.entrySet()) {
				RegularExpression nextStateReg = keyVal.getValue();
				GeneralizedFiniteAutomataState nextState = keyVal.getKey();
				RegularExpression toAdd;
				if(selfTransition == null) {
					toAdd = predToThis.concatenate(
							nextStateReg);
				}
				else {
					toAdd = predToThis.concatenate(
							selfTransition.kleene()).concatenate(nextStateReg);
				}
				if (nextState == pred) {
					pred.selfTransition = pred.selfTransition
							.alternation(toAdd);
				} else {
					if (pred.nextStates.containsKey(nextState)) {
						RegularExpression re = pred.nextStates
								.remove(nextState);
						re = re.alternation(toAdd);
						pred.addStateBy(re, nextState);
					} else {
						pred.addStateBy(toAdd, nextState);
					}
				}
			}
		}
		for (GeneralizedFiniteAutomataState pred : predecessors) {
			pred.nextStates.remove(this);
		}
		Set<GeneralizedFiniteAutomataState> iterSet = new  HashSet<GeneralizedFiniteAutomataState>(nextStates.keySet());
		for (GeneralizedFiniteAutomataState next : iterSet) {
			next.predecessors.remove(this);
		}


	}

	public void addStateBy(RegularExpression re, GeneralizedFiniteAutomataState other) {
		if (other != this) {
			if(nextStates.containsKey(other)) {
				RegularExpression toRem = nextStates.remove(other);
				RegularExpression newRE = toRem.alternation(re);
				nextStates.put(other, newRE);
			}
			else {
				nextStates.put(other, re);
				other.predecessors.add(this);
		    }
		}
		else {
			selfTransition = re;
		}
	}

	public void markAsAccept() {
		accepts = true;
	}


	public RegularExpression regularExpressionToState(
			GeneralizedFiniteAutomataState acceptState) {
		return nextStates.get(acceptState);
	}

}
