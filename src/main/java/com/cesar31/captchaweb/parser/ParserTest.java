package com.cesar31.captchaweb.parser;

import com.cesar31.captchaweb.control.DBHandler;
import java.io.IOException;
import java.io.StringReader;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class ParserTest {

    public static void main(String[] args) {
        
        String path = "html.gcic";
        
        boolean val1 = true && false || !false && true && !(false && true || false);
        boolean val2 = !(3 > 2 || 75 != 3) && !false;
        boolean val3 = "cadena1" == "cadena1";
        System.out.println(val3);

        DBHandler db = new DBHandler();
        String input = db.readData(path);
        
        //System.out.println(input);
        System.out.println("\n");

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
