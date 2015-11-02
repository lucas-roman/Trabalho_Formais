package main.lexer.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.graph.AutomataState;

public class MinimizeComputer {

	private Automata automata;

	private Map<AutomataState, Set<AutomataState>> categories;
	
	public MinimizeComputer(AutomataSkeleton automataSkeleton) {
		automata = automataSkeleton;
		categories = new HashMap<>();
		addBaseCategories();
	}

	private void addBaseCategories() {
		Set<AutomataState> accept = new HashSet<AutomataState>();
		accept.addAll(automata.acceptStates());
		for(AutomataState acceptState : automata.acceptStates()) {
			categories.put(acceptState, accept);
		}
		Set<AutomataState> notAccept = new HashSet<AutomataState>();
		notAccept.addAll(automata.notAcceptStates());
		for(AutomataState state : automata.notAcceptStates()) {
			categories.put(state, notAccept);
		}
	}

	public Automata compute() throws NonDeterministicException {
		for(Set<AutomataState> set : categories.values()) {
			for(char character : automata.charForTransitions()) {
				Set<AutomataState> pointed = null;
				for(AutomataState state : set) {
					//Should be fixed to deal with dead transitions.
					AutomataState nextStateByCharacter=null;
					if(state.nextState(character).size() > 1) {
						throw new NonDeterministicException();
					}
					for(AutomataState magic : state.nextState(character)) {
						nextStateByCharacter = magic;
					}
					if(pointed == null) {
						pointed = categories.get(nextStateByCharacter);
					}
					if(categories.get(nextStateByCharacter) != pointed) {
						//We should create a new category after processing all the states.
					}
				}
			}
		}
		return automata;
	}

}
