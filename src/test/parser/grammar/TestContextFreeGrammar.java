package test.parser.grammar;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import main.parser.grammar.ContextFreeGrammar;
import main.parser.grammar.ContextFreeNonTerminal;
import main.parser.grammar.ContextFreeProduction;
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
		System.out.println(grammar);
		grammar.removeEmptyWord();
		System.out.println(grammar);
	}

}
