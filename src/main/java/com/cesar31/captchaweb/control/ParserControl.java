package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.Component;
import com.cesar31.captchaweb.model.ComponentParent;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Instruction;
import com.cesar31.captchaweb.model.Param;
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

    public ParserControl() {
        this.errors = new ArrayList<>();
        this.AST = new LinkedList<>();
    }

    public ParserControl(String source) throws UnsupportedEncodingException {
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
        String html = "";

        for (Component c : captcha.getBody().getChildren()) {
            html += getChilds(c);
        }

        System.out.println(html);
        return html;
    }

    private String getChilds(Component c) {
        switch (c.getTag()) {
            case H1:
                return "<h1 style=\"color: "+ c.getParams().get(Param.COLOR).getValue() +"; \"> " + c.getContent() + "</h1>\n";
            case P:
                return "<p> " + c.getContent() + "</p>\n";

            case SPAN:
                return "<span> " + c.getContent() + "</span>\n";

            case BUTTON:
                return "<button> " + c.getContent() + "</button>\n";
                
            case BR:
                return "<br>\n";
                
            case IMG:
                return "<img src=\"" + c.getParams().get(Param.SRC).getValue() + "\" />\n";
                
            case TEXTAREA:
                return "<textarea> write something here </textarea>";
                
            case DIV:
                return "<div>\n" + getChildsParent((ComponentParent) c) + "</div>\n";
        }
        return "";
    }
    
    private String getChildsParent(ComponentParent p) {
        String html = "";
        for(Component c : p.getChildren()) {
            html += getChilds(c);
        }
        return html;
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
}
