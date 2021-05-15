package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;
import java.util.LinkedList;

/**
 *
 * @author cesar31
 */
public class While implements Instruction {

    private Operation op;
    private LinkedList<Instruction> instructions;
    private Token l;

    public While() {
    }

    public While(Operation op, LinkedList<Instruction> instructions, Token l) {
        /* Parentesis */
        this.l = l;

        /* Instrucciones while */
        this.op = op;
        this.instructions = instructions;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        while (Boolean.valueOf(op.run(table, operation).getValue())) {
            SymbolTable local = new SymbolTable();
            local.addAll(table);

            /* scope */
            operation.getScope().push("WHILE");

            for (Instruction i : instructions) {
                Object o = i.run(local, operation);

                if (o != null) {
                    if (o instanceof Exit) {
                        return o;
                    }
                }
            }
        }

        /* recuperar scope */
        operation.getScope().pop();
        return null;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        Variable v = op.test(table, operation);
        operation.getEh().checkBooleanVariable("WHILE", l, v);

        SymbolTable local = new SymbolTable();
        local.addAll(table);
        instructions.forEach(i -> {
            i.test(local, operation);
        });

        return null;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

    public LinkedList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Token getL() {
        return l;
    }

    public void setL(Token l) {
        this.l = l;
    }
}
