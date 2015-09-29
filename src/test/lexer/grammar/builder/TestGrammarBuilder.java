package test.lexer.grammar.builder;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import main.lexer.grammar.RegularGrammarBuilder;
import main.lexer.grammar.GrammarSymbol;
import main.lexer.grammar.NonTerminal;
import main.lexer.grammar.Terminal;
import main.lexer.grammar.exceptions.SameProductionException;

import org.junit.Test;

public class TestGrammarBuilder {
	
	
	@Test(expected=SameProductionException.class)
	public void testSameProduction() throws SameProductionException {
		RegularGrammarBuilder test = new RegularGrammarBuilder();
		NonTerminal nt = test.addNonTerminal("S");
		Terminal t = test.addTerminal('a');
		test.addProduction(nt, t);
		test.addProduction(nt, t);
	}
	
	@Test
	public void testDifferentProduction() {
		RegularGrammarBuilder test = new RegularGrammarBuilder();
		NonTerminal nt = test.addNonTerminal("S");
		Terminal t = test.addTerminal('a');
		Terminal t2 = test.addTerminal('b');
		try {
			test.addProduction(nt, t);
			test.addProduction(nt, t2);
		} catch (SameProductionException e) {
			Assert.fail();
		}
	}

}
