package com.cesar31.captchaweb.parser;

import com.cesar31.captchaweb.control.AstOperation;
import com.cesar31.captchaweb.control.DBHandler;
import com.cesar31.captchaweb.control.ParserControl;
import com.cesar31.captchaweb.model.AST;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.ComponentParent;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.Param;
import com.cesar31.captchaweb.model.SymbolTable;
import com.cesar31.captchaweb.model.Tag;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java_cup.runtime.Symbol;

/**
 *
 * @author cesar31
 */
public class ParserTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        List<Err> errors;
        HashMap<String, AST> scripts = new HashMap<>();

        String path = "html.gcic";
        DBHandler db = new DBHandler();
        String input = db.readData(path);

        System.out.println(input);
        System.out.println("\n");

//        getTokens(input);
        CaptchaLex lex = new CaptchaLex(new StringReader(input));
        CaptchaParser parser = new CaptchaParser(lex);
        Captcha c = null;
        try {
            c = (Captcha) parser.parse().value;
            scripts = parser.getScripts();
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
            runAST(scripts);
        } else {
            System.out.println("Shit!");
        }

        //getCaptcha();
        // getHtml();
    }

//    public static void getHtml() throws UnsupportedEncodingException {
//        String path = "html.gcic";
//        DBHandler db = new DBHandler();
//        String input = db.readData(path);
//
//        ParserControl control = new ParserControl(input);
//        control.parseSourceCode();
//
//        if (control.getErrors().isEmpty()) {
//
//            /* Redirigir a captcha */
//            Captcha captcha = control.getCaptcha();
//
//            control.getHtml(captcha);
//        } else {
//            /* Redirigir errores */
//            control.getErrors().forEach(e -> {
//                System.out.println(e);
//            });
//        }
//    }
    public static void runAST(HashMap<String, AST> scripts) {

        if (scripts != null) {
            scripts.forEach((s, ast) -> {
                AstOperation operation = new AstOperation();
                SymbolTable table = new SymbolTable();
                
                System.out.println("\nAST -> " + ast.getName());
                for(Instruction i : ast.getInstructions()) {
                    i.test(table, operation);
                }
                
                if(!operation.getErrors().isEmpty()) {
                    System.out.println("Errores en " + ast.getName());
                    operation.getErrors().forEach(e -> {
                        System.out.println(e.toString());
                    });
                } else {
                    System.out.println("AST -> " + ast.getName() + " aparentemente limpio");
                    AstOperation o = new AstOperation();
                    SymbolTable t = new SymbolTable();
                    for(Instruction i : ast.getInstructions()) {
                        i.run(t, o);
                    }
                    
                    t.forEach(v -> {
                        System.out.println(v.toString());
                    });
                }
            });

        } else {
            System.out.println("scripts null");
        }
    }

    public static void checkCaptcha(Captcha c) {
        System.out.println("ID: " + c.getParams().get(Param.ID));
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try {

            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("captcha.gcic"), c);
            //json += mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
            //System.out.println(json);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public static void getCaptcha() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Captcha c = mapper.readValue(new File("captcha.gcic"), Captcha.class);
            System.out.println("ID: " + c.getParams().get(Param.ID));
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
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

//    private static void astToJson(AST ast) {
//        System.out.println("Nombre: " + ast.getName());
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("ast.gcic"), ast);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.out);
//        }
//    }
//    private static void getAst() {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            AST ast = mapper.readValue(new File("ast.gcic"), AST.class);
//        } catch (IOException ex) {
//            ex.printStackTrace(System.out);
//        }
//    }
}
