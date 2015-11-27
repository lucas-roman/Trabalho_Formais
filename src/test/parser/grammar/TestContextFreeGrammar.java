package test.parser.grammar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import junit.framework.Assert;
import main.parser.grammar.ContextFreeEmptyWord;
import main.parser.grammar.ContextFreeGrammar;
import main.parser.grammar.ContextFreeNonTerminal;
import main.parser.grammar.ContextFreeSymbol;
import main.parser.grammar.ContextFreeTerminalSymbol;

import org.junit.Test;

public class TestContextFreeGrammar {
	
	
	@Test
	public void testAddOperationAbsent() {
		ContextFreeGrammar grammar = new ContextFreeGrammar();
		int firstSize = grammar.terminalSet().size();
		grammar.createTerminalForString("id");
		int newSize = grammar.terminalSet().size();
		Assert.assertEquals(firstSize + 1, newSize);
	}
	
	@Test
	public void testAddExistingTerminal() {
		ContextFreeGrammar grammar = new ContextFreeGrammar();
		grammar.createTerminalForString("id");
		int firstSize = grammar.terminalSet().size();
		grammar.createTerminalForString("id");
		int newSize = grammar.terminalSet().size();
		Assert.assertEquals(firstSize , newSize);
	}
	
	@Test
	public void testAddEmptyWord() {
		ContextFreeGrammar grammar = new ContextFreeGrammar();
		int oldSize = grammar.terminalSet().size();
		grammar.createTerminalForString("");
		int newSize = grammar.terminalSet().size();
		Assert.assertEquals(oldSize , newSize);
	}
	
	@Test
	public void testEmptyWordTerminal() {
		ContextFreeGrammar grammar = new ContextFreeGrammar();
		boolean execute = false;
		for(ContextFreeTerminalSymbol symbol : grammar.terminalSet()) {
			execute = true;
			Assert.assertTrue(symbol.productionsForSymbol().isEmpty());
		}
		if(!execute) {
			Assert.fail("Shouldn't skip for loop.");
		}
	}
	
	@Test
	public void testRemoveEpslonTransition1() {
		ContextFreeGrammar grammar = new ContextFreeGrammar();
		ContextFreeTerminalSymbol id = grammar.createTerminalForString("id");
		ContextFreeTerminalSymbol k = grammar.createTerminalForString("k");
		ContextFreeTerminalSymbol ab = grammar.createTerminalForString("ab");
		ContextFreeNonTerminal s = grammar.createNonTerminalForString("S");
		ContextFreeNonTerminal a = grammar.createNonTerminalForString("A");
		ContextFreeNonTerminal b = grammar.createNonTerminalForString("B");
		List<ContextFreeSymbol> production = new ArrayList<>();
		production.add(id);
		production.add(a);
		production.add(b);
		s.addProduction(production);
		production = new ArrayList<>();
		production.add(a);
		s.addProduction(production);
		production = new ArrayList<>();
		production.add(id);
		s.addProduction(production);
		production = new ArrayList<>();
		production.add(grammar.createTerminalForString(""));
		a.addProduction(production);
		production = new ArrayList<>();
		production.add(k);
		a.addProduction(production);
		production = new ArrayList<>();
		production.add(k);
		production.add(a);
		a.addProduction(production);
		production = new ArrayList<>();
		production.add(ab);
		b.addProduction(production);
		Assert.assertEquals(3, s.productionsForSymbol().size());
		Assert.assertEquals(3, a.productionsForSymbol().size());
		Assert.assertEquals(1, b.productionsForSymbol().size());
		Assert.assertEquals(3, grammar.nonTerminalSet().size());
		grammar.removeEmptyWord();
		Assert.assertEquals(4, grammar.nonTerminalSet().size());
		Assert.assertEquals(4, s.productionsForSymbol().size());
		Assert.assertEquals(2, a.productionsForSymbol().size());
		Assert.assertEquals(1, b.productionsForSymbol().size());
	}
	
