package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Enviroment;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Token;
import com.cesar31.captchaweb.model.Var;
import com.cesar31.captchaweb.model.Variable;
import com.cesar31.captchaweb.parser.CaptchaParser;

/**
 *
 * @author cesar31
 */
public class EnviromentHandler {

    private CaptchaParser parser;

    public EnviromentHandler() {
    }

    public EnviromentHandler(CaptchaParser parser) {
        this.parser = parser;
    }

    /**
     * Agregar a tabla de simbolos
     *
     * @param type
     * @param id
     * @param value
     * @param global
     * @param e
     * @param assignment
     */
    public void addSymbolTable(Token type, Token id, Variable value, boolean global, Enviroment e, boolean assignment) {
        if(e == null) {
            System.out.println("No existe la tabla de simbolos");
        }
        
        if (type != null) {
            // Verificar que tipo declarado sea igual al tipo de variable
            if (value != null) {
                if (getVar(type) == value.getType() && value.getValue() != null) {
                    value.setId(id.getValue());
                    value.setGlobal(global);
                    
                    
                    if (!e.getVariables().containsKey(value.getId())) {
                        e.put(value);
                        // System.out.println("Agregada a tabla de simbolos: " + value);
                        System.out.println(value + " -> " + e);
                    } else {
                        /* La variable ya esta definida */
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        err.setDescription("La variable " + id.getValue() + "ya esta definida, intente con un id distinto.");
                        this.parser.getErrors().add(err);
                    }
                } else {
                    /* No son variables del mismo tipo */
                    if (getVar(type) != value.getType()) {
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        err.setDescription("Esta intentando asignar una variable de tipo " + value.getType().toString().toLowerCase() + " a una variable de tipo " + type.getValue());
                        this.parser.getErrors().add(err);
                    }

                    /* La variable no esta definida */
                    if (value.getValue() == null) {
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        String description = "Esta intentando agregar un valor nulo a la variable " + id.getValue() + ".";
                        if (value.getId() != null) {
                            description += "La variable " + value.getId() + ", no esta definida.";
                        }
                        err.setDescription(description);
                        this.parser.getErrors().add(err);
                    }
                }
            } else {
                if (!assignment) {
                    Variable v = new Variable(getVar(type), id.getValue(), global, null);
                    if (!e.getVariables().containsKey(v.getId())) {
                        e.put(v);
                        System.out.println(v + " -> " + e);
                        // System.out.println("Agregada a tabla de simbolos: " + v);
                    } else {
                        /* La variable ya esta definida */
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        err.setDescription("La variable " + id.getValue() + "ya esta definida, intente con un id distinto.");
                        this.parser.getErrors().add(err);
                    }
                } else {
                    /* No se puede asignar por valor nulo */
                    Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                    err.setDescription("Uno de los operadores es nulo, no es posible obtener el valor para la variable " + id.getValue());
                    this.parser.getErrors().add(err);
                }
            }
        } else {
            /* Indicar tipo */
            System.out.println("No se indico tipo");
        }
    }

    /**
     * Obtener variable de tabla de simbolos
     *
     * @param id
     * @param e
     * @param assignment
     * @return
     */
    public Variable getFromSymbolTable(Token id, Enviroment e, boolean assignment) {
        Variable v = e.get(id.getValue());
        if (v != null) {
            if (v.getValue() == null & assignment) {
                Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                String description = "La variable " + id.getValue() + ", no tiene un valor definido, no es posible realizar la asignacion.";
                err.setDescription(description);
                this.parser.getErrors().add(err);

                return null;
            }

            return v;
        }

        /* Error la variable no existe */
        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
        String description = "No se puede encontrar la variable " + id.getValue() + ", esta no se ha definido.";
        err.setDescription(description);
        this.parser.getErrors().add(err);

        return null;
    }

    /**
     * Obtener tipo de variable
     *
     * @param type
     * @return
     */
    private Var getVar(Token type) {
        return Var.valueOf(type.getValue().toUpperCase());
    }
}
