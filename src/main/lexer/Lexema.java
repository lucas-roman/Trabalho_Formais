package main.lexer;

import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.StringToRE;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

public class Lexema {

	private RegularExpression regularExpression;
	private String tag;

	public Lexema(String tag, String regularExpression)
			throws IllegalRegularExpressionException {
		this.regularExpression = StringToRE.stringToRE(regularExpression);
		this.tag = tag;
	}

	public Automata automataForLexema() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException {
		return regularExpression.createAutomata();
	}

}
