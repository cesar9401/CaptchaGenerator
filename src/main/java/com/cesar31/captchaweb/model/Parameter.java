
package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Parameter {
    
    private Param type;
    private String value;

    public Parameter() {
    }

    public Parameter(Param type, String value) {
        this.type = type;
        this.value = value;
    }

    public Param getType() {
        return type;
    }

    public void setType(Param type) {
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
        return "Parameter{" + "type=" + type + ", value=" + value + '}';
    }
}
