package main.lexer.automata;

import main.lexer.automata.exceptions.DeterministicException;

public interface Automata {

	boolean accepts(String string);

	Automata convert() throws DeterministicException;

}
