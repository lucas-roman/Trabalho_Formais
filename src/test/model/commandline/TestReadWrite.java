package test.model.commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.StringToRE;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;
import main.model.commandline.fileutils.Reader;
import main.model.commandline.fileutils.Writer;
import main.model.commandline.fileutils.exceptions.IllegalOrderOfTextStructure;
import main.model.commandline.fileutils.exceptions.IllegalStartOfText;

import org.junit.Test;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA INE - DEPARTAMENTO DE INFORM�TICA E
 * ESTAT�STICA LINGUAGENS FORMAIS E COMPILADORES
 * 
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES Copyright � 2015
 */

// TODO : implementar tudo
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
		RegularExpression tst = read.readRegularExpression(new File("rexprtest.Out"));
		comp = tst.createAutomata();
		Assert.assertTrue(comp.accepts("abbbb"));
		Assert.assertTrue(comp.accepts("aa"));
		Assert.assertTrue(comp.accepts("ab"));
		Assert.assertFalse(comp.accepts(""));
		Assert.assertFalse(comp.accepts("c"));
		
	}

}
