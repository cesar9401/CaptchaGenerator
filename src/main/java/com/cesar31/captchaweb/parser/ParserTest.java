package com.cesar31.captchaweb.parser;

import com.cesar31.captchaweb.control.AstOperation;
import com.cesar31.captchaweb.control.DBHandler;
import com.cesar31.captchaweb.control.ParserControl;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.ComponentParent;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.SymbolTable;
import com.cesar31.captchaweb.model.Tag;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class ParserTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
//        List<Err> errors;
//        LinkedList<Instruction> AST = null;
//
//        String path = "html.gcic";
//        DBHandler db = new DBHandler();
//        String input = db.readData(path);
//
//        System.out.println(input);
//        System.out.println("\n");
//
//        // getTokens(input);
//        CaptchaLex lex = new CaptchaLex(new StringReader(input));
//        CaptchaParser parser = new CaptchaParser(lex);
//        Captcha c = null;
//        try {
//            c = (Captcha) parser.parse().value;
//            AST = parser.getAST();
//            errors = parser.getErrors();
//        } catch (Exception ex) {
//            errors = parser.getErrors();
//            ex.printStackTrace(System.out);
//        }
//
//        if (!errors.isEmpty()) {
//            errors.forEach(e -> {
//                System.out.println(e.toString());
//            });
//        } else if (c != null) {
//            checkCaptcha(c);
//            runAST(AST);
//        } else {
//            System.out.println("Shit!");
//        }

        getHtml();
    }

    public static void getHtml() throws UnsupportedEncodingException {
        String path = "html.gcic";
        DBHandler db = new DBHandler();
        String input = db.readData(path);

        ParserControl control = new ParserControl(input);
        control.parseSourceCode();

        if (control.getErrors().isEmpty()) {

            /* Redirigir a captcha */
            Captcha captcha = control.getCaptcha();
            
            control.getHtml(captcha);
        } else {
            /* Redirigir errores */
            control.getErrors().forEach(e -> {
                System.out.println(e);
            });
        }

    }

    public static void runAST(LinkedList<Instruction> AST) {
        AstOperation operation = new AstOperation();

        int count = 0;

        if (AST != null) {
            SymbolTable table = new SymbolTable();

            for (Instruction i : AST) {
                i.test(table, operation);
            }

            if (operation.getErrors().isEmpty()) {
                System.out.println("\nAST aparentemente limpio\n");

                SymbolTable symbol = new SymbolTable();
                AstOperation astO = new AstOperation();
                for (Instruction i : AST) {
                    i.run(symbol, astO);
                }

                if (!astO.getErrors().isEmpty()) {
                    astO.getErrors().forEach(e -> {
                        System.out.println(e.toString());
                    });
                } else {
                    symbol.forEach(v -> {
                        System.out.println(v.toString());
                    });
                }

            } else {
                System.out.println("\nErrores en AST");
                operation.getErrors().forEach(e -> {
                    System.out.println(e);
                });
            }

        } else {
            System.out.println("AST null");
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
