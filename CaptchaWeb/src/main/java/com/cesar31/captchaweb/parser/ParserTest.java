package com.cesar31.captchaweb.parser;

import java.io.IOException;
import java.io.StringReader;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class ParserTest {

    public static void main(String[] args) {
        String input = "<C_GCIC [id= \"captcha_matematico_1\"] [name= \"Captcha Matemático 1\"]>";
        
        System.out.println(input);
        // getTokens(input);
        
        CaptchaLex lex = new CaptchaLex(new StringReader(input));
        CaptchaParser parser = new CaptchaParser(lex);
        try {
            parser.parse();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void getTokens(String input) {
        CaptchaLex lex = new CaptchaLex(new StringReader(input));
        while (true) {
            Symbol s;
            try {
                s = lex.next_token();
                if (s.sym == 0) {
                    break;
                }
                System.out.println("Fila " + s.left + " Columna: " + s.right + " Value: " + s.value + ", Token: " + s.sym);
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

}
