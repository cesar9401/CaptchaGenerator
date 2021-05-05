package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

/**
 *
 * @author cesar31
 */
public interface Instruction {

    /**
     * Test para verificar errores en codigo
     *
     * @param table
     * @param operation
     * @return
     */
    public Object test(SymbolTable table, AstOperation operation);

    /**
     * Ejecutar accion de la instruccion
     *
     * @param table
     * @param operation
     * @return
     */
    public Object run(SymbolTable table, AstOperation operation);
}
