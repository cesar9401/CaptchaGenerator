package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Variable {

    // private String name;
    private Var type;
    private String value;

    public Variable(Var type, String value) {
        this.type = type;
        this.value = value;
    }

    public Var getType() {
        return type;
    }

    public void setType(Var type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Variable{" + "type=" + type + ", value=" + value + '}';
    }
}
