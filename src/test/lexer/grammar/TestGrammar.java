package test.lexer.grammar;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.grammar.Grammar;
import main.lexer.grammar.GrammarSymbol;
import main.lexer.grammar.NonTerminal;
import main.lexer.grammar.Terminal;
import main.lexer.grammar.builder.GrammarBuilder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestGrammar {

	Grammar grammar;

	@Before
	public void init() {
		GrammarBuilder builder = new GrammarBuilder();
		Terminal tA = builder.addTerminal('a');
		Terminal tB = builder.addTerminal('b');
		NonTerminal ntS = builder.addNonTerminal('S');
		NonTerminal ntA =  builder.addNonTerminal('A');
		NonTerminal ntB =builder.addNonTerminal('B');
		List<GrammarSymbol> prodS0 = new ArrayList<GrammarSymbol>();
		prodS0.add(tA);
		builder.addProduction(ntS, prodS0);
		List<GrammarSymbol> prodS1 = new ArrayList<GrammarSymbol>();
		prodS1.add(tA);
		prodS1.add(ntA);
		builder.addProduction(ntS, prodS1);
		List<GrammarSymbol> prodS2 = new ArrayList<GrammarSymbol>();
		prodS2.add(tB);
		builder.addProduction(ntS, prodS2);
		List<GrammarSymbol> prodS3 = new ArrayList<GrammarSymbol>();
		prodS3.add(tB);
		prodS3.add(ntB);
		builder.addProduction(ntS, prodS3);
		List<GrammarSymbol> prodA0 = new ArrayList<GrammarSymbol>();
		prodA0.add(tA);
		prodA0.add(ntA);
		builder.addProduction(ntA, prodA0);
		List<GrammarSymbol> prodA1 = new ArrayList<GrammarSymbol>();
		prodA1.add(tA);
		builder.addProduction(ntA, prodA1);
		List<GrammarSymbol> prodB0 = new ArrayList<GrammarSymbol>();
		prodB0.add(tB);
		prodB0.add(ntB);
		builder.addProduction(ntA, prodB0);
		List<GrammarSymbol> prodB1 = new ArrayList<GrammarSymbol>();
		prodB1.add(tB);
		builder.addProduction(ntA, prodB1);
		grammar = builder.createGrammar();
	}

	@After
	public void tearDown() {
		grammar = null;
	}

	@Test
	public void testCreatedAutomata() throws DeterministicException {
			Automata testAutomata = grammar.createAutomata();
			Assert.assertTrue(testAutomata.accepts(""));
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
			Automata deterministic = testAutomata.convert();
			Grammar newGrammar = new Grammar(deterministic);
			testAutomata = newGrammar.createAutomata();
			Assert.assertTrue(testAutomata.accepts(""));
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
