package com.cesar31.captchaweb.parser;

import com.cesar31.captchaweb.model.*;
import static com.cesar31.captchaweb.parser.CaptchaParserSym.*;
import java_cup.runtime.*;

%%

%class CaptchaLex
%type java_cup.runtime.Symbol
%public
%unicode
%cup
// %standalone
%line
%column
// %cupdebug

%{
		private Symbol symbol(int type) {
			return new Symbol(type, yyline + 1, yycolumn + 1, new Token(type, yyline + 1, yycolumn + 1));
			// return new Symbol(type, yyline + 1, yycolumn + 1);
		}

		private Symbol symbol(int type, Object object) {
			return new Symbol(type, yyline + 1, yycolumn + 1, new Token(type, (String) object, yyline + 1, yycolumn + 1));
			// return new Symbol(type, yyline + 1, yycolumn + 1, object);
		}

		private String getString(String str) {
			return str.substring(1, str.length() - 1);
		}
%}

%eofval{
	return symbol(EOF);
%eofval}
%eofclose

LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/* Coments */
InputCharacter = [^\r\n]
CommentContent = ([^-]|\-+[^"->"]|\-[>]|\-*[\w<,:;\'\"])*
EndOfLineComment = "!!" {InputCharacter}* {LineTerminator}?
BlockComment = "<!--" {CommentContent} -* "-->"

Comment = {EndOfLineComment} | {BlockComment}

/* Integer and Decimal */
Integer = 0|[1-9][0-9]*
Decimal = {Integer} \. \d+

/* string and char */
string = {Q} ([^\r\n\"]|[\n])* {Q}
char = {Qs} [^\r\n\']? {Qs}

/* Id */
Id = {Q} [\_\-\$\w]+ {Q}

/* Id Variable */
IdV = [a-zA-Z]\w*

/* Quote */
Q = \"

/* Simple Quote */
Qs = \'

/* measure */
Pixels = {Q} {Integer} "px" {Q}
Percentage = {Q} {Integer} "%" {Q}
IntegerQuote = {Q} {Integer} {Q}

/* Hexadecimal */
Color = #([0-9a-fA-F]{6}|[0-9a-fA-F]{3})

/* Input con comillas */
// Str = {Q} [^\r\n\"\\]+ {Q}

/* String para llamada a funciones entre comillas */
OnClick = {Q} \w+ "(" ")" {Q}

/* url */
Url = {Q} "http" "s"? ":" "/"{2,2} [\w\-\.]+ "." \w{2,5} "/"? \S* {Q}

/* Entrada para etiquetas */
In = [\w\"\'\?\.\\\^¿¡#$%&]+

/* no case-sensitive */
a = [aA]
b = [bB]
c = [cC]
d = [dD]
e = [eE]
// f = [fF]
g = [gG]
h = [hH]
i = [iI]
// j = [jJ]
k = [kK]
l = [lL]
m = [mM]
n = [nN]
o = [oO]
p = [pP]
// q = [qQ]
r = [rR]
s = [sS]
t = [tT]
u = [uU]
v = [vV]
// w = [wW]
x = [xX]
y = [yY]
// z = [zZ]

Gcic = {c} "_" {g}{c}{i}{c}
Head = {c} "_" {h}{e}{a}{d}
Title = {c} "_" {t}{i}{t}{l}{e}
Link = {c} "_" {l}{i}{n}{k}
Body = {c} "_" {b}{o}{d}{y}
Spam = {c} "_" {s}{p}{a}{m}
Input = {c} "_" {i}{n}{p}{u}{t}
TextArea = {c} "_" {t}{e}{x}{t}{a}{r}{e}{a}
Select = {c} "_" {s}{e}{l}{e}{c}{t}
Option = {c} "_" {o}{p}{t}{i}{o}{n}
Div = {c} "_" {d}{i}{v}
Img = {c} "_" {i}{m}{g}
Br = {c} "_" {b}{r}
Button = {c} "_" {b}{u}{t}{t}{o}{n}
H1 = {c} "_" {h} "1"
Paragraph = {c} "_" {p}
Script = {c} "_" {s}{c}{r}{i}{p}{t}{i}{n}{g}

%%

<YYINITIAL> {

	/* reserved words */
	{Gcic}
	{ return symbol(GCIC, yytext()); }

	{Head}
	{ return symbol(HEAD, yytext()); }

	{Title}
	{ return symbol(TITLE, yytext()); }

	{Link}
	{ return symbol(LINK, yytext()); }

	{Body}
	{ return symbol(BODY, yytext()); }

	{Spam}
	{ return symbol(SPAM, yytext()); }

	{Input}
	{ return symbol(INPUT, yytext()); }

	{TextArea}
	{ return symbol(TXTAREA, yytext()); }

	{Select}
	{ return symbol(SELECT, yytext()); }

	{Option}
	{ return symbol(OPTION, yytext()); }

	{Div}
	{ return symbol(DIV, yytext()); }

	{Img}
	{ return symbol(IMG, yytext()); }

	{Br}
	{ return symbol(BR, yytext()); }

	{Button}
	{ return symbol(BUTTON, yytext()); }

	{H1}
	{ return symbol(H1, yytext()); }

	{Paragraph}
	{ return symbol(PARAGRAPH, yytext()); }

	{Script}
	{ return symbol(SCRIPT, yytext()); }

	/* parametros */
	"href"
	{ return symbol(HREF, yytext()); }

	"background"
	{ return symbol(BCKGRND, yytext()); }

	"color"
	{ return symbol(COLOR, yytext()); }

	"font-size"
	{ return symbol(FONTS, yytext()); }

	"font-family"
	{ return symbol(FONTF, yytext()); }

	"text-align"
	{ return symbol(ALIGN, yytext()); }

	"type"
	{ return symbol(TYPE, yytext()); }

	"id"
	{ return symbol(ID, yytext()); }

	"name"
	{ return symbol(NAME, yytext()); }

	"cols"
	{ return symbol(COLS, yytext()); }

	"rows"
	{ return symbol(ROWS, yytext()); }

	"class"
	{ return symbol(CLASS, yytext()); }

	"src"
	{ return symbol(SRC, yytext()); }

	"width"
	{ return symbol(WIDTH, yytext()); }

	"height"
	{ return symbol(HEIGHT, yytext()); }

	"alt"
	{ return symbol(ALT, yytext()); }

	"onclick"
	{ return symbol(CLICK, yytext()); }

	/* reserved words for script */
	"IF"
	{ return symbol(IF, yytext()); }

	"THEN"
	{ return symbol(THEN, yytext()); }

	"ELSE"
	{ return symbol(ELSE, yytext()); }

	"REPEAT"
	{ return symbol(REPEAT, yytext()); }

	"HUNTIL"
	{ return symbol(UNTIL, yytext()); }

	"WHILE"
	{ return symbol(WHILE, yytext()); }

	"THENWHILE"
	{ return symbol(THEN_WHILE, yytext()); }

	"INIT"
	{ return symbol(INIT, yytext()); }

	"END"
	{ return symbol(END, yytext()); }

	"integer"
	{ return symbol(INT, yytext()); }

	"decimal"
	{ return symbol(DEC, yytext()); }

	"boolean"
	{ return symbol(BOOL, yytext()); }

	"true"
	{ return symbol(TRUE, yytext()); }

	"false"
	{ return symbol(FALSE, yytext()); }

	"char"
	{ return symbol(CHR, yytext()); }

	"string"
	{ return symbol(STR, yytext()); }

	/* special functions from clc */
	"ASC"
	{ return symbol(ASC, yytext()); }

	"DESC"
	{ return symbol(DESC, yytext()); }

	"LETPAR_NUM"
	{ return symbol(LETPAR, yytext()); }

	"LETIMPAR_NUM"
	{ return symbol(LETIMPAR, yytext()); }

	"REVERSE"
	{ return symbol(REVERSE, yytext()); }

	"CARACTER_ALEATORIO"
	{ return symbol(RANDOM_C, yytext()); }

	"NUM_ALEATORIO"
	{ return symbol(RANDOM_N, yytext()); }

	"ALERT_INFO"
	{ return symbol(ALERT, yytext()); }

	"EXIT"
	{ return symbol(EXIT, yytext()); }

	"@global"
	{ return symbol(GLOBAL, yytext()); }

	/* reserved words for script */
	"ON_LOAD"
	{ return symbol(ON_LOAD, yytext()); }

	/* font-family */
	{Q} "Courier" {Q}
	{ return symbol(FONTF_VALUE, getString(yytext())); }

	{Q} "Verdana" {Q}
	{ return symbol(FONTF_VALUE, getString(yytext())); }

	{Q} "Arial" {Q}
	{ return symbol(FONTF_VALUE, getString(yytext())); }

	{Q} "Geneva" {Q}
	{ return symbol(FONTF_VALUE, getString(yytext())); }

	{Q} "sans-serif" {Q}
	{ return symbol(FONTF_VALUE, getString(yytext())); }

	/* text-align */
	{Q} "left" {Q}
	{ return symbol(ALIGN_VALUE, getString(yytext())); }

	{Q} "right" {Q}
	{ return symbol(ALIGN_VALUE, getString(yytext())); }

	{Q} "center" {Q}
	{ return symbol(ALIGN_VALUE, getString(yytext())); }

	{Q} "justify" {Q}
	{ return symbol(ALIGN_VALUE, getString(yytext())); }

	/* colors */
	{Q} {Color} {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "black" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "olive" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "teal" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "red" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "blue" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "maroon" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "navy" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "gray" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "lime" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "fuchsia" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "green" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "white" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "purple" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "silver" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "yellow" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	{Q} "aqua" {Q}
	{ return symbol(COLOUR, getString(yytext())); }

	/* type */
	{Q} "text" {Q}
	{ return symbol(TYPE_VALUE, getString(yytext())); }

	{Q} "number" {Q}
	{ return symbol(TYPE_VALUE, getString(yytext())); }

	{Q} "radio" {Q}
	{ return symbol(TYPE_VALUE, getString(yytext())); }

	{Q} "checkbox" {Q}
	{ return symbol(TYPE_VALUE, getString(yytext())); }

	/* class */
	{Q} "row" {Q}
	{ return symbol(CLASS_VALUE, getString(yytext())); }

	{Q} "col" {Q}
	{ return symbol(CLASS_VALUE, getString(yytext())); }

	/* measures */
	{IntegerQuote}
	{ return symbol(INTQ, getString(yytext())); }

	{Pixels}
	{ return symbol(PIXEL, getString(yytext())); }

	{Percentage}
	{ return symbol(PERCNTG, getString(yytext())); }

	{OnClick}
	{ return symbol(ONCLICK, getString(yytext())); }

	{IdV}
	{ return symbol(ID_V, yytext()); }

	/* Id */
	{Id}
	{ return symbol(ID_, getString(yytext())); }

	{Url}
	{ return symbol(URL, getString(yytext())); }

	{string}
	{ return symbol(STRING, getString(yytext())); }

	{char}
	{ return symbol(CHAR, getString(yytext())); }

	/* numbers */
	{Integer}
	{ return symbol(INTEGER, yytext()); }

	{Decimal}
	{ return symbol(DECIMAL, yytext()); }

	/* simbols and operators */
	"<"
	{ return symbol(SMALLER, yytext()); }

	">"
	{ return symbol(GREATER, yytext()); }

	"{"
	{ return symbol(LBRACE, yytext()); }

	"}"
	{ return symbol(RBRACE, yytext()); }

	"["
	{ return symbol(LBRACKET, yytext()); }

	"]"
	{ return symbol(RBRACKET, yytext()); }

	":"
	{ return symbol(COLON, yytext()); }

	";"
	{ return symbol(SEMI, yytext()); }

	"="
	{ return symbol(EQUAL, yytext()); }

	"=="
	{ return symbol(EQEQ, yytext()); }

	"!="
	{ return symbol(NEQ, yytext()); }

	">="
	{ return symbol(GRTREQ, yytext()); }

	"<="
	{ return symbol(SMLLREQ, yytext()); }

	"||"
	{ return symbol(OR, yytext()); }

	"&&"
	{ return symbol(AND, yytext()); }

	"!"
	{ return symbol(NOT, yytext()); }

	"+"
	{ return symbol(PLUS, yytext()); }

	"-"
	{ return symbol(MINUS, yytext()); }

	"*"
	{ return symbol(TIMES, yytext()); }

	"/"
	{ return symbol(DIVIDE, yytext()); }

	"("
	{ return symbol(LPAREN, yytext()); }

	")"
	{ return symbol(RPAREN, yytext()); }

	","
	{ return symbol(COMMA, yytext()); }

	{In}
	{ return symbol(IN, yytext()); }

	/* coments and whitspaces(line terminator) */
	{Comment}
	{ /* Ignore */ }

	{WhiteSpace}
	{ /* Ignore */ }
}

[^]
{
	System.out.println("Error: < " + yytext() + " >");
	return symbol(ERROR, yytext());
	// throw new Error("Ilegal character: <" + yytext() + ">");
}
