package main.model.commandline;

import main.lexer.automata.Automata;
import main.lexer.grammar.RegularGrammar;
import main.lexer.regularexpression.RegularExpression;

public class CommandLineMain {
	
	
	/*
	 * Argumentos :
	 * 0 -> Tipo que vai converter de
	 * 1 -> Tipo que vai converter para
	 * 2 -> Input file
	 * 3 -> Output file
	 */
	
	
	/*
	 * Outro TODO : criar arquivos pra ler de teste e uma String teste para comparar com o output. Ler output e comparar com o teste. Deve ser feito dentro do
	 * pacote test.model.commandline
	 */

	public static void main(String[] args) {
		//TODO primeiro argumento : tipo de arquivo. Segundo argumento : o arquivo pra ler.
		//Se for chamado pra gramática
		if(args[0].equals("grammar")) {
			if(args[1].equals("automata")) {
				Automata aut = readGrammar(args[2]);
				writeOutputFile(aut, args[3]);
			}
			else {
				//Erro
				System.exit(1);
			}
		}
		else if (args[0].equals("rexr")) {
			if(args[1].equals("automata")) {
				Automata aut = readRegularExpression(args[2]);
				writeOutputFile(aut, args[3]);
			}
			else {
				//Erro
				System.exit(1);
			}
		}
		else if (args[0].equals("automata")) {
			if(args[1].equals("grammar")) {
				RegularGrammar gr = readAutomataByGrammar(args[2]);
				writeOutputFile(gr, args[3]);
			}
			else if(args[1].equals("rexr")) {
				RegularExpression re = readAutomataByRegularExpression(args[2]);
				writeOutputFile(re, args[3]);
			}
			else {
				//Erro
				System.exit(1);
			}
		}
		else {
			//Erro
			System.exit(1);
		}
	}

	private static void writeOutputFile(RegularExpression re, String args) {
		// TODO Auto-generated method stub
		
	}

	private static RegularExpression readAutomataByRegularExpression(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void writeOutputFile(RegularGrammar gr, String args) {
		// TODO Auto-generated method stub
		
	}

	private static RegularGrammar readAutomataByGrammar(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private static void writeOutputFile(Automata automata, String args) {
		// TODO Aqui o automato deve ir para uma string de saída.
		
	}

	private static Automata readRegularExpression(String string) {
		// TODO ler arquivo de expressão regular aqui. CUIDADO: expressão regular deve gerar uma árvore de expressão de acordo com
		// precedência de operadores. Por isso, estou pensando em criar uma classe que lê uma String e cria uma expressão regular dentro do pacote
		//de expressão regular. (classe StringToRE). 
		return null;
	}

	private static Automata readGrammar(String string) {
		//TODO ler arquivo de gramática aqui. Precisa ser implementado aqui o jeito de ler um arquivo e criar a gramática. Usar o RegularGrammarBuilder
		//para adicionar e pegar os símbolos não terminais e terminais. Exemplos estão no teste
		return null;
	}
	
}
