package com.cesar31.captchaweb.parser;

import com.cesar31.captchaweb.control.DBHandler;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.ComponentParent;
import com.cesar31.captchaweb.model.Tag;
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
        boolean val2 = !(3 > 2 || 75 > 3) && !false;
        // System.out.println("val2 = " + val2);

        boolean val = 3 + 1 > 2;
        // System.out.println("val = " + val);
        boolean bool1 = 3 + 1 > 2 * 2 - 1 && !(4 > 2 + 10) && !false;
        // System.out.println("bool1 = " + bool1);

        System.out.println("\n");

        DBHandler db = new DBHandler();
        String input = db.readData(path);

        System.out.println(input);
        System.out.println("\n");

        // getTokens(input);
        CaptchaLex lex = new CaptchaLex(new StringReader(input));
        CaptchaParser parser = new CaptchaParser(lex);
        Captcha c = null;
        try {
            c = (Captcha) parser.parse().value;
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

        if (c != null) {
            checkCaptcha(c);
        }
    }

    public static void checkCaptcha(Captcha c) {
        c.getHead().getChildren().forEach(h -> {
            System.out.println(h.getTag() + " <" + h.getParams() + ">");
        });

        c.getBody().getChildren().forEach(b -> {
            System.out.println(b.getTag() + "<" + b.getParams() + ">");
            if (b.getTag() == Tag.DIV) {
                getChildren((ComponentParent) b);
            }
        });
    }

    public static void getChildren(ComponentParent p) {
        p.getChildren().forEach(b -> {
            System.out.println(b.getTag() + "<" + b.getParams() + ">");
            if (b.getTag() == Tag.DIV) {
                getChildren((ComponentParent) b);
            }
        });
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
