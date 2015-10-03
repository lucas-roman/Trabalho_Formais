package test.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

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
import main.lexer.grammar.exceptions.NonTerminalMissingException;
import main.lexer.grammar.exceptions.StartSymbolMissingException;
import main.lexer.grammar.exceptions.TerminalMissingException;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.StringToRE;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;
import main.model.commandline.fileutils.Reader;
import main.model.commandline.fileutils.Writer;
import main.model.commandline.fileutils.exceptions.IllegalOrderOfTextStructure;
import main.model.commandline.fileutils.exceptions.IllegalStartOfText;
import main.model.commandline.fileutils.exceptions.IllegalTextStructure;

import org.junit.Test;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA INE - DEPARTAMENTO DE INFORM�TICA E
 * ESTAT�STICA LINGUAGENS FORMAIS E COMPILADORES
 * 
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES Copyright � 2015
 */

public class TestReadWrite {

	@Test
	public void testWriteRegularExpression()
			throws IllegalRegularExpressionException,
			UnsupportedEncodingException, FileNotFoundException,
			MissingStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException,
			IllegalStartOfText, IllegalOrderOfTextStructure {
		Writer writ = new Writer("rexprtest.Out");
		RegularExpression re = StringToRE.stringToRE("ab*(a|b)");
		writ.writeRegularExpression(re);
		writ = new Writer("aut.Out");
		writ.writeAutomata(re.createAutomata());
		Reader read = new Reader();
		Automata comp = read.readAutomata(new File("aut.Out"));
		Assert.assertTrue(comp.accepts("abbbb"));
		Assert.assertTrue(comp.accepts("aa"));
		Assert.assertTrue(comp.accepts("ab"));
		Assert.assertFalse(comp.accepts(""));
		Assert.assertFalse(comp.accepts("c"));
		RegularExpression tst = read.readRegularExpression(new File(
				"rexprtest.Out"));
		comp = tst.createAutomata();
		Assert.assertTrue(comp.accepts("abbbb"));
		Assert.assertTrue(comp.accepts("aa"));
		Assert.assertTrue(comp.accepts("ab"));
		Assert.assertFalse(comp.accepts(""));
		Assert.assertFalse(comp.accepts("c"));

	}

	@Test
	public void testWriteGrammar() throws NonTerminalMissingException,
			TerminalMissingException, StartSymbolMissingException,
			FileNotFoundException, IllegalStartOfText, IllegalTextStructure,
			IllegalOrderOfTextStructure, MissingStateException,
			OverrideInitialStateException, InvalidStateException,
			InitialStateMissingException, IllegalAutomataException,
			DeterministicException {
		RegularGrammarBuilder builder = new RegularGrammarBuilder();
		builder.addTerminal('a');
		builder.addTerminal('b');
		builder.addNonTerminal("S");
		builder.addNonTerminal("A");
		builder.addNonTerminal("B");
		builder.addProduction(builder.getNonTerminalOf("S"),
				builder.getTerminalOf('a'));
		builder.addEmptyWord();
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
		RegularGrammar grammar = builder.createGrammar();
		Automata result = grammar.createAutomata().convert();
		Assert.assertTrue(result.accepts(""));
		Assert.assertTrue(result.accepts("a"));
		Assert.assertTrue(result.accepts("b"));
		Assert.assertTrue(result.accepts("abba"));
		Assert.assertTrue(result.accepts("baabbababbab"));
		Assert.assertTrue(result.accepts("aa"));
		Assert.assertTrue(result.accepts("bb"));
		Assert.assertFalse(result.accepts("ab"));
		Assert.assertFalse(result.accepts("ba"));
		Assert.assertFalse(result.accepts("abababaaab"));
		Assert.assertFalse(result.accepts("bababaaaaba"));

		Writer writer = new Writer("grammTest.Out");
		writer.writeGrammar(grammar);
		Reader readTest = new Reader();
		RegularGrammar toTest = readTest.readRegularGrammar(new File(
				"grammTest.Out"));
		result = toTest.createAutomata().convert();
		Assert.assertTrue(result.accepts("a"));
		Assert.assertTrue(result.accepts("b"));
		Assert.assertTrue(result.accepts("abba"));
		Assert.assertTrue(result.accepts("baabbababbab"));
		Assert.assertTrue(result.accepts("aa"));
		Assert.assertTrue(result.accepts("bb"));
		Assert.assertFalse(result.accepts("ab"));
		Assert.assertFalse(result.accepts("ba"));
		Assert.assertFalse(result.accepts("abababaaab"));
		Assert.assertFalse(result.accepts("bababaaaaba"));
	}


}
