/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.AutomataSkeleton;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

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
		categories.add(new HashSet<AutomataState>());
	}

	public Automata compute() throws InitialStateMissingException,
			IllegalAutomataException {
		try {
			calculateCategories();
			removeEmptyCategory();
			Map<Set<AutomataState>, String> categoryMap = categoryMap();
			AutomataBuilder builder = createBuilder(categoryMap);
			addTransitions(builder, categoryMap);
			return builder.build();
		} catch (InvalidStateException e) {
			return null;
		}
	}

	private AutomataBuilder createBuilder(
			Map<Set<AutomataState>, String> categoryMap)
			throws InvalidStateException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("q0");
		for (String val : categoryMap.values()) {
			if (!val.equals("q0")) {
				builder.addState(val);
			}
		}
		return builder;
	}

	private void addTransitions(AutomataBuilder builder,
			Map<Set<AutomataState>, String> categoryMap) {
		try {
			for (Entry<Set<AutomataState>, String> entry : categoryMap
					.entrySet()) {
				AutomataState state = null;
				for (AutomataState magic : entry.getKey()) {
					state = magic;
					break;
				}
				if (state.accepts()) {
					builder.markAcceptState(entry.getValue());
				}
				for (Entry<Character, Set<AutomataState>> transition : state
						.getTransitions()) {
					if (transition.getValue().size() > 0) {
						AutomataState transitionedTo = null;
						for (AutomataState magic : transition.getValue()) {
							transitionedTo = magic;
						}
						builder.addTransition(entry.getValue(),
								categoryMap.get(getCategory(transitionedTo)),
								transition.getKey());
					}
				}
			}
		} catch (InvalidStateException | MissingStateException e) {
			// Should not get here...
			e.printStackTrace();
		}
	}

	private Map<Set<AutomataState>, String> categoryMap() {
		Map<Set<AutomataState>, String> categoryMap = new HashMap<>();
		int id = 0;
		categoryMap.put(getCategory(automata.initialState()), "q" + id);
		id++;
		for (Set<AutomataState> category : categories) {
			if (!categoryMap.containsKey(category)) {
				categoryMap.put(category, "q" + id);
				id++;
			}
		}
		return categoryMap;
	}

	private void removeEmptyCategory() {
		categories.remove(deadStateCategory());
	}

	private void calculateCategories() {
		while (!lastIterationForCategory())
			;
	}

	private boolean lastIterationForCategory() {
		boolean lastIteration = true;
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
			if (!(newCategories.size() < 1)) {
				lastIteration = false;
			}
			removeFromOldCategories(newCategories);
			categories.addAll(newCategories);
		}
		return lastIteration;
	}

	private void removeFromOldCategories(Set<Set<AutomataState>> newCategories) {
		for (Set<AutomataState> set : newCategories) {
			for (AutomataState state : set) {
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
		AutomataState nextState = getNextStateByCharacter(state, character);
		if(nextState != null) {
			return getCategory(nextState);
		}
		return deadStateCategory();
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

	private Set<AutomataState> deadStateCategory() {
		Set<AutomataState> empty = new HashSet<>();
		for (Set<AutomataState> emptyChecker : categories) {
			if (emptyChecker.isEmpty()) {
				empty = emptyChecker;
			}
		}
		return empty;
	}
}
