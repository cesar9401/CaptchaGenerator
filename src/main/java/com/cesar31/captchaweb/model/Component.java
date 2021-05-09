package com.cesar31.captchaweb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Component {

    private Tag tag;
    // h1, p y span
    private String content;

    // Etiquetas con parametros
    private HashMap<Param, Parameter> params;

    /* hijos */
    // Para DIV y BODY y HEAD, SELECT
    private List<Component> children;

    public Component() {
        this.params = new HashMap<>();
        this.children = new ArrayList<>();
    }

    public Component(Tag tag) {
        this();
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HashMap<Param, Parameter> getParams() {
        return params;
    }

    public void setParams(HashMap<Param, Parameter> params) {
        this.params = params;
    }

    public List<Component> getChildren() {
        return children;
    }

    public void setChildren(List<Component> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Component{" + "tag=" + tag + '}';
    }
}
