• Instituicao:           	UNIVERSIDADE FEDERAL DE SENTA CATARINA.
• Departamento:          	INE - DEPARTAMENTO DE INFORMÁTICA E ESTATÍSTICA.
• Graduandos:             	LUCAS FINGER ROMAN (13103523).
						  	RODRIGO PEDRO MARQUES (12200660).
• Data:                   	FLORIANÓPOLIS, 02 DE OUTUBRO DE 2015.

• Linguagem Utilizada:    	Java.
• IDE Utilizada:			Eclipse.

• GUIA DE UTILIZAÇÃO:

- INTRODUÇÃO

	Este trabalho foi desenvolvido por Lucas F. Roman e Rodrigo Pedro Marques, 
ambos graduandos do curso de Bacharelado de Ciências da Computação na Universidade
Federal de Santa Catarina. Este trabalho foi desenvolvido para a disciplina de
Linguagens Formais e Compiladores, onde os principais são:
a) Transformar Autômatos Finitos Não Determinísticos (AFND) para Autômato Finito 
Determinístico(AFD).
b) Transformar AFNDs com epsolon transições para AFDs.
c) Transformar Gramáticas Regulares (GR) para AFs.
d) Transformar AFs para GR.
e) Converter Expressões Regulares (ER) para AFs.
f) Converter AFs para ERs.

- DESENVOLVIMENTO E ORGANIZAÇÃO DO CÓDIGO
	
	O código se encontra em vários pacotes para uma melhor organização do mesmo. Os
principais códigos e classes se encontram nos pacotes "main.*". Os pacotes "test.*" são
usados para testar partes do códigos, como por exemplo, se o construtor está funcionando 
corretamente; se os algoritmos de conversão estão operando como desejado; se um dado AF
aceita algumas palavras; etc.
	Grande parte da documentação se encontra nas classes de interface, onde é possível
consultar o que cada método faz sem precisar ficar analisando a fundo.
	A estrutura dos autômatos foram baseadas em grafo pois achamos mais prático e versátil sua
implementação.
	A classe principal se encontra no pacote "main.model.commandline". Ela recebe três argumentos:
	i. 		Tipo de algoritmo a executar, i.e, AFND para AFD; GR para AF; AF para GR; etc.
	ii. 	Arquivo de entrada contendo o AF, GR ou ER.
	iii. 	Arquivo de saída onde será escrito o resultado do algoritmo.


- UTILIZANDO O PROGRAMA
	
	Para utilizar o programa deve-se usar o .jar para executar o algoritmo desejado. Ele deve
ser utilizado via prompt de comando e deve-se especificar o que deseja-se fazer com o arquivo de entrada
citado. Os comandos existentes são:
	. "aftoer":		Transforma um AF para uma ER.
	. "ertoaf": 	Transforma uma ER para AF.
	. "afndtoaf": 	Transforma um AFND para AF.
	. "aftogr":		Transforma um AF para GR.

	OBS: Usar os comandos sem aspas e pontuações.
	
	O .jar recebe como primeira entrada o comando, em seguida o arquivo de entrada e o arquivo de saída.
Para saber como escrever um AF, ER ou GR, basta ir até a pasta "test_files" e consultar alguns exemplos.
Foi criada uma certa estrutura para padronizar as entradas e facilitar o "parseamento" do arquivo de entrada.

	-> ARQUIVOS PARA EXPRESSÃO REGULAR
		Para se criar um arquivo com expressão regular, ele deve conter na sua primeira linha a palavra
	reservada "REGULAREXPRESSION". Esta palavra indica que na linha seguinte há uma expressão regular.
	A notação usada para a criação de expressões regulares foi a mesma vista em aula. Por exemplo:
	a(a|b)*a?(a|b)+
	A expressão regular deve ser inserida somente em uma linha. A linha seguinte deverá conter a palavra
	reservada "END", que indica o fim da expressão regular.
	
	-> ARQUIVOS PARA GRAMÁTICAS REGULARES
		Para se criar um arquivo com uma GR, a sua primeira linha deverá conter a palavra reservada
	"INITIALSYMBOL" que indicará que na linha seguinte haverá o símbolo inicial da GR.
	Na linha seguinte então, deve-se haver um símbolo que representa o símbolo inicial.
	Na linha seguinte, deverá haver a palavra reservada "END", que indica o final do comando "INITIALSYMBOL".
	Na linha seguinte, deverá haver a palabra reservada "TRANSITIONS", que indica que nas próximas linhas
	haverão os símbolos e suas produções.
	Nas linhas seguintes então deverá haver os símbolos e suas produções. A notação usada é muito parecida
	com a usada em aula. Por exemplo: S=a|b|aA|bB.
	Na linha após as produções deverá haver a palavra reservada "END", indicando o final das transições.
	
	-> ARQUIVOS PARA AUTÔMATOS FINITOS
		Para criar um arquivo com um AF, a sua primeira linha deverá conter a palavra reservada
	"STATES" que indicará que nas linhas seguintes haverão os estados do automato. Os estados devem
	ser inseridos um por linha. Após a insersão dos mesmos, deve-se colocar na linha seguinte do último símbolo
	a palavra reservada "END".
	Em seguida, deve-se indicar os estados finais começando com a palavra "FINALSTATES".
	Nas linhas seguintes deve-se inserir os estados finais. Também devem ser inseridos um por linha e devem
	estar listados na lista de estados. Ao final, colocar a palavra "END".
	Em seguida, deve-se inserir a palavra "TRANSITIONS" que indica que nas linhas seguintes virão as transições
	do AF.
	Para criar uma transição, primeiro vem o estado origem seguido do destino e em seguida o simbolo pelo qual
	ele transita. OBS: os estados devem ser separados por espaços. Caso queira inserir "epsolon transição" basta
	colocar um espaço no lugar do símbolo de transição.
	Ao final, deve-se colocar a palavra "END".
	

