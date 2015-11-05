� Instituicao:           	UNIVERSIDADE FEDERAL DE SENTA CATARINA.
� Departamento:          	INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA.
� Graduandos:             	LUCAS FINGER ROMAN (13103523).
						  	RODRIGO PEDRO MARQUES (12200660).
� Data:                   	FLORIAN�POLIS, 05 DE NOVEMBRO DE 2015.

� Linguagem Utilizada:    	Java.
� IDE Utilizada:			Eclipse.
� Link do projeto:			https://github.com/lucas-roman/Trabalho_Formais.git

� TRABALHO II: CONSTRU��O DE UM ANALISADOR L�XICO

- INTRODU��O
	Para a constru��o de um analisador l�xico foi necess�rio fazeer a implementa��o de um algoritmo de minimiza��o
	de aut�matos. Foi necess�rio tamb�m fazer um algoritmo de uni�o de aut�matos que ser� explicado mais a frente.
	
- DESENVOLVIMENTO
	Foi nos requisitados a cria��o de uma linguagem simples com alguns requisitos, s�o eles:
	. Conter pelo menos uma estrutura de repeti��o e uma estrutura condicional.
		Para este item, n�s escolhemos fazer as estruturas 'while' e 'for' como estrutura de repeti��es, e a estrutura
		condicional escolhida foi a 'if'.
		
	. Aceitar comandos de leitura e escrita.
		N�s escolhemos o comando 'read' e 'write', onde um l� e o outro escreve respectivamente.
	
	. Aceitar opera��es alg�bricas simples.
		Escolhemos os s�mbolos padr�es para a representa��o de cada opera��o. S�mbolo '+' para soma; s�mbolo '-' para subtra��o
		e assim por diante.
	
	. Aceitar declara��es e opera��es de estruturas do tipo "array" unidimensional e multidimensional.
		A nossa linguagem s� ir� aceitar arrays dos tipos: int,string,float,char,bool.
	
	. Aceitar comandos de compara��o.
		Os comandos de compara��es que a nossa linguagem possui s�o: diferente (!=), maior ou igual (>=), menor ou igual (<=),
		igual (==), maior (>) e menor (<),
	
	. Aceitar operadores l�gicos.
		Os operadores l�gicos aceitos pela nossa linguagem s�o: e (and), or (ou) e nega��o (not).

	. Aceitar escrita de strings.
		Basta iniciar a declara��o da string com "string nome_da_variavel = alguma sentenca" (sem aspas). N�s ainda iremos definir
		melhor esta declara��o mais a frente.
	
	. Nome das vari�veis pode ser feito usando "_".
		Ok.
	
	. Deve permitir declara��o de constantes num�ricas e booleanas.
		A linguagem ir� aceitar tipos de constantes int,string,float,char,bool.
		
- ALGORITMOS

	� poss�vel encontrar o algoritmo de minimiza��o na classe: /Trabalho_Formais/src/main/lexer/automata/MinimizeComputer.java.
	
	Os algoritmos de opera��es sobre aut�matos (fechamento, uni�o e concatena��o) podem ser encontrados na classe: /Trabalho_Formais/src/main/lexer/automata/AutomataSkeleton.java
	Os algoritmos s�o explicados brevemente na interface /Trabalho_Formais/src/main/lexer/automata/Automata.java.
	
	