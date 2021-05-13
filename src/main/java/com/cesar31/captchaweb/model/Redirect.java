package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public class Redirect implements Instruction {

    private Token token;

    public Redirect() {
    }

    public Redirect(Token token) {
        this.token = token;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        return null;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        operation.setRedirect(true);
        return null;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
