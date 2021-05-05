package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public class Assignment implements Instruction {

    private Token type;
    private Token id;
    private Operation op;
    private boolean global;

    public Assignment(Token type, Token id, Operation op, boolean global) {
        this.type = type;
        this.id = id;
        this.op = op;
        this.global = global;
    }

    public Assignment(Token id, Operation op) {
        this.id = id;
        this.op = op;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        if (this.type != null) {
            /* Declaracion y asignacion */
            Variable v = this.op.run(table, operation);
            operation.getEh().addSymbolTable(type, id, v, global, table, true);
        } else {
            /* Asignacion */
            Variable v = this.op.run(table, operation);
            operation.getEh().makeAssignment(id, v, table);
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        return this.run(table, operation);
    }

    public Token getType() {
        return type;
    }

    public Token getId() {
        return id;
    }

    public Operation getOp() {
        return op;
    }

    @Override
    public String toString() {
        return "Assignment{" + "id=" + id + ", type=" + type + '}';
    }
}
