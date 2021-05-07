package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.Component;
import com.cesar31.captchaweb.model.ComponentParent;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.Param;
import static com.cesar31.captchaweb.model.Param.*;
import com.cesar31.captchaweb.model.Tag;
import com.cesar31.captchaweb.parser.CaptchaLex;
import com.cesar31.captchaweb.parser.CaptchaParser;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class ParserControl {

    private String source;
    private List<Err> errors = new ArrayList<>();
    private Captcha captcha;
    private LinkedList<Instruction> AST;

    private String title;
    private String background;
    private String style;

    public ParserControl() {
        this.errors = new ArrayList<>();
        this.AST = new LinkedList<>();
    }

    public ParserControl(String source) {
        this();
        //this.source = new String(source.getBytes("ISO-8859-1"), "UTF-8");
        this.source = source;
    }

    public void parseSourceCode() {
        CaptchaLex lex = new CaptchaLex(new StringReader(source));
        CaptchaParser parser = new CaptchaParser(lex);
        try {
            this.captcha = (Captcha) parser.parse().value;
            this.AST = parser.getAST();
            this.errors = parser.getErrors();
        } catch (Exception ex) {
            this.errors = parser.getErrors();
            ex.printStackTrace(System.out);
        }
    }

    public String getHtml(Captcha captcha) {
        for(Component c : captcha.getHead().getChildren()) {
            if(c.getTag() == Tag.TITLE) {
                this.title = c.getContent();
                break;
            }
        }
        
        this.background = (captcha.getBody().getParams().get(BACKGROUND) != null) ? captcha.getBody().getParams().get(BACKGROUND).getValue() : "#5DADE2";
        
        String html = "";

        for (Component c : captcha.getBody().getChildren()) {
            html += "<div class='row'>\n<div class='col text-center m-2'>\n" + getChilds(c) + "</div>\n</div>\n";
        }

        return html;
    }

    private String getChilds(Component c) {
        switch (c.getTag()) {
            case H1:
                return "<h1 " + getStyles(c) + " id='" + param(c, ID) + "'> " + c.getContent() + "</h1>\n";
            case P:
                return "<p " + getStyles(c) + " id='" + param(c, ID) + "'> " + c.getContent() + "</p>\n";

            case SPAN:
                return "<div " + getStyles(c) + " id='" + param(c, ID) + "'>\n<span>" + c.getContent() + "<span>\n</div>\n";

            case BUTTON:
                return "<button class='btn btn-primary btn-lg' " + getStyles(c) + " id='" + param(c, ID) + "' type='submit' name='action' value='" + param(c, ONCLICK) + "'> " + c.getContent() + "</button>\n";

            case BR:
                return "<br>\n";

            case IMG:
                return "<img class='text-center' src='" + param(c, SRC) + "' id='" + param(c, ID) + "' width='" + param(c, WIDTH) + "' height='" + param(c, HEIGHT) + "' alt='" + param(c, ALT) + "' />\n";

            case TEXTAREA:
                return "<textarea " + getStyles(c) + " id='" + param(c, ID) + "'> " + "</textarea>\n";

            case DIV:
                return "<div " + getStyles(c) + " id='" + param(c, ID) + "'>\n" + getChildsParent((ComponentParent) c) + "</div>\n";

            case INPUT:
                return "<input " + getStyles(c) + " type='" + param(c, TYPE) + "' id='" + param(c, ID) + "'/>\n";

            case SELECT:
                return "<select class='form-select form-select-lg' " + getStyles(c) + " id='" + param(c, ID) + "'>\n" + getChildsParent((ComponentParent) c) + "</select>\n";

            case OPTION:
                return "<option value='" + c.getContent() + "'> " + c.getContent() + "</option>\n";

        }
        return "";
    }

    private String getChildsParent(ComponentParent p) {
        String html = "";
        for (Component c : p.getChildren()) {
            html += getChilds(c);
        }
        return html;
    }

    private String getStyles(Component c) {
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

    private String param(Component c, Param p) {
        return (c.getParams().get(p) != null) ? c.getParams().get(p).getValue() : "";
    }

    public List<Err> getErrors() {
        return errors;
    }

    public Captcha getCaptcha() {
        return captcha;
    }

    public LinkedList<Instruction> getAST() {
        return AST;
    }

    public String getTitle() {
        return title;
    }

    public String getBackground() {
        return background;
    }
}
