package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Variable {

    private Var type;
    private String id;
    private boolean global;
    private String value;

    public Variable() {
    }

    public Variable(Var type, String id, String value) {
        this.type = type;
        this.id = id;
        this.value = value;
    }

    public Variable(Var type, String id, boolean global, String value) {
        this.type = type;
        this.id = id;
        this.global = global;
        this.value = value;
    }

    public Variable(Var type, String value) {
        this.type = type;
        this.value = value;
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

    @Override
    public String toString() {
        return "Variable{" + "type=" + type + ", id=" + id + ", global=" + global + ", value=" + value + '}';
    }
}
