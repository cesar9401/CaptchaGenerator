package com.cesar31.captchaweb.parser;

import com.cesar31.captchaweb.control.DBHandler;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.ComponentParent;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Tag;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class ParserTest {

    public static void main(String[] args) {
        List<Err> errors;

        String path = "scripting.gcic";
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
            errors = parser.getErrors();
        } catch (Exception ex) {
            errors = parser.getErrors();
            ex.printStackTrace(System.out);
        }

        
        if (!errors.isEmpty()) {
            errors.forEach(e -> {
                System.out.println(e.toString());
            });
        } else if (c != null) {
            checkCaptcha(c);
        } else {
            System.out.println("Shit!");
        }

    }

    public static void checkCaptcha(Captcha c) {
        System.out.println(c.getTag() + "<" + c.getParams() + ">");
        
        c.getHead().getChildren().forEach(h -> {
            System.out.println(h.getTag() + " <" + h.getParams() + ">" + " \"" + h.getContent() + "\"");
        });

        c.getBody().getChildren().forEach(b -> {
            System.out.println(b.getTag() + "<" + b.getParams() + ">" + " \"" + b.getContent() + "\"");
            if (b.getTag() == Tag.DIV) {
                getChildren((ComponentParent) b);
            }
        });
    }

    public static void getChildren(ComponentParent p) {
        p.getChildren().forEach(b -> {
            System.out.println(b.getTag() + "<" + b.getParams() + ">" + " \"" + b.getContent() + "\"");
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
