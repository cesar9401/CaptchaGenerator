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
        
        
        System.out.println(path.getClass().getSimpleName());
        
        Character ch2 = '/';
        System.out.println(ch2.getClass().getSimpleName());
        
        char ch1 = '?';
        int entero = (int) ch1;
        System.out.println("entero = " + entero);
        
        boolean val1 = true && false || !false && true && !(false && true || false);
        boolean val2 = !(3 > 2 || 75 > 3) && !false;
        System.out.println("val2 = " + val2);
        
        boolean val = 3 + 1 > 2;
        System.out.println("val = " + val);
        boolean bool1 = 3 + 1 > 2 * 2 -1 && !(4 > 2 + 10) && !false;
        System.out.println("bool1 = " + bool1);
        
        
        System.out.println("\n");
        
        DBHandler db = new DBHandler();
        String input = db.readData(path);
        
        System.out.println(input);
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
