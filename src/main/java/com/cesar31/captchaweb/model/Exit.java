package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public class Exit implements Instruction {
    
    private Token token;

    public Exit() {
    }
    
    public Exit(Token token) {
        this.token = token;
    }
    
    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        return new Exit();
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        return new Exit();
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
