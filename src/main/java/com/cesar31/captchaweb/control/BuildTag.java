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

    private HashMap<String, Parameter> ids;

    private HashMap<Param, Parameter> s;

    public BuildTag() {
        this.params = new HashMap<>();
        this.tmp = new HashMap<>();
        this.tagParam = new HashMap<>();
        this.errors = new ArrayList<>();
        this.ids = new HashMap<>();

        this.setStandardParam();
    }

    public BuildTag(CaptchaParser parser) {
        this();
        this.parser = parser;
    }

    public Captcha makeCaptcha(Tag tag, HashMap<Param, Parameter> params, Component head, Component body) {
        Captcha c = new Captcha();
        c.setTag(tag);

        if (params != null) {
            c.setParams(params);
        }

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

    public Component makeDivInsteadScript(int script) {
        Component c = new Component();
        c.setTag(Tag.DIV);
        c.getParams().put(ID, new Parameter(ID, "__script__" + script + "__"));

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
    public Component makeTagParent(Tag tag, HashMap<Param, Parameter> map, List<Component> children) {
        Component c = new Component();
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
            Err e = new Err(0, 0, "SEMANTICO", "C_TITLE");
            e.setDescription("Se debe agregar la etiqueta C_TITLE en C_HEAD");
            errs.add(e);
        } else {
            children.remove(head.get(TITLE));
        }

        if (!head.containsKey(LINK)) {
            // Error, agregar link
            Err e = new Err(0, 0, "SEMANTICO", "C_HEAD");
            e.setDescription("Se debe agregar la etiqueta C_LINK en C_HEAD");
            errs.add(e);
        } else {
            children.remove(head.get(LINK));
        }

        if (!children.isEmpty()) {
            for (Component c : children) {
                Err e = new Err(0, 0, "SEMANTICO", "C" + c.getTag());
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
                    Err e = new Err(token.getLine(), token.getColumn(), "SEMANTICO", p.getType().toString().toLowerCase());
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
                    if (p == ID) {
                        /* Verificar id repetidos */
                        if (!this.ids.containsKey(tmp.get(p).getValue())) {
                            this.ids.put(tmp.get(p).getValue(), tmp.get(p));
                        } else {
                            /* Crear error por id repetido */
                            Token t = this.tmp.get(p).getToken();
                            Err e = new Err(t.getLine(), t.getColumn(), "SEMANTICO", t.getValue());
                            String description = "El id(" + t.getValue() + ") que desea utilizar en la etiqueta " + tag + ", no esta disponible, intente con otro.";
                            e.setDescription(description);
                            this.errors.add(e);
                        }
                    }

                    addParam(tmp.remove(p));
                } else {
                    // Agregar parametro por defecto
                    if (s.get(p) != null) {
                        addParam(s.get(p));
                    }
                }
            }
        });

        if (tag == Tag.GCIC) {
            if (!this.params.containsKey(Param.ID)) {
                /* crear error, etiqueta GCIC debe llevar id */
                Err e = new Err(token.getLine(), token.getColumn(), "SEMANTICO", "");
                e.setDescription("En la etiqueta " + Tag.GCIC + " el parametro id es obligatorio para identificar al captcha.");
                this.errors.add(e);
            }
        }

        if (!tmp.isEmpty()) {
            // Crear error por parametros que no van
            tmp.forEach((Param p, Parameter u) -> {
                Err e = new Err(token.getLine(), token.getColumn(), "SEMANTICO", p.toString().toLowerCase());
                String description = "El parametro: " + p.toString().toLowerCase() + ", no se debe incluir en la etiqueta: C_" + tag;
                e.setDescription(description);
                this.errors.add(e);
            });
        }

        this.parser.getErrors().addAll(errors);

        return this.params;
    }

    /**
     * Contenido para etiquetas p, h1, span
     *
     * @param tokens
     * @return
     */
    public String getContent(List<Token> tokens) {
        String content = "";
        for (Token t : tokens) {
            content += t.getValue() + " ";
        }

        return content;
    }

    /**
     * Agregar parametros
     *
     * @param p
     */
    private void addParam(Parameter p) {
        this.params.put(p.getType(), p);
    }

    /**
     * Parametros segun etiqueta
     *
     * @param t
     * @return
     */
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
                map.replace(BACKGROUND, true);
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

    /**
     * Parametros usuales
     *
     * @param map
     */
    private void usuallyParams(HashMap<Param, Boolean> map) {
        map.replace(FONT_SIZE, true);
        map.replace(FONT_FAMILY, true);
        map.replace(TEXT_ALIGN, true);
        map.replace(ID, true);
        map.replace(COLOR, true);
    }

    /**
     * Parametros por defecto
     */
    private void setStandardParam() {
        this.s = new HashMap<>();
        s.put(ALT, new Parameter(ALT, "Mi_imagen"));
        s.put(BACKGROUND, new Parameter(BACKGROUND, "#7DCEA0"));
        s.put(CLASS, new Parameter(CLASS, "row"));
        s.put(COLOR, new Parameter(COLOR, "#8E44AD"));
        s.put(COLS, new Parameter(COLS, "10"));
        s.put(FONT_FAMILY, new Parameter(FONT_FAMILY, "sans-serif"));
        //s.put(FONT_SIZE, new Parameter(FONT_SIZE, "24px"));
        s.put(HEIGHT, new Parameter(HEIGHT, "500px"));
        s.put(HREF, new Parameter(HREF, "https://www.google.com/"));
        //s.put(ID, new Parameter(ID, "id1"));
        s.put(NAME, new Parameter(NAME, "Mi Captcha GCIC"));
        //s.put(ONCLICK, new Parameter(ONCLICK, "ON_LOAD"));
        s.put(ROWS, new Parameter(ROWS, "20"));
        s.put(SRC, new Parameter(SRC, "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/bojack-horseman-final-1571059533.jpg?crop=0.927xw:0.925xh;0.0538xw,0.0754xh&resize=2048:*"));
        s.put(TEXT_ALIGN, new Parameter(TEXT_ALIGN, "center"));
        s.put(TYPE, new Parameter(TYPE, "text"));
        s.put(WIDTH, new Parameter(WIDTH, "500px"));
    }
}
