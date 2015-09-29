package test.lexer.grammar.builder;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import main.lexer.grammar.GrammarBuilder;
import main.lexer.grammar.GrammarSymbol;
import main.lexer.grammar.NonTerminal;
import main.lexer.grammar.Terminal;
import main.lexer.grammar.exceptions.SameProductionException;

import org.junit.Test;

public class TestGrammarBuilder {
	
	
	@Test(expected=SameProductionException.class)
	public void testSameProduction() throws SameProductionException {
		GrammarBuilder test = new GrammarBuilder();
		NonTerminal nt = test.addNonTerminal("S");
		Terminal t = test.addTerminal("a");
		List<GrammarSymbol> toAdd = new ArrayList<GrammarSymbol>();
		toAdd.add(t);
		test.addProduction(nt, toAdd);
		List<GrammarSymbol> toAdd2 = new ArrayList<GrammarSymbol>();
		toAdd2.add(t);
		test.addProduction(nt, toAdd2);
	}
	
	@Test
	public void testDifferentProduction() {
		GrammarBuilder test = new GrammarBuilder();
		NonTerminal nt = test.addNonTerminal("S");
		Terminal t = test.addTerminal("a");
		Terminal t2 = test.addTerminal("b");
		List<GrammarSymbol> toAdd = new ArrayList<GrammarSymbol>();
		toAdd.add(t);
		try {
			test.addProduction(nt, toAdd);
			List<GrammarSymbol> toAdd2 = new ArrayList<GrammarSymbol>();
			toAdd2.add(t2);
			test.addProduction(nt, toAdd2);
		} catch (SameProductionException e) {
			Assert.fail();
		}
	}

}
