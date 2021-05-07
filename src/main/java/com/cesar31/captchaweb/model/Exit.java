package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public class Exit implements Instruction {

    public Exit() {
    }
    
    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        return null;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        return null;
    }
}
