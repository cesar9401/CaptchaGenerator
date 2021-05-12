package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.AST;
import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.Component;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.Param;
import static com.cesar31.captchaweb.model.Param.*;
import com.cesar31.captchaweb.model.Parameter;
import com.cesar31.captchaweb.model.SymbolTable;
import com.cesar31.captchaweb.model.Tag;
import com.cesar31.captchaweb.model.Token;
import com.cesar31.captchaweb.parser.CaptchaLex;
import com.cesar31.captchaweb.parser.CaptchaParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ParserControl {

    private String path;
    // private HttpServletRequest request;
    private String link;

    private String source;

    private List<Err> errors = new ArrayList<>();
    private Captcha captcha;
    private HashMap<String, AST> scripts;

    private String title;
    private String background;
    private static String style;

    public ParserControl() {
        this.errors = new ArrayList<>();
    }

    public ParserControl(String source, String path) {
        this();
        this.source = source;
        this.path = path;
    }

    public void parseSourceCode() {
        CaptchaLex lex = new CaptchaLex(new StringReader(source));
        CaptchaParser parser = new CaptchaParser(lex);
        try {
            this.captcha = (Captcha) parser.parse().value;
            this.scripts = parser.getScripts();
            this.errors = parser.getErrors();
        } catch (Exception ex) {
            this.errors = parser.getErrors();
            ex.printStackTrace(System.out);
        } finally {
            checkSource();
        }
    }

    /**
     * Revisar Errores y AST's
     */
//    private void checkSource() {
//        /* Archivo parseado con exito */
//        if (this.errors.isEmpty()) {
//
//            /* Revisar posibles errores en AST's */
//            this.scripts.forEach((s, ast) -> {
//                AstOperation operation = new AstOperation();
//                SymbolTable table = new SymbolTable();
//
//                for (Instruction i : ast.getInstructions()) {
//                    i.test(table, operation);
//                }
//
//                /* Agregar posibles errores */
//                if (!operation.getErrors().isEmpty()) {
//                    this.errors.addAll(operation.getErrors());
//                }
//            });
//
//            if (this.errors.isEmpty()) {
//                /* nombre archivo */
//                String name = captcha.getParams().get(Param.ID).getValue();
//                name += ".gcic";
//
//                DBHandler db = new DBHandler();
//
//                /* Verificar si id esta disponible */
//                if (!db.getList(path + "script/").contains(name)) {
//                    /* json */
//                    String json = getJson(captcha);
//
//                    db.writeFile(path + name, json);
//
//                    /* original */
//                    db.writeFile(path + "script/" + name, source);
//
//                    /* Captcha guardado, mostrar enlace */
//                    this.link = "http://localhost:8080/CaptchaGenerator/CaptchaMain?id=" + name;
//                } else {
//                    /* Agregar error por id ocupado */
//                    Token t = captcha.getParams().get(Param.ID).getToken();
//                    Err e = new Err(t.getLine(), t.getColumn(), "SEMANTICO", captcha.getParams().get(Param.ID).getValue());
//                    e.setDescription("El id que desea utilizar no esta disponible, intente con otro");
//                    this.errors.add(e);
//                }
//            }
//        }
//    }
    /**
     * Si no hay errores, almacenar en db
     */
    private void checkSource() {
        if (this.captcha != null) {
            Parameter id = this.captcha.getParams().get(Param.ID);

            if (id != null) {
                DBHandler db = new DBHandler();

                String name = id.getValue() + ".gcic";
                if (!db.getList(path + "script/").contains(name)) {
                    if (this.errors.isEmpty()) {
                        /* No hay errores, archivos para almacenamiento */

                        // archivo json
                        String json = getJson(captcha);
                        db.writeFile(path + name, json);

                        /* original */
                        db.writeFile(path + "script/" + name, source);

                        /* Captcha guardado, mostrar enlace */
                        this.link = "http://localhost:8080/CaptchaGenerator/CaptchaMain?id=" + name;
                    }
                } else {
                    Token t = captcha.getParams().get(Param.ID).getToken();
                    Err e = new Err(t.getLine(), t.getColumn(), "SEMANTICO", captcha.getParams().get(Param.ID).getValue());
                    e.setDescription("El id que desea utilizar no esta disponible, intente con otro.");
                    this.errors.add(e);
                }
            }
        }
    }

    private String getJson(Captcha c) {
        String json = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(c);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace(System.out);
        }

        return json;
    }

    public String getHtml(Captcha captcha) {
        for (Component c : captcha.getHead().getChildren()) {
            if (c.getTag() == Tag.TITLE) {
                this.title = c.getContent();
                break;
            }
        }

        this.background = (captcha.getBody().getParams().get(BACKGROUND) != null) ? captcha.getBody().getParams().get(BACKGROUND).getValue() : "#5DADE2";

        String html = "";

        for (Component c : captcha.getBody().getChildren()) {
            html += "<div class='row'>\n<div class='col text-center m-2'>\n" + getChilds(c) + "\n</div>\n</div>\n";
        }

        return html;
    }

    public static String getChilds(Component c) {
        switch (c.getTag()) {
            case H1:
                return "<h1 " + getStyles(c) + " id='" + param(c, ID) + "'> " + c.getContent() + "</h1>";
            case P:
                return "<p " + getStyles(c) + " id='" + param(c, ID) + "'> " + c.getContent() + "</p>";

            case SPAN:
                return "<div " + getStyles(c) + " id='" + param(c, ID) + "'><span>" + c.getContent() + "</span></div>";

            case BUTTON:
                return "<button class='btn btn-primary btn-lg' " + getStyles(c) + " id='" + param(c, ID) + "' type='submit' name='action' value='" + param(c, ONCLICK) + "'> " + c.getContent() + "</button>";

            case BR:
                return "<br>";

            case IMG:
                return "<img class='text-center' src='" + param(c, SRC) + "' id='" + param(c, ID) + "' width='" + param(c, WIDTH) + "' height='" + param(c, HEIGHT) + "' alt='" + param(c, ALT) + "' />";

            case TEXTAREA:
                return "<textarea " + getStyles(c) + " id='" + param(c, ID) + "' name='" + param(c, ID) + "'> " + "</textarea>";

            case DIV:
                return "<div " + getStyles(c) + " id='" + param(c, ID) + "'>" + getChildsParent(c) + "</div>";

            case INPUT:
                return "<input " + getStyles(c) + " type='" + param(c, TYPE) + "' id='" + param(c, ID) + "' name='" + param(c, ID) + "'/>";

            case SELECT:
                return "<select class='form-select form-select-lg' " + getStyles(c) + " id='" + param(c, ID) + "' name='" + param(c, ID) + "'>" + getChildsParent(c) + "</select>";

            case OPTION:
                return "<option value='" + c.getContent() + "'> " + c.getContent() + "</option>";

        }
        return "";
    }

    private static String getChildsParent(Component p) {
        String html = "";
        for (Component c : p.getChildren()) {
            html += getChilds(c);
        }
        return html;
    }

    private static String getStyles(Component c) {
        style = "style='";

        c.getParams().forEach((param, parameter) -> {
            switch (param) {
                case BACKGROUND:
                    style += "background-color: " + parameter.getValue() + "; ";
                    break;
                case COLOR:
                    style += "color: " + parameter.getValue() + "; ";
                    break;
                case FONT_FAMILY:
                    style += "font-family: " + parameter.getValue() + "; ";
                    break;
                case FONT_SIZE:
                    style += "font-size: " + parameter.getValue() + "; ";
                    break;
                case TEXT_ALIGN:
                    style += "text-align: " + parameter.getValue() + "; ";
                    break;
            }
        });

        style += "'";
        return style;
    }

    public List<Captcha> getCaptchas(String path) {
        ObjectMapper mapper = new ObjectMapper();

        List<Captcha> captchas = new ArrayList<>();
        DBHandler db = new DBHandler();
        List<String> list = db.getList(path);

        for (String s : list) {
            if (!s.equals("script")) {
                try {
                    Captcha tmp = mapper.readValue(new File(path + s), Captcha.class);
                    captchas.add(tmp);
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }

        return captchas;
    }

    private static String param(Component c, Param p) {
        return (c.getParams().get(p) != null) ? c.getParams().get(p).getValue() : "";
    }

    public List<Err> getErrors() {
        return errors;
    }

    public Captcha getCaptcha() {
        return captcha;
    }

    public HashMap<String, AST> getScripts() {
        return scripts;
    }

    public String getTitle() {
        return title;
    }

    public String getBackground() {
        return background;
    }

    public String getLink() {
        return link;
    }
}
