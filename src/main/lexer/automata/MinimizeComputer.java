package main.lexer.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.graph.AutomataState;

public class MinimizeComputer {

	private Automata automata;

	private Set<Set<AutomataState>> categories;

	public MinimizeComputer(AutomataSkeleton automataSkeleton) {
		try {
			automata = automataSkeleton.convert();
		} catch (DeterministicException e) {
			automata = automataSkeleton;
		}
		categories = new HashSet<>();
		addBaseCategories();
	}

	private void addBaseCategories() {
		Set<AutomataState> accept = new HashSet<AutomataState>();
		accept.addAll(automata.acceptStates());
		categories.add(accept);
		Set<AutomataState> notAccept = new HashSet<AutomataState>();
		notAccept.addAll(automata.notAcceptStates());
		categories.add(notAccept);
		categories.add(null);
	}

	public Automata compute() {
		for (Set<AutomataState> set : categories) {
			for (char character : automata.charForTransitions()) {
				Set<AutomataState> pointed = null;
				Set<AutomataState> temp = new HashSet<>();
				for (AutomataState state : set) {
					if(pointed == null) {
						pointed = getCategory(getNextStateByCharacter(state, character));
					}
					if(pointed != getCategory(getNextStateByCharacter(state, character))) {
						
					}
				}
			}
		}
		return automata;
	}

	private AutomataState getNextStateByCharacter(AutomataState state,
			char character) {
		if (!state.nextState(character).isEmpty())
			for (AutomataState magic : state.nextState(character)) {
				return magic;
			}
		return null;
	}

	private Set<AutomataState> getCategory(AutomataState state) {
		for (Set<AutomataState> set : categories) {
			if(set.contains(state)) {
				return set;
			}
		}
		return null;
	}
}
