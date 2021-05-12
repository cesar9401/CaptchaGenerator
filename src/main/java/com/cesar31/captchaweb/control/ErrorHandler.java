package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Token;
import com.cesar31.captchaweb.parser.CaptchaParser;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ErrorHandler {

    private CaptchaParser parser;

    public ErrorHandler(CaptchaParser parser) {
        this.parser = parser;
    }

    public void getErrors(Token token, String type, List<String> expected) {
        String typeError = (type.equals("ERROR")) ? "LEXICO" : "SINTACTICO";
        String lexema = (type.equals("EOF")) ? "Fin de entrada" : "(" + token.getType() + ")" + token.getValue();

        Err e = new Err(token.getLine(), token.getColumn(), typeError, lexema);
        String description = typeError.equals("LEXICO") ? "La cadena no se reconoce en el lenguaje." : "";
        description += "Se encontro " + lexema + ". Se esperaba: ";

        for (int i = 0; i < expected.size(); i++) {
            description += "\'" + expected.get(i) + "\'";
            if (i == expected.size() - 1) {
                description += ".";
            } else {
                description += ", ";
            }
        }
        e.setDescription(description);
        this.parser.getErrors().add(e);
    }
}
