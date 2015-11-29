package main.fileio;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import main.fileio.exceptions.IllegalStructureOfText;
import main.parser.grammar.ContextFreeEmptyWord;
import main.parser.grammar.ContextFreeGrammar;
import main.parser.grammar.ContextFreeNonTerminal;
import main.parser.grammar.ContextFreeProduction;
import main.parser.grammar.ContextFreeTerminalSymbol;

public class ContextFreeGrammarIO {

	public ContextFreeGrammar readGrammar(String fileName)
			throws FileNotFoundException, IllegalStructureOfText {
		Scanner scan = new Scanner(new File(fileName));
		ContextFreeGrammar returnGrammar = new ContextFreeGrammar();
		while (scan.hasNextLine()) {
			String nextLine = scan.nextLine();
			String[] broke = nextLine.split("->");
			if (broke.length < 1) {
				scan.close();
				throw new IllegalStructureOfText();
			}
			ContextFreeNonTerminal nonTerminal = returnGrammar
					.createNonTerminalForString(broke[0].trim());
			if (broke.length == 1) {
				ContextFreeProduction production = new ContextFreeProduction();
				production.addSymbol(ContextFreeEmptyWord.getInstance());
				nonTerminal.addProduction(production);
			} else if (broke.length == 2 && broke[1].trim().equals("")){
				ContextFreeProduction production = new ContextFreeProduction();
				production.addSymbol(ContextFreeEmptyWord.getInstance());
				nonTerminal.addProduction(production);
			}
			else {
				String productions = broke[1];
				broke = productions.split("\\|");
				for (String str : broke) {
					String[] productionsSymbols = str.split(" ");
					ContextFreeProduction production = new ContextFreeProduction();
					for (String symbolStringValue : productionsSymbols) {
						symbolStringValue = symbolStringValue.trim();
						if (!symbolStringValue.equals("")) {
							if (symbolStringValue.startsWith("{")
									&& symbolStringValue.endsWith("}")) {
								symbolStringValue = symbolStringValue
										.substring(1,
												symbolStringValue.length() - 1);
								ContextFreeNonTerminal nt = returnGrammar
										.createNonTerminalForString(symbolStringValue);
								production.addSymbol(nt);
							} else {
								ContextFreeTerminalSymbol term = returnGrammar
										.createTerminalForString(symbolStringValue);
								production.addSymbol(term);
							}
						}
					}
					nonTerminal.addProduction(production);
				}
			}
		}
		scan.close();
		return returnGrammar;
	}

}
