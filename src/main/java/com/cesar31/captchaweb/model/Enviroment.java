package com.cesar31.captchaweb.model;

import java.util.HashMap;

/**
 *
 * @author cesar31
 */
public class Enviroment {

    private String name;
    private int line;
    private int column;
    
    private HashMap<String, Variable> variables;
    private Enviroment dad;

    public Enviroment() {
        this.variables = new HashMap<>();
    }

    public Enviroment(String name, Enviroment dad) {
        this();
        this.name = name;
        this.dad = dad;
    }

    public Enviroment(String name, int line, int column, Enviroment dad) {
        this();
        this.name = name;
        this.line = line;
        this.column = column;
        this.dad = dad;
    }
    
    // Agregar variable
    public void put(Variable v) {
        //System.out.println("Put: " + v.toString());
        this.variables.put(v.getId(), v);
    }

    // Obtener variable
    public Variable get(String id) {
        for (Enviroment e = this; e != null; e = e.getDad()) {
            Variable v = e.getVariables().get(id);
            if (v != null) {
                return v;
            }
        }
        return null;
    }
    
    // Obtener entorno segun variable
    public Enviroment getE(String id) {
        for(Enviroment e = this; e != null; e = e.getDad()) {
            Variable v = e.getVariables().get(id);
            if(v != null) {
                return e;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public HashMap<String, Variable> getVariables() {
        return variables;
    }

    public Enviroment getDad() {
        return dad;
    }

    public void setDad(Enviroment dad) {
        this.dad = dad;
    }

    @Override
    public String toString() {
        return "Enviroment{" + "name=" + name + ", line=" + line + ", column=" + column + '}';
    }
}
