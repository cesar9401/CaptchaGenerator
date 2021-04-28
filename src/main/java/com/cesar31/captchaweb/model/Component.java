package com.cesar31.captchaweb.model;

import java.util.HashMap;

/**
 *
 * @author cesar31
 */
public class Component {

    private Tag tag;    
    // h1 y p
    private String content;

    private HashMap<Param, Parameter> params;

    public Component() {
        this.params = new HashMap<>();
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

    @Override
    public String toString() {
        return "Component{" + "tag=" + tag + '}';
    }
}
