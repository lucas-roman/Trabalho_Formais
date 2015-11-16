/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMATICA E ESTATISTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright c 2015
 */

package main.lexer;

import java.util.ArrayList;
import java.util.List;

import main.lexer.Lexema;
import main.lexer.automata.Automata;
import main.lexer.automata.exceptions.DeterministicException;
import main.lexer.automata.exceptions.IllegalAutomataException;
import main.lexer.automata.exceptions.InitialStateMissingException;
import main.lexer.automata.exceptions.InvalidStateException;
import main.lexer.automata.exceptions.MissingStateException;
import main.lexer.automata.structure.graph.AutomataState;
import main.lexer.regularexpression.RegularExpression;
import main.lexer.regularexpression.StringToRE;
import main.lexer.regularexpression.exceptions.IllegalRegularExpressionException;

/*
 * This class is reponsible for building a language.
 * It receives a list of lexemas and then convert it to an automata and then
 * it minimizes it. At the end of each minimization, it makes the union
 * of the new automata with the previous.
 * After that, the automata of the language is returned.
 */
public class LanguageBuilder {

	private List<Lexema> lexemas;

	public LanguageBuilder(List<Lexema> lexemas) {
		this.lexemas = lexemas;
	}

	public Automata lexicalAutomata() throws MissingStateException,
			InvalidStateException, InitialStateMissingException,
			IllegalAutomataException, DeterministicException,
			IllegalRegularExpressionException {
		List<String> order = new ArrayList<>();
		RegularExpression baseReg = RegularExpression
				.emptySetRegularExpression();
		Automata returnAutomata = baseReg.createAutomata();
		for (Lexema lex : lexemas) {
			order.add(lex.getTag());
			RegularExpression spaceRE = StringToRE.stringToRE("\\ ");
			RegularExpression newLineRE = RegularExpression.createRegularExpression('\n');
			RegularExpression baseRE = spaceRE.alternation(newLineRE);
			baseRE = baseRE.kleene();
			Automata base = baseRE.createAutomata();
			Automata other = lex.automataForLexema();
			other = base.concatenate(other);
			// Convert and minimize for each lexema
			try {
				other = other.convert();
			} catch (DeterministicException e) {
			}
			other = other.minimize();
			for (AutomataState acceptState : other.acceptStates()) {
				acceptState.setTag(lex.getTag());
			}
			returnAutomata = returnAutomata.union(other);
		}
		returnAutomata.addTagOrder(order);
		returnAutomata = returnAutomata.convert();
		for (AutomataState state : returnAutomata.getStates()) {
			if (!state.accepts()) {
				state.setTag("ERROR");
			}
		}
		return returnAutomata;
	}
}
