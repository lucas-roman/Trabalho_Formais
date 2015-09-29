package test.lexer.grammar;

import junit.framework.Assert;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.exceptions.OverrideInitialStateException;
import main.lexer.grammar.RegularGrammar;
import main.lexer.grammar.RegularGrammarBuilder;
import main.lexer.grammar.exceptions.SameProductionException;
import main.lexer.grammar.exceptions.StartSymbolMissingException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGrammar {

	RegularGrammar grammar;

	@Before
	public void init() throws SameProductionException,
			StartSymbolMissingException {
		RegularGrammarBuilder builder = new RegularGrammarBuilder();
		builder.addTerminal('a');
		builder.addTerminal('b');
		builder.addNonTerminal("S");
		builder.addNonTerminal("A");
		builder.addNonTerminal("B");
		builder.addProduction(builder.getNonTerminalOf("S"),
				builder.getTerminalOf('a'));
		builder.addProduction(builder.getNonTerminalOf("S"),
				builder.getTerminalOf('a'), builder.getNonTerminalOf("A"));
		builder.addProduction(builder.getNonTerminalOf("S"),
				builder.getTerminalOf('b'));
		builder.addProduction(builder.getNonTerminalOf("S"),
				builder.getTerminalOf('b'), builder.getNonTerminalOf("B"));
		builder.addProduction(builder.getNonTerminalOf("A"),
				builder.getTerminalOf('a'), builder.getNonTerminalOf("A"));
		builder.addProduction(builder.getNonTerminalOf("A"),
				builder.getTerminalOf('b'), builder.getNonTerminalOf("A"));
		builder.addProduction(builder.getNonTerminalOf("A"),
				builder.getTerminalOf('a'));
		builder.addProduction(builder.getNonTerminalOf("B"),
				builder.getTerminalOf('b'), builder.getNonTerminalOf("B"));
		builder.addProduction(builder.getNonTerminalOf("B"),
				builder.getTerminalOf('b'));
		builder.addProduction(builder.getNonTerminalOf("B"),
				builder.getTerminalOf('a'), builder.getNonTerminalOf("B"));
		builder.markStartSymbol(builder.getNonTerminalOf("S"));
		grammar = builder.createGrammar();
	}

	@After
	public void tearDown() {
		grammar = null;
	}

	@Test
	public void testCreatedAutomata() throws DeterministicException,
			MissingStateException, OverrideInitialStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		Automata testAutomata = grammar.createAutomata().convert();
		Assert.assertTrue(testAutomata.accepts("a"));
		Assert.assertTrue(testAutomata.accepts("b"));
		Assert.assertTrue(testAutomata.accepts("abba"));
		Assert.assertTrue(testAutomata.accepts("baabbababbab"));
		Assert.assertTrue(testAutomata.accepts("aa"));
		Assert.assertTrue(testAutomata.accepts("bb"));
		Assert.assertFalse(testAutomata.accepts("ab"));
		Assert.assertFalse(testAutomata.accepts("ba"));
		Assert.assertFalse(testAutomata.accepts("abababaaab"));
		Assert.assertFalse(testAutomata.accepts("bababaaaaba"));
	}

}