	@Test
	public void testRemoveEpslonTransition2() {
		ContextFreeGrammar grammar = new ContextFreeGrammar();
		ContextFreeTerminalSymbol id = grammar.createTerminalForString("id");
		ContextFreeTerminalSymbol k = grammar.createTerminalForString("k");
		ContextFreeTerminalSymbol ab = grammar.createTerminalForString("ab");
		ContextFreeNonTerminal s = grammar.createNonTerminalForString("S");
		ContextFreeNonTerminal a = grammar.createNonTerminalForString("A");
		ContextFreeNonTerminal b = grammar.createNonTerminalForString("B");
		List<ContextFreeSymbol> production = new ArrayList<>();
		production.add(id);
		production.add(a);
		production.add(b);
		s.addProduction(production);
		production = new ArrayList<>();
		production.add(a);
		production.add(b);
		s.addProduction(production);
		production = new ArrayList<>();
		production.add(id);
		s.addProduction(production);
		production = new ArrayList<>();
		production.add(grammar.createTerminalForString(""));
		a.addProduction(production);
		production = new ArrayList<>();
		production.add(k);
		a.addProduction(production);
		production = new ArrayList<>();
		production.add(k);
		production.add(a);
		a.addProduction(production);
		production = new ArrayList<>();
		production.add(ab);
		b.addProduction(production);
		grammar.removeEmptyWord();
	}
	
	@Test
	public void testFirst() {
		ContextFreeGrammar grammar = new ContextFreeGrammar();
		ContextFreeNonTerminal e = grammar.createNonTerminalForString("E");
		ContextFreeNonTerminal e1 = grammar.createNonTerminalForString("E1");
		ContextFreeNonTerminal t = grammar.createNonTerminalForString("T");
		ContextFreeNonTerminal t1 = grammar.createNonTerminalForString("T1");
		ContextFreeNonTerminal f = grammar.createNonTerminalForString("F");
		ContextFreeTerminalSymbol plus = grammar.createTerminalForString("+");
		ContextFreeTerminalSymbol times = grammar.createTerminalForString("*");
		ContextFreeTerminalSymbol leftPar = grammar.createTerminalForString("(");
		ContextFreeTerminalSymbol rightPar = grammar.createTerminalForString(")");
		ContextFreeTerminalSymbol id = grammar.createTerminalForString("id");
		List<ContextFreeSymbol> productions = new ArrayList<>();
		productions.add(t);
		productions.add(e1);
		e.addProduction(productions);
		productions = new ArrayList<>();
		productions.add(plus);
		productions.add(t);
		productions.add(e1);
		e1.addProduction(productions);
		productions = new ArrayList<>();
		productions.add(ContextFreeEmptyWord.getInstance());
		e1.addProduction(productions);
		productions = new ArrayList<>();
		productions.add(f);
		productions.add(t1);
		t.addProduction(productions);
		productions = new ArrayList<>();
		productions.add(times);
		productions.add(f);
		productions.add(t1);
		t1.addProduction(productions);
		productions = new ArrayList<>();
		productions.add(ContextFreeEmptyWord.getInstance());
		t1.addProduction(productions);
		productions = new ArrayList<>();
		productions.add(leftPar);
		productions.add(e);
		productions.add(rightPar);
		f.addProduction(productions);
		productions = new ArrayList<>();
		productions.add(id);
		f.addProduction(productions);
		Assert.assertTrue(t.first().contains(id));
		Assert.assertTrue(t.first().contains(leftPar));
		Assert.assertTrue(e.first().contains(id));
		Assert.assertTrue(e.first().contains(leftPar));
		Assert.assertTrue(f.first().contains(id));
		Assert.assertTrue(f.first().contains(leftPar));
		Assert.assertTrue(e1.first().contains(plus));
		Assert.assertTrue(e1.first().contains(ContextFreeEmptyWord.getInstance()));
		Assert.assertTrue(t1.first().contains(ContextFreeEmptyWord.getInstance()));
		Assert.assertTrue(t1.first().contains(times));
		Assert.assertTrue(e.first().size()==2);
		Assert.assertTrue(e1.first().size()==2);
		Assert.assertTrue(t.first().size()==2);
		Assert.assertTrue(t1.first().size()==2);
		Assert.assertTrue(f.first().size()==2);
		grammar.follow();
	}

}
