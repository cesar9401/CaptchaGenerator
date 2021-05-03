package com.cesar31.captchaweb.model;

import java.util.LinkedList;

/**
 *
 * @author cesar31
 */
public class SymbolTable extends LinkedList<Variable> {

    /* Atributos */
    /**
     * Constructor
     */
    public SymbolTable() {
        super();
    }

    public Variable getVariable(String id) {
        for (Variable v : this) {
            if (v.getId().equals(id)) {
                return v;
            }
        }
        return null;
    }

    public boolean contains(String id) {
        for (Variable v : this) {
            if (v.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}
