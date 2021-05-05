package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public class Statement implements Instruction {

    private Token type;
    private Token id;
    private boolean global;

    public Statement(Token type, Token id, boolean global) {
        this.type = type;
        this.id = id;
        this.global = global;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        operation.getEh().addSymbolTable(type, id, null, global, table, false);
        return null;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        return this.run(table, operation);
    }
}
