� Instituicao:           	UNIVERSIDADE FEDERAL DE SENTA CATARINA.
� Departamento:          	INE - DEPARTAMENTO DE INFORM�TICA E ESTAT�STICA.
� Graduandos:             	LUCAS FINGER ROMAN (13103523).
						  	RODRIGO PEDRO MARQUES (12200660).
� Data:                   	FLORIAN�POLIS, 02 DE OUTUBRO DE 2015.

� Linguagem Utilizada:    	Java.
� IDE Utilizada:			Eclipse.

� GUIA DE UTILIZA��O:

- INTRODU��O

	Este trabalho foi desenvolvido por Lucas F. Roman e Rodrigo Pedro Marques, 
ambos graduandos do curso de Bacharelado de Ci�ncias da Computa��o na Universidade
Federal de Santa Catarina. Este trabalho foi desenvolvido para a disciplina de
Linguagens Formais e Compiladores, onde os principais s�o:
a) Transformar Aut�matos Finitos N�o Determin�sticos (AFND) para Aut�mato Finito 
Determin�stico(AFD).
b) Transformar AFNDs com epsolon transi��es para AFDs.
c) Transformar Gram�ticas Regulares (GR) para AFs.
d) Transformar AFs para GR.
e) Converter Express�es Regulares (ER) para AFs.
f) Converter AFs para ERs.

- DESENVOLVIMENTO E ORGANIZA��O DO C�DIGO
	
	O c�digo se encontra em v�rios pacotes para uma melhor organiza��o do mesmo. Os
principais c�digos e classes se encontram nos pacotes "main.*". Os pacotes "test.*" s�o
usados para testar partes do c�digos, como por exemplo, se o construtor est� funcionando 
corretamente; se os algoritmos de convers�o est�o operando como desejado; se um dado AF
aceita algumas palavras; etc.
	Grande parte da documenta��o se encontra nas classes de interface, onde � poss�vel
consultar o que cada m�todo faz sem precisar ficar analisando a fundo.
	A estrutura dos aut�matos foram baseadas em grafo pois achamos mais pr�tico e vers�til sua
implementa��o.
	A classe principal se encontra no pacote "main.model.commandline". Ela recebe tr�s argumentos:
	i. 		Tipo de algoritmo a executar, i.e, AFND para AFD; GR para AF; AF para GR; etc.
	ii. 	Arquivo de entrada contendo o AF, GR ou ER.
	iii. 	Arquivo de sa�da onde ser� escrito o resultado do algoritmo.


- UTILIZANDO O PROGRAMA
	
	Para utilizar o programa deve-se usar o .jar para executar o algoritmo desejado. Ele deve
ser utilizado via prompt de comando e deve-se especificar o que deseja-se fazer com o arquivo de entrada
citado. Os comandos existentes s�o:
	. "aftoer":		Transforma um AF para uma ER.
	. "ertoaf": 	Transforma uma ER para AF.
	. "afndtoaf": 	Transforma um AFND para AF.
	. "aftogr":		Transforma um AF para GR.

	OBS: Usar os comandos sem aspas e pontua��es.
	
	O .jar recebe como primeira entrada o comando, em seguida o arquivo de entrada e o arquivo de sa�da.
Para saber como escrever um AF, ER ou GR, basta ir at� a pasta "test_files" e consultar alguns exemplos.
Foi criada uma certa estrutura para padronizar as entradas e facilitar o "parseamento" do arquivo de entrada.

	-> ARQUIVOS PARA EXPRESS�O REGULAR
		Para se criar um arquivo com express�o regular, ele deve conter na sua primeira linha a palavra
	reservada "REGULAREXPRESSION". Esta palavra indica que na linha seguinte h� uma express�o regular.
	A nota��o usada para a cria��o de express�es regulares foi a mesma vista em aula. Por exemplo:
	a(a|b)*a?(a|b)+
	A express�o regular deve ser inserida somente em uma linha. A linha seguinte dever� conter a palavra
	reservada "END", que indica o fim da express�o regular.
	
	-> ARQUIVOS PARA GRAM�TICAS REGULARES
		Para se criar um arquivo com uma GR, a sua primeira linha dever� conter a palavra reservada
	"INITIALSYMBOL" que indicar� que na linha seguinte haver� o s�mbolo inicial da GR.
	Na linha seguinte ent�o, deve-se haver um s�mbolo que representa o s�mbolo inicial.
	Na linha seguinte, dever� haver a palavra reservada "END", que indica o final do comando "INITIALSYMBOL".
	Na linha seguinte, dever� haver a palabra reservada "TRANSITIONS", que indica que nas pr�ximas linhas
	haver�o os s�mbolos e suas produ��es.
	Nas linhas seguintes ent�o dever� haver os s�mbolos e suas produ��es. A nota��o usada � muito parecida
	com a usada em aula. Por exemplo: S=a|b|aA|bB.
	Na linha ap�s as produ��es dever� haver a palavra reservada "END", indicando o final das transi��es.
	
	-> ARQUIVOS PARA AUT�MATOS FINITOS
		Para criar um arquivo com um AF, a sua primeira linha dever� conter a palavra reservada
	"STATES" que indicar� que nas linhas seguintes haver�o os estados do automato. Os estados devem
	ser inseridos um por linha. Ap�s a insers�o dos mesmos, deve-se colocar na linha seguinte do �ltimo s�mbolo
	a palavra reservada "END".
	Em seguida, deve-se indicar os estados finais come�ando com a palavra "FINALSTATES".
	Nas linhas seguintes deve-se inserir os estados finais. Tamb�m devem ser inseridos um por linha e devem
	estar listados na lista de estados. Ao final, colocar a palavra "END".
	Em seguida, deve-se inserir a palavra "TRANSITIONS" que indica que nas linhas seguintes vir�o as transi��es
	do AF.
	Para criar uma transi��o, primeiro vem o estado origem seguido do destino e em seguida o simbolo pelo qual
	ele transita. OBS: os estados devem ser separados por espa�os. Caso queira inserir "epsolon transi��o" basta
	colocar um espa�o no lugar do s�mbolo de transi��o.
	Ao final, deve-se colocar a palavra "END".
	

