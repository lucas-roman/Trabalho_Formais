package main.lexer.automata.structure.graph;

/**
 * UNIVERSIDADE FEDERAL DE SANTA CATARINA
 * INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA
 * LINGUAGENS FORMAIS E COMPILADORES
 * @author LUCAS FINGER ROMAN
 * @author RODRIGO PEDRO MARQUES
 * Copyright © 2015
 */

/*
 * Simple implementation. One state accepts, the other rejects. Used by states of the graph.
 */
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
