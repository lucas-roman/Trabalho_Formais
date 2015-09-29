package main.lexer.automata.structure;

import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.NonDeterministicException;
import main.lexer.automata.structure.graph.AutomataState;

public interface AutomataStructure {
	
	public void addTransition(String from, String to, char trans) throws InvalidStateException, MissingStateException, NonDeterministicException;

	public AutomataState createState(String stateName) throws InvalidStateException;

	public boolean validateAutomata() throws InitialStateMissingException;
	
	public void addEpslonTransition(String from, String to) throws InvalidStateException;
	
	public void markAcceptState(String string) throws InvalidStateException, InitialStateMissingException;
	
	public void markInitialState(String s) throws MissingStateException;

	public boolean empty();

	public boolean check(String string);

	public AutomataState automataInitialState();

}
