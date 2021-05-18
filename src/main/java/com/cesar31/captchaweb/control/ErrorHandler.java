package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Token;
import com.cesar31.captchaweb.parser.CaptchaParser;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ErrorHandler {

    private CaptchaParser parser;
    private HashMap<String, String> gram;

    public ErrorHandler(CaptchaParser parser) {
        this.parser = parser;
        initGram();
    }

    public void getErrors(Token token, String type, List<String> expected) {
        String typeError = (type.equals("ERROR")) ? "LEXICO" : "SINTACTICO";
        String lexema = (type.equals("EOF")) ? "Fin de entrada" : token.getValue();

        Err e = new Err(token.getLine(), token.getColumn(), typeError, lexema);
        String description = typeError.equals("LEXICO") ? "La cadena no se reconoce en el lenguaje." : "";
        description += "Se encontro " + lexema + ". Se esperaba: ";

        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals("error")) {
                description += "\'" + getValue(expected.get(i)) + "\'";
                if (i == expected.size() - 1) {
                    description += ".";
                } else {
                    description += ", ";
                }
            }
        }
        e.setDescription(description);
        this.parser.getErrors().add(e);
    }

    private void initGram() {
        this.gram = new HashMap<>();
        put("GCIC", "C_GCIC");
        put("HEAD", "C_HEAD");
        put("TITLE", "C_TITLE");
        put("LINK", "C_LINK");
        put("BODY", "C_BODY");
        put("SPAM", "C_SPAM");
        put("INPUT", "C_INPUT");
        put("TXTAREA", "C_TEXTAREA");
        put("SELECT", "C_SELECT");
        put("OPTION", "C_OPTION");
        put("DIV", "C_DIV");
        put("IMG", "C_IMG");
        put("BR", "C_BR");
        put("BUTTON", "C_BUTTON");
        put("H1", "C_H1");
        put("PARAGRAPH", "C_P");
        put("SCRIPT", "C_SCRIPTING");
        put("HREF", "href");
        put("BCKGRND", "background");
        put("COLOR", "color");
        put("FONTS", "font-size");
        put("FONTF", "font-family");
        put("ALIGN", "text-align");
        put("TYPE", "type");
        put("ID", "id");
        put("NAME", "name");
        put("COLS", "cols");
        put("ROWS", "rows");
        put("CLASS", "class");
        put("SRC", "src");
        put("WIDTH", "width");
        put("HEIGHT", "height");
        put("ALT", "alt");
        put("CLICK", "onclick");
        put("IF", "IF");
        put("THEN", "THEN");
        put("ELSE", "ELSE");
        put("REPEAT", "REPEAT");
        put("UNTIL", "HUNTIL");
        put("THENWHILE", "THEN_WHILE");
        put("INIT", "INIT");
        put("END", "END");
        put("INT", "integer");
        put("DEC", "decimal");
        put("BOOL", "booleano");
        put("TRUE", "true");
        put("FALSE", "false");
        put("CHR", "char");
        put("QS", "Comilla simple(')");
        put("STR", "string");
        put("ASC", "ASC");
        put("DESC", "DESC");
        put("LETPAR", "LETPAR_NUM");
        put("LETIMPAR", "LETIMPAR_NUM");
        put("REVERSE", "REVERSE");
        put("RANDOM_C", "CARACTER_ALEATORIO");
        put("RANDOM_N", "NUM_ALEATORIO");
        put("ALERT", "ALERT_INFO");
        put("EXIT", "EXIT");
        put("REDIRECT", "REDIRECT");
        put("GET", "getElementById");
        put("INSERT", "INSERT");
        put("GLOBAL", "@global");
        put("ON_LOAD", "ON_LOAD");
        put("CHAR", "caracter entre comillas simples");
        put("FONTF_VALUE", "Courier, Verdana, Arial, Geneva, sans-serif");
        put("ALIGN_VALUE", "left, right, center, justify");
        put("COLOUR", "Color en hexadecimal o black, olive, teal, red, blue, maroon, nany, gray, lime, fuchsia, green, white, purple, silver, yellow, aqua");
        put("TYPE_VALUE", "text, number, radio, checkbox");
        put("CLASS_VALUE", "row, col");
        put("INTQ", "Numero entero en comillas");
        put("PIXEL", "Medida en pixeles");
        put("PRCNTG", "Medida en porcentaje");
        put("ONCLICK", "Nombre de proceso");
        put("ID_V", "Identificador para variable");
        put("ID_", "Identificador para etiquetas entre comillas dobles");
        put("ID_2", "Identificador para etiquetas entre comillas simples");
        put("URL", "Direccion web o url");
        put("STRING", "Cadena o string entre comillas");
        put("INTEGER", "Numero entero(4 bytes maximo)");
        put("DECIMAL", "Numero decimal(4 cifras decimales maximo)");
        put("SMALLER", "mayor que <");
        put("GREATER", "menor que >");
        put("LBRACE", "Llave de apertura {");
        put("RBRACE", "Llave de cierre }");
        put("LBRACKET", "Corchete de apertura [");
        put("RBRACKET", "Corchete de cierre ]");
        put("COLON", "Dos puntos :");
        put("SEMI", "Punto y coma ;");
        put("EQUAL", "Signo igual =");
        put("EQEQ", "Signo igual-igual ==");
        put("NEQ", "Signo no-igual !=");
        put("GRTREQ", "Mayor o igual que >=");
        put("SMLLREQ", "Menor o igual que <=");
        put("OR", "Operador logico or ||");
        put("AND", "Operador logico and &&");
        put("NOT", "Operador logico negacion !");
        put("PLUS", "Signo mas +");
        put("MINUS", "Signo menos -");
        put("TIMES", "Signo de multiplicación *");
        put("DIVIDE", "Signo de división /");
        put("LPAREN", "Parentesis de apertura (");
        put("RPAREN", "Parentesis de cierre )");
        put("COMMA", "Coma ,");
        put("IN", "Conjunto de simbolos especiales o caracteres alfanumericos");
        put("", "");
        put("", "");
        put("EOF", "Fin de entrada");

        put("ERROR", "Caracter que no pertenece al lenguaje");

    }

    private String getValue(String key) {
        return this.gram.containsKey(key) ? gram.get(key) : key;
    }

    private void put(String key, String value) {
        gram.put(key, value);
    }
}
