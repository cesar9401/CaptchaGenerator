package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public class Alert implements Instruction {

    private Token l;
    private Operation op;

    public Alert() {
    }

    public Alert(Token l, Operation op) {
        this.l = l;
        this.op = op;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        /* Verificar que operacion sea de tipo string */
        Variable v = op.test(table, operation);
        if (v != null) {
            if (v.getType() != Var.STRING) {
                /* Error, argumento de tipo string */
                Err e = new Err(l.getLine(), l.getColumn() + 1, "SEMANTICO", v.getValue());
                e.setDescription("Se encontro como argumento de la funcion: ALERT_INFO una variable de tipo " + v.getType().toString().toLowerCase() + ", se esperaba argumento de tipo string.");
                operation.getErrors().add(e);
            }
        } else {
            /* Error, argumento null*/
            Err e = new Err(l.getLine(), l.getColumn() + 1, "SEMANTICO", "null");
            e.setDescription("Se encontro argumento null en la funcion: ALERT_INFO, no se puede evaluar.");
            operation.getErrors().add(e);
        }

        return null;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        Variable v = op.run(table, operation);
        if (v != null) {
            if (v.getValue() != null) {
                operation.getAlerts().add(v.getValue());
            }
        }

        return null;
    }

    public Token getL() {
        return l;
    }

    public void setL(Token l) {
        this.l = l;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }
}
