package main.lexer.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.lexer.automata.exceptions.DeterministicException;
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
		calculateCategories();
		return automata;
	}
	
	private void calculateCategories() {
		for (char character : automata.charForTransitions()) {
			Set<Set<AutomataState>> newCategories = new HashSet<>();
			for (Set<AutomataState> set : categories) {
				Set<AutomataState> pointed = null;
				Map<Set<AutomataState>, Set<AutomataState>> temp = new HashMap<>();
				for (AutomataState state : set) {
					if (pointed == null) {
						pointed = getCategoryOfNextState(state, character);
					}
					if (pointed != getCategoryOfNextState(state, character)) {
						if (!temp.containsKey(getCategoryOfNextState(state,
								character))) {
							temp.put(getCategoryOfNextState(state, character),
									new HashSet<AutomataState>());
						}
						temp.get(getCategoryOfNextState(state, character)).add(
								state);
					}
				}
				newCategories.addAll(temp.values());
			}
			if(newCategories.size() < 1) {
				break;
			}
			removeFromOldCategories(newCategories);
			categories.addAll(newCategories);
		}
	}
	
	private void removeFromOldCategories(Set<Set<AutomataState>> newCategories) {
		for(Set<AutomataState> set : newCategories) {
			for(AutomataState state : set) {
				getCategory(state).remove(state);
			}
		}
	}

	private AutomataState getNextStateByCharacter(AutomataState state,
			char character) {
		if (!state.nextState(character).isEmpty())
			for (AutomataState magic : state.nextState(character)) {
				return magic;
			}
		return null;
	}

	private Set<AutomataState> getCategoryOfNextState(AutomataState state,
			char character) {
		return getCategory(getNextStateByCharacter(state, character));
	}

	private Set<AutomataState> getCategory(AutomataState state,
			Set<Set<AutomataState>> cat) {
		for (Set<AutomataState> set : cat) {
			if (set.contains(state)) {
				return set;
			}
		}
		return null;
	}

	private Set<AutomataState> getCategory(AutomataState state) {
		return getCategory(state, categories);
	}
}
