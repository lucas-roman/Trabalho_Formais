KEYWORD
program|end|if|else|while|and|or|not|true|false|read|write

VAR
int|string|double|char

SYMBOL
=|>|<|+|-|*|/

#Scratch
E	-> TE'
E'	-> +TE'|-TE'|Vazio
T	-> FT'
T'	-> *FT'|/FT'|Vazio
F	-> (E) | id

/* Palavras Reservadas */

TOKEN: {
	< PROGRAM:	"program"	>
|	< END:		"end"		>
|	< IF:		"if"		>
|	< ELSE:		"else"		>
|	< WHILE:	"while"		>
|	< AND:		"and"		>
|	< OR:		"or"		>
|	< NOT:		"not"		>
|	< TRUE:		"true"		>
| 	< FALSE:	"false"		>
| 	< READ:		"read"		>
|	< WRITE:	"write"		>
|	< INT:		"int"		>
|	< STRING:	"string"	>
|	< DOUBLE:	"double"	>
| 	< CHAR:		"char"		>
}

/* Operadores */

TOKEN: {
	< ASSIGN:	"="	>
|	< GT:		">" >
|	< LT:		"<" >
|	< PLUS:		"+"	>
|	< MINUS:	"-" >
|	< MULT:		"*"	>
|	< DIV:		"/"	>
}

/* Special Symbols */
TOKEN: {
	< LEFTPAREN:	"("	>
|	< RIGHTPAREN:	")"	>
|	< LEFTBRACE:	"{"	>
|	< RIGHTBRACE:	"}"	>
|	< LEFTBRACKET:	"["	>
|	< RIGHTBRACKET:	"]" >
|	< COMMA:		","	>
}