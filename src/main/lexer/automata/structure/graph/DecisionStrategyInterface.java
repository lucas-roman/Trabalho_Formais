package main.lexer.automata.structure.graph;

public interface DecisionStrategyInterface {
	
	public boolean decide();
	
	public static class DecisionAcceptStrategy implements DecisionStrategyInterface {

		@Override
		public boolean decide() {
			return true;
		}
		
	}
	
	public static class DecisionRejectStrategy implements DecisionStrategyInterface {

		@Override
		public boolean decide() {
			return false;
		}
		
	}

}
