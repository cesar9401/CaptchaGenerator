package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Captcha;
import com.cesar31.captchaweb.model.Component;
import com.cesar31.captchaweb.model.ComponentParent;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Param;
import static com.cesar31.captchaweb.model.Tag.*;
import static com.cesar31.captchaweb.model.Param.*;
import com.cesar31.captchaweb.model.Parameter;
import com.cesar31.captchaweb.model.Tag;
import com.cesar31.captchaweb.model.Token;
import com.cesar31.captchaweb.parser.CaptchaParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class BuildTag {

    private CaptchaParser parser;

    private HashMap<Param, Parameter> params;
    private HashMap<Param, Parameter> tmp;
    private HashMap<Param, Boolean> tagParam;
    private List<Err> errors;

    public BuildTag() {
        this.params = new HashMap<>();
        this.tmp = new HashMap<>();
        this.tagParam = new HashMap<>();
        this.errors = new ArrayList<>();
    }

    public BuildTag(CaptchaParser parser) {
        this();
        this.parser = parser;
    }

    public Captcha makeCaptcha(ComponentParent head, ComponentParent body) {
        Captcha c = new Captcha();
        c.setHead(head);
        c.setBody(body);
        return c;
    }

    /**
     * Crear etiqueta
     *
     * @param tag
     * @param map
     * @param tokens
     * @return
     */
    public Component makeTag(Tag tag, HashMap<Param, Parameter> map, List<Token> tokens) {
        Component c = new Component(tag);
        if (map != null) {
            c.setParams(map);
        }

        if (tokens != null) {
            String content = getContent(tokens);
            c.setContent(content);
        }

        return c;
    }

    /**
     * Crear etiqueta padre
     *
     * @param tag
     * @param map
     * @param children
     * @return
     */
    public ComponentParent makeTagParent(Tag tag, HashMap<Param, Parameter> map, List<Component> children) {
        ComponentParent c = new ComponentParent();
        c.setTag(tag);

        if (map != null) {
            c.setParams(map);
        }

        if (children != null) {
            if (tag == HEAD) {
                // Revisar etiquetas head
                HashMap<Tag, Component> comps = checkHead(children);
                if (comps != null) {
                    c.getChildren().add(comps.get(TITLE));
                    c.getChildren().add(comps.get(LINK));
                }
            } else {
                c.setChildren(children);
            }
        }

        return c;
    }

    private HashMap<Tag, Component> checkHead(List<Component> children) {
        List<Err> errs = new ArrayList<>();
        HashMap<Tag, Component> head = new HashMap<>();

        for (Component c : children) {
            if (c.getTag() == TITLE) {
                if (!head.containsKey(TITLE)) {
                    head.put(TITLE, c);
                }
            }

            if (c.getTag() == LINK) {
                if (!head.containsKey(LINK)) {
                    head.put(LINK, c);
                }
            }
        }

        if (!head.containsKey(TITLE)) {
            // Error, agregar titulo
            Err e = new Err(0, 0, "SEMANTICO");
            e.setDescription("Se debe agregar la etiqueta C_TITLE en C_HEAD");
            errs.add(e);
        } else {
            children.remove(head.get(TITLE));
        }

        if (!head.containsKey(LINK)) {
            // Error, agregar link
            Err e = new Err(0, 0, "SEMANTICO");
            e.setDescription("Se debe agregar la etiqueta C_LINK en C_HEAD");
            errs.add(e);
        } else {
            children.remove(head.get(LINK));
        }

        if (!children.isEmpty()) {
            for (Component c : children) {
                Err e = new Err(0, 0, "SEMANTICO");
                String description = "La etiqueta C_" + c.getTag() + " ya ha sido agregada, por favor verifique";
                e.setDescription(description);
                errs.add(e);
            }
        }

        if (!errs.isEmpty()) {
            this.parser.getErrors().addAll(errs);
            return null;
        }

        if (head.size() == 2) {
            return head;
        }

        return null;
    }

    /**
     * Parametros para las etiquetas
     *
     * @param token
     * @param tag
     * @param list
     * @return
     */
    public HashMap<Param, Parameter> getParameters(Token token, Tag tag, List<Parameter> list) {
        this.params = new HashMap<>();
        this.tmp = new HashMap<>();
        this.tagParam = new HashMap<>();
        this.errors = new ArrayList<>();

        this.tagParam = getParam(tag);

        // Agregar parametros a hashmap tmp
        list.forEach((Parameter p) -> {
            if (!this.tmp.containsKey(p.getType())) {
                this.tmp.put(p.getType(), p);
            } else {
                // Si es repetido y pertenece
                if (this.tagParam.get(p.getType())) {
                    // Parametro repetido, crear error
                    Err e = new Err(token.getLine(), token.getColumn(), "SEMANTICO");
                    String description = "El parametro " + p.getType().toString().toLowerCase() + "ya ha sido agregado.";
                    e.setDescription(description);
                    this.errors.add(e);
                }
            }
        });

        //setParams(token, tag);
        tagParam.forEach((Param p, Boolean b) -> {
            if (b) {
                if (tmp.containsKey(p)) {
                    addParam(tmp.remove(p));
                } else {
                    // Agregar parametro por defecto
                }
            }
        });

        if (!tmp.isEmpty()) {
            // Crear error por parametros que no van
            tmp.forEach((Param p, Parameter u) -> {
                Err e = new Err(token.getLine(), token.getColumn(), "SEMANTICO");
                String description = "El parametro: " + p.toString().toLowerCase() + "no se debe incluir en la etiqueta: C_" + tag;
                e.setDescription(description);
                this.errors.add(e);
            });
        }

//        this.params.forEach((Param p, Parameter t) -> {
//            System.out.println(tag + " -> " + t);
//        });

        this.parser.getErrors().addAll(errors);

        return this.params;
    }

    /**
     * Contenido para etiquetas p, h1, span
     *
     * @param tokens
     * @return
     */
    private String getContent(List<Token> tokens) {
        String content = "";
        for (Token t : tokens) {
            content += t.getValue() + " ";
        }

        return content;
    }

    private void addParam(Parameter p) {
        this.params.put(p.getType(), p);
    }

    private HashMap<Param, Boolean> getParam(Tag t) {
        HashMap<Param, Boolean> map = new HashMap<>();
        for (Param p : Param.values()) {
            map.put(p, false);
        }

        switch (t) {
            case GCIC:
                map.replace(NAME, true);
                map.replace(ID, true);
                break;
            case BODY:
                map.replace(BACKGROUND, true);
                break;
            case LINK:
                map.replace(HREF, true);
            case P:
            case H1:
            case SELECT:
            case SPAN:
                usuallyParams(map);
                break;
            case IMG:
                map.replace(SRC, true);
                map.replace(WIDTH, true);
                map.replace(HEIGHT, true);
                map.replace(ALT, true);
                map.replace(ID, true);
                break;
            case BUTTON:
                usuallyParams(map);
                map.replace(ONCLICK, true);
                break;
            case DIV:
                usuallyParams(map);
                map.replace(CLASS, true);
                map.replace(BACKGROUND, true);
                break;
            case TEXTAREA:
                usuallyParams(map);
                map.replace(COLOR, false);
                map.replace(COLS, true);
                map.replace(ROWS, true);
                break;
            case INPUT:
                usuallyParams(map);
                map.replace(TYPE, true);
                break;
        }

        return map;
    }

    private void usuallyParams(HashMap<Param, Boolean> map) {
        map.replace(FONT_SIZE, true);
        map.replace(FONT_FAMILY, true);
        map.replace(TEXT_ALIGN, true);
        map.replace(ID, true);
        map.replace(COLOR, true);
    }
}
