
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

package com.cesar31.captchaweb.parser;

/** CUP generated class containing symbol constants. */
public class CaptchaParserSym {
  /* terminals */
  public static final int LETIMPAR = 46;
  public static final int TIMES = 80;
  public static final int BOOL = 37;
  public static final int ALIGN_VALUE = 90;
  public static final int H1 = 16;
  public static final int TYPE = 25;
  public static final int ID_2 = 60;
  public static final int ROWS = 86;
  public static final int SEMI = 70;
  public static final int GET = 53;
  public static final int UNTIL = 103;
  public static final int ASC = 43;
  public static final int NAME = 27;
  public static final int INIT = 106;
  public static final int ID_ = 59;
  public static final int COMMA = 92;
  public static final int ALT = 33;
  public static final int RBRACE = 66;
  public static final int RPAREN = 84;
  public static final int INTEGER = 35;
  public static final int LBRACE = 65;
  public static final int LPAREN = 83;
  public static final int PROCESS = 108;
  public static final int NOT = 77;
  public static final int FALSE = 39;
  public static final int PERCNTG = 58;
  public static final int ERROR = 88;
  public static final int DEC = 62;
  public static final int THEN = 100;
  public static final int STR = 42;
  public static final int FONTS = 22;
  public static final int WIDTH = 31;
  public static final int REVERSE = 47;
  public static final int INTQ = 56;
  public static final int CLASS = 29;
  public static final int QS = 41;
  public static final int SELECT = 10;
  public static final int FONTF = 23;
  public static final int REDIRECT = 52;
  public static final int TRUE = 38;
  public static final int PIXEL = 57;
  public static final int PLUS = 78;
  public static final int DIVIDE = 81;
  public static final int WHILE = 104;
  public static final int EQUAL = 85;
  public static final int COLS = 28;
  public static final int CHAR = 98;
  public static final int FONTF_VALUE = 87;
  public static final int ALIGN = 24;
  public static final int DECIMAL = 36;
  public static final int LETPAR = 45;
  public static final int INPUT = 8;
  public static final int TXTAREA = 9;
  public static final int GCIC = 2;
  public static final int GREATER = 63;
  public static final int DIV = 12;
  public static final int ELSE = 101;
  public static final int INT = 61;
  public static final int URL = 82;
  public static final int EQEQ = 71;
  public static final int LINK = 5;
  public static final int EOF = 0;
  public static final int SMLLREQ = 74;
  public static final int BUTTON = 15;
  public static final int PARAGRAPH = 17;
  public static final int REPEAT = 102;
  public static final int GRTREQ = 73;
  public static final int SPAM = 7;
  public static final int MINUS = 79;
  public static final int SCRIPT = 18;
  public static final int SRC = 30;
  public static final int IN = 95;
  public static final int BODY = 6;
  public static final int HREF = 19;
  public static final int TITLE = 4;
  public static final int THEN_WHILE = 105;
  public static final int OR = 75;
  public static final int error = 1;
  public static final int EXIT = 51;
  public static final int CLASS_VALUE = 91;
  public static final int CHR = 40;
  public static final int IF = 99;
  public static final int BCKGRND = 20;
  public static final int DESC = 44;
  public static final int ID = 26;
  public static final int CLICK = 34;
  public static final int COLOR = 21;
  public static final int ON_LOAD = 93;
  public static final int HEAD = 3;
  public static final int END = 107;
  public static final int ALERT = 50;
  public static final int COLON = 69;
  public static final int IMG = 13;
  public static final int RANDOM_N = 49;
  public static final int HEIGHT = 32;
  public static final int BR = 14;
  public static final int RBRACKET = 68;
  public static final int GLOBAL = 54;
  public static final int OPTION = 11;
  public static final int TYPE_VALUE = 89;
  public static final int INSERT = 109;
  public static final int ID_V = 96;
  public static final int STRING = 97;
  public static final int ONCLICK = 94;
  public static final int COLOUR = 55;
  public static final int RANDOM_C = 48;
  public static final int SMALLER = 64;
  public static final int NEQ = 72;
  public static final int AND = 76;
  public static final int LBRACKET = 67;
  public static final String[] terminalNames = new String[] {
  "EOF",
  "error",
  "GCIC",
  "HEAD",
  "TITLE",
  "LINK",
  "BODY",
  "SPAM",
  "INPUT",
  "TXTAREA",
  "SELECT",
  "OPTION",
  "DIV",
  "IMG",
  "BR",
  "BUTTON",
  "H1",
  "PARAGRAPH",
  "SCRIPT",
  "HREF",
  "BCKGRND",
  "COLOR",
  "FONTS",
  "FONTF",
  "ALIGN",
  "TYPE",
  "ID",
  "NAME",
  "COLS",
  "CLASS",
  "SRC",
  "WIDTH",
  "HEIGHT",
  "ALT",
  "CLICK",
  "INTEGER",
  "DECIMAL",
  "BOOL",
  "TRUE",
  "FALSE",
  "CHR",
  "QS",
  "STR",
  "ASC",
  "DESC",
  "LETPAR",
  "LETIMPAR",
  "REVERSE",
  "RANDOM_C",
  "RANDOM_N",
  "ALERT",
  "EXIT",
  "REDIRECT",
  "GET",
  "GLOBAL",
  "COLOUR",
  "INTQ",
  "PIXEL",
  "PERCNTG",
  "ID_",
  "ID_2",
  "INT",
  "DEC",
  "GREATER",
  "SMALLER",
  "LBRACE",
  "RBRACE",
  "LBRACKET",
  "RBRACKET",
  "COLON",
  "SEMI",
  "EQEQ",
  "NEQ",
  "GRTREQ",
  "SMLLREQ",
  "OR",
  "AND",
  "NOT",
  "PLUS",
  "MINUS",
  "TIMES",
  "DIVIDE",
  "URL",
  "LPAREN",
  "RPAREN",
  "EQUAL",
  "ROWS",
  "FONTF_VALUE",
  "ERROR",
  "TYPE_VALUE",
  "ALIGN_VALUE",
  "CLASS_VALUE",
  "COMMA",
  "ON_LOAD",
  "ONCLICK",
  "IN",
  "ID_V",
  "STRING",
  "CHAR",
  "IF",
  "THEN",
  "ELSE",
  "REPEAT",
  "UNTIL",
  "WHILE",
  "THEN_WHILE",
  "INIT",
  "END",
  "PROCESS",
  "INSERT"
  };
}

