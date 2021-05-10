
package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Parameter {
    
    private Param type;
    private String value;
    private Token token;

    public Parameter() {
    }

    public Parameter(Param type, String value) {
        this.type = type;
        this.value = value;
    }

    public Parameter(Param type, String value, Token token) {
        this.type = type;
        this.value = value;
        this.token = token;
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

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
    
    @Override
    public String toString() {
        return "Parameter{" + "type=" + type + ", value=" + value + '}';
    }
}
