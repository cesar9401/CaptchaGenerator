package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public interface Instruction {

    /**
     * Ejecutar accion de la instruccion
     *
     * @param table
     * @return
     */
    public Object run(SymbolTable table, AstOperation operation);
}
