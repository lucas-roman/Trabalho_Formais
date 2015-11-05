/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer.automata;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.AutomataStructure;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

/*
 * This class represents part of the structure of an automata.
 */
public abstract class AutomataSkeleton implements Automata {

	protected AutomataStructure stateImpl;

	protected List<String> tagOrder = new ArrayList<>();

	private Set<Character> charTrans;

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
	public void addTagOrder(List<String> order) {
		tagOrder = order;
		Set<String> stateTags = new HashSet<>();
		for(AutomataState state : getStates()) {
			stateTags.add(state.getTag());
		}
		tagOrder.retainAll(stateTags);
	}

	@Override
	public int size() {
		return getStates().size();
	}

	@Override
	public Automata minimize() throws InitialStateMissingException, IllegalAutomataException  {
		return new MinimizeComputer(this).compute();
	}

	@Override
	public String toString() {
		String result = "States : ";
		for (AutomataState state : getStates()) {
			result += "\n";
			result += "ID -> ";
			result += state + "\n";
			result += "Transitions : ";
			result += "\n";
			for (Entry<Character, Set<AutomataState>> trans : state
					.getTransitions()) {
				for (AutomataState transState : trans.getValue()) {
					result += trans.getKey() + " -> " + transState + "\n";
				}
			}
			result += "\n";
			result += "Epslon : ";
			result += "\n";
			for (AutomataState stateByEpslon : state.epslonTransitions()) {
				if (stateByEpslon != state) {
					result += stateByEpslon + "\n";
				}
			}
		}
		result += "\n";
		result += "Accept state : ";
		result += "\n";
		for (AutomataState state : acceptStates()) {
			result += state + "\n";
		}
		return result;

	}

	@Override
	public Set<Character> charForTransitions() {
		if (charTrans == null) {
			charTrans = new HashSet<>();
			for (AutomataState state : getStates()) {
				for (Entry<Character, Set<AutomataState>> trans : state
						.getTransitions()) {
					charTrans.add(trans.getKey());
				}
			}
		}
		return charTrans;
	}

	@Override
	public Automata union(Automata other) throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("0");
		decomposeAutomataIntoBuilder(builder);
		other.decomposeAutomataIntoBuilder(builder);
		int size1 = size();
		for (AutomataState acceptState : acceptStates()) {
			builder.markAcceptState(1 + acceptState.stateID() + "");
			builder.addTagToState(1 + acceptState.stateID() + "", acceptState.getTag());
		}
		for (AutomataState acceptState : other.acceptStates()) {
			builder.markAcceptState(size1 + acceptState.stateID() + 1 + "");
			builder.addTagToState(size1 + acceptState.stateID() + 1 + "", acceptState.getTag());
		}
		builder.addEmptyTransition("0", "1");
		builder.addEmptyTransition("0", size1 + 1 + "");
		return builder.build();
	}

	@Override
	public Automata concatenate(Automata other) throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		AutomataBuilder build = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		decomposeAutomataIntoBuilder(build);
		other.decomposeAutomataIntoBuilder(build);
		int aut1Size = size();
		for (AutomataState acceptState : acceptStates()) {
			build.addEmptyTransition(acceptState.stateID() + "", other
					.initialState().stateID() + aut1Size + "");
			build.addTagToState(acceptState.stateID() + "", acceptState.getTag());
		}
		for (AutomataState acceptState : other.acceptStates()) {
			build.markAcceptState(acceptState.stateID() + aut1Size + "");
			build.addTagToState(acceptState.stateID() + aut1Size + "", acceptState.getTag());
		}
		return build.build();
	}

	@Override
	public Automata kleene() throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("0");
		decomposeAutomataIntoBuilder(builder);
		builder.markAcceptState("0");
		builder.addEmptyTransition("0", "1");
		for (AutomataState acceptState : acceptStates()) {
			builder.markAcceptState(acceptState.stateID() + 1 + "");
			builder.addEmptyTransition(acceptState.stateID() + 1 + "", "1");
		}
		return builder.build();
	}

	@Override
	public Set<AutomataState> notAcceptStates() {
		Set<AutomataState> returnSet = new HashSet<>();
		for (AutomataState state : getStates()) {
			if (!acceptStates().contains(state)) {
				returnSet.add(state);
			}
		}
		return returnSet;
	}

	@Override
	public void decomposeAutomataIntoBuilder(AutomataBuilder builder)
			throws InvalidStateException {
		int index = builder.currentID();
		addStates(builder, index);
		addTransitions(builder, index);
	}

	private void addTransitions(AutomataBuilder builder, int lastID)
			throws InvalidStateException {
		for (AutomataState state : getStates()) {
			for (Entry<Character, Set<AutomataState>> keyPair : state
					.getTransitions()) {
				for (AutomataState other : keyPair.getValue()) {
					try {
						builder.addTransition(state.stateID() + lastID + "",
								other.stateID() + lastID + "", keyPair.getKey());
					} catch (MissingStateException e) {
						// All states already added. Safe to ignore.
					}
				}
			}
			for (AutomataState epslonReachable : state.epslonClosure()) {
				// Ignore empty transitions to same state
				if (epslonReachable != state) {
					try {
						builder.addEmptyTransition(state.stateID() + lastID
								+ "", epslonReachable.stateID() + lastID + "");
					} catch (MissingStateException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void addStates(AutomataBuilder build, int lastID)
			throws InvalidStateException {
		build.addState(initialState().stateID() + lastID + "");
		for (AutomataState state : getStates()) {
			build.addState(state.stateID() + lastID + "");
		}
	}

}
