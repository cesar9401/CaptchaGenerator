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
    private boolean asignment;

    public Assignment(Token type, Token id, Operation op, boolean global, boolean asignment) {
        this.type = type;
        this.id = id;
        this.op = op;
        this.global = global;
        this.asignment = asignment;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        Variable v = this.op.run(table, operation);
        operation.getEh().addSymbolTable(type, id, v, global, table, asignment);
        return null;
    }

    @Override
    public String toString() {
        return "Assignment{" + "id=" + id + ", type=" + type + '}';
    }
}
