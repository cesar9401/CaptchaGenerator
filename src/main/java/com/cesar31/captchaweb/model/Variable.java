package com.cesar31.captchaweb.model;

import java.io.Serializable;

/**
 *
 * @author cesar31
 */
public class Variable implements Serializable {

    private Var type;
    private String id;
    private boolean global;
    private String value;
    private String scope;
    private Integer tried;
    private Integer line;

    private Token token;

    public Variable() {
        this.tried = 1;
    }

    public Variable(Var type, String id, String value) {
        this();
        this.type = type;
        this.id = id;
        this.value = value;
    }

    public Variable(Var type, String id, boolean global, String value) {
        this();
        this.type = type;
        this.id = id;
        this.global = global;
        this.value = value;
    }

    public Variable(Var type, String value) {
        this();
        this.type = type;
        this.value = value;
    }

    public Variable(Var type, Token token) {
        this();
        this.type = type;
        this.token = token;

        this.value = token.getValue();
    }

    public Variable(String id) {
        this.id = id;
    }

    public Var getType() {
        return type;
    }

    public void setType(Var type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Integer getTried() {
        return tried;
    }

    public void setTried(Integer tried) {
        this.tried = tried;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Variable{" + "type=" + type + ", id=" + id + ", global=" + global + ", value=" + value + ", scope=" + scope + ", tried=" + tried + '}';
    }
}
