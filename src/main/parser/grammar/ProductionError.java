package main.parser.grammar;

public class ProductionError extends ContextFreeProduction {
	
	@Override
	public boolean checkValid() {
		return false;
	}

}
