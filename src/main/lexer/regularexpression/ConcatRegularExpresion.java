package main.lexer.regularexpression;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.factory.AutomataBuilder;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.automata.structure.graph.AutomataStructureGraphFactory;

class ConcatRegularExpresion extends RegularExpression {

	private RegularExpression leftChild;

	private RegularExpression rightChild;

	public ConcatRegularExpresion(RegularExpression leftChild,
			RegularExpression rightChild) {
		this.leftChild = leftChild;
		this.rightChild = rightChild;
	}

	public String toString() {
		return "" + leftChild + rightChild;
	}


	public Automata createAutomata() throws InvalidStateException,
			MissingStateException, InitialStateMissingException,
			IllegalAutomataException {
		Automata leftChildAutomata = leftChild.createAutomata();
		Automata rightChildAutomata = rightChild.createAutomata();
		AutomataBuilder build = getBuilderValueOf(leftChildAutomata,
				rightChildAutomata);
		int aut1Size = leftChildAutomata.size();
		for (AutomataState acceptState : leftChildAutomata.acceptStates()) {
			build.addEmptyTransition(acceptState.stateID() + "",
					rightChildAutomata.initialState().stateID() + aut1Size + "");
		}
		for (AutomataState acceptState : rightChildAutomata.acceptStates()) {
			build.markAcceptState(acceptState.stateID() + aut1Size + "");
		}
		return build.build();
	}
	
	//Returns a builder without the initial state nor the final state marked for the given automata.
		AutomataBuilder getBuilderValueOf(Automata aut1, Automata aut2) throws InvalidStateException {
			AutomataBuilder builder = new AutomataBuilder(new AutomataStructureGraphFactory());
			addStates(builder, aut1);
			//Here, builder's current id is one past last state value. So, it is safe to add it with the state value from other automata.
			int lastID = builder.currentID();
			//We need to pass the lastID so we don't have conflicts regarding state names.
			addStatesSecondAutomata(builder, aut2, lastID);
			addTransitions(builder,aut1 );
			addTransitionsSecondAutomata(builder, aut2, lastID);
			return builder;
		}
		
		private void addTransitionsSecondAutomata(AutomataBuilder builder,
				Automata aut2, int lastID) throws InvalidStateException {
			for(AutomataState state : aut2.getStates()) {
				for(Character c : state.getTransitions()) {
					for(AutomataState other : state.nextState(c)) {
						try {
							builder.addTransition(state.stateID() + lastID + "", other.stateID() + lastID + "", c);
						} catch (MissingStateException e) {
							//All states already added. Safe to ignore.
						}
					}
				}
				for(AutomataState epslonReachable : state.epslonClosure()) {
					//Ignore empty transitions to same state
					if(epslonReachable != state) {
						try {
							builder.addEmptyTransition(state.stateID() + lastID + "", epslonReachable.stateID() + lastID + "");
						} catch (MissingStateException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		}

		private void addStates(AutomataBuilder build, Automata aut) throws InvalidStateException {
			build.addState(aut.initialState().stateID() + "");
			for(AutomataState state : aut.getStates()) {
				build.addState(state.toString());
			}
		}
		
		private void addStatesSecondAutomata(AutomataBuilder build, Automata aut, int lastID) throws InvalidStateException {
			for(AutomataState state : aut.getStates()) {
				build.addState(state.stateID() + lastID + "");
			}
		}
		
		private void addTransitions(AutomataBuilder builder, Automata aut) throws InvalidStateException {
			for(AutomataState state : aut.getStates()) {
				for(Character c : state.getTransitions()) {
					for(AutomataState other : state.nextState(c)) {
						try {
							builder.addTransition(state.toString(), other.toString(), c);
						} catch (MissingStateException e) {
							//All states already added. Safe to ignore.
						}
					}
				}
				for(AutomataState epslonReachable : state.epslonClosure()) {
					//Ignore empty transitions to same state
					if(epslonReachable != state) {
						try {
							builder.addEmptyTransition(state.toString(), epslonReachable.toString());
						} catch (MissingStateException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}


}
