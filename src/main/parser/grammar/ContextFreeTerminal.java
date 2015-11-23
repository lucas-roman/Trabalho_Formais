package main.parser.grammar;

import java.util.ArrayList;
import java.util.List;

public class ContextFreeTerminal implements ContextFreeSymbol {

	@Override
	public List<ContextFreeProduction> productionsForSymbol() {
		List<ContextFreeProduction> returnList = new ArrayList<>();
		ContextFreeProduction prod = new ContextFreeProduction();
		prod.addSymbol(this);
		returnList.add(prod);
		return returnList;
	}
	

}
