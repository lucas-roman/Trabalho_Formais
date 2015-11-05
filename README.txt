• Instituicao:           	UNIVERSIDADE FEDERAL DE SENTA CATARINA.
• Departamento:          	INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA.
• Graduandos:             	LUCAS FINGER ROMAN (13103523).
						  	RODRIGO PEDRO MARQUES (12200660).
• Data:                   	FLORIANÓPOLIS, 05 DE NOVEMBRO DE 2015.

• Linguagem Utilizada:    	Java.
• IDE Utilizada:			Eclipse.
• Link do projeto:			https://github.com/lucas-roman/Trabalho_Formais.git

• TRABALHO II: CONSTRUÇÃO DE UM ANALISADOR LÉXICO

- INTRODUÇÃO
	Para a construção de um analisador léxico foi necessário fazeer a implementação de um algoritmo de minimização
	de autômatos. Foi necessário também fazer um algoritmo de união de autômatos que será explicado mais a frente.
	
- DESENVOLVIMENTO
	Foi nos requisitados a criação de uma linguagem simples com alguns requisitos, são eles:
	. Conter pelo menos uma estrutura de repetição e uma estrutura condicional.
		Para este item, nós escolhemos fazer as estruturas 'while' e 'for' como estrutura de repetições, e a estrutura
		condicional escolhida foi a 'if'.
		
	. Aceitar comandos de leitura e escrita.
		Nós escolhemos o comando 'read' e 'write', onde um lê e o outro escreve respectivamente.
	
	. Aceitar operações algébricas simples.
		Escolhemos os símbolos padrões para a representação de cada operação. Símbolo '+' para soma; símbolo '-' para subtração
		e assim por diante.
	
	. Aceitar declarações e operações de estruturas do tipo "array" unidimensional e multidimensional.
		A nossa linguagem só irá aceitar arrays dos tipos: int,string,float,char,bool.
	
	. Aceitar comandos de comparação.
		Os comandos de comparações que a nossa linguagem possui são: diferente (!=), maior ou igual (>=), menor ou igual (<=),
		igual (==), maior (>) e menor (<),
	
	. Aceitar operadores lógicos.
		Os operadores lógicos aceitos pela nossa linguagem são: e (and), or (ou) e negação (not).

	. Aceitar escrita de strings.
		Basta iniciar a declaração da string com "string nome_da_variavel = alguma sentenca" (sem aspas). Nós ainda iremos definir
		melhor esta declaração mais a frente.
	
	. Nome das variáveis pode ser feito usando "_".
		Ok.
	
	. Deve permitir declaração de constantes numéricas e booleanas.
		A linguagem irá aceitar tipos de constantes int,string,float,char,bool.
		
- ALGORITMOS

	É possível encontrar o algoritmo de minimização na classe: /Trabalho_Formais/src/main/lexer/automata/MinimizeComputer.java.
	
	Os algoritmos de operações sobre autômatos (fechamento, união e concatenação) podem ser encontrados na classe: /Trabalho_Formais/src/main/lexer/automata/AutomataSkeleton.java
	Os algoritmos são explicados brevemente na interface /Trabalho_Formais/src/main/lexer/automata/Automata.java.
	
	