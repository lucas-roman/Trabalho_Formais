package main.lexer.automata.generalizednondeterministic;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;
import main.lexer.regularexpression.RegularExpression;

public class GeneralizedFiniteAutomataStructure {

	private GeneralizedFiniteAutomataState initialState;

	private Map<AutomataState, GeneralizedFiniteAutomataState> stateMap;

	private GeneralizedFiniteAutomataState acceptState;

	public void removeRandomState() {
		for (Entry<AutomataState, GeneralizedFiniteAutomataState> keyVal : stateMap
				.entrySet()) {
			int i = 1;
			if (!keyVal.getValue().accepts()
					&& keyVal.getValue() != initialState) {
				// Remove algorithm here...
				GeneralizedFiniteAutomataState stateToBeRemoved = stateMap.remove(keyVal.getKey());
				stateToBeRemoved.updateReferences();
				i++;
				return;
			}
			assert(i == 1);
		}
	}

	public GeneralizedFiniteAutomataStructure(Automata aut)
			throws InvalidStateException, MissingStateException,
			InitialStateMissingException, IllegalAutomataException {
		stateMap = new HashMap<>();
		Automata result;
		try {
			result = aut.convert();
		} catch (DeterministicException e) {
			result = aut;
		}
		AutomataBuilder builder = new AutomataBuilder(
				new AutomataStructureGraphFactory());
		builder.addState("0");
		result.decomposeAutomataIntoBuilder(builder);
		builder.addEmptyTransition("0", "1");
		builder.addState(builder.currentID() + "");
		int lstStatID = builder.currentID() - 1;
		for (AutomataState acceptState : aut.acceptStates()) {
			builder.addEmptyTransition(acceptState.stateID() + 1 + "",
					lstStatID + "");
		}
		builder.markAcceptState(lstStatID + "");
		Automata newAutomata = builder.build();
		for (AutomataState state : newAutomata.getStates()) {
			stateMap.put(state,
					new GeneralizedFiniteAutomataState(state.stateID()));
		}
		initialState = stateMap.get(newAutomata.initialState());
		for (AutomataState state : newAutomata.getStates()) {
			for (Entry<Character, Set<AutomataState>> trans : state
					.getTransitions()) {
				for (AutomataState transStateValue : trans.getValue()) {
					stateMap.get(state).addStateBy(
							RegularExpression.createRegularExpression(trans
									.getKey()), stateMap.get(transStateValue));
				}
				
			}
			for (AutomataState epslonState : state.epslonTransitions()) {
				stateMap.get(state).addStateBy(
						RegularExpression.createRegularExpression('\0'),
						stateMap.get(epslonState));
			}
		}
		for (AutomataState acceptState : newAutomata.acceptStates()) {
			stateMap.get(acceptState).markAsAccept();
			this.acceptState = stateMap.get(acceptState);
		}
	}
	
	
	private int size() {
		return stateMap.size();
	}
	
	
	public RegularExpression convertToRegularExpression() {
		while(size() > 2) {
			removeRandomState();
		}
		
		assert(stateMap.containsValue(initialState));
		assert(stateMap.containsValue(acceptState));
		return initialState.regularExpressionToState(acceptState);
	}

}
