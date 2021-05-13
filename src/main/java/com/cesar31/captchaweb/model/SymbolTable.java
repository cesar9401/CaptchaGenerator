package com.cesar31.captchaweb.model;

import java.util.LinkedList;

/**
 *
 * @author cesar31
 */
public class SymbolTable extends LinkedList<Variable> {

    /* Atributos */
    private String process;
    
    /**
     * Constructor
     */
    public SymbolTable() {
        super();
    }
    
    public SymbolTable(String process) {
        super();
        this.process = process;
    }
    
    public void setGlobalValue(String id, String value) {
        Variable v = getVariable(id);
        if(v != null) {
            v.setValue(value);
        }
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

    public String getProcess() {
        return process;
    }
}
