package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;
import java.util.LinkedList;

/**
 *
 * @author cesar31
 */
public class Repeat implements Instruction {

    private Assignment assignment;
    private Operation until;
    private LinkedList<Instruction> instructions;

    private Token l1, l2;

    public Repeat() {
    }

    public Repeat(Assignment assignment, Operation until, LinkedList<Instruction> instructions, Token l1, Token l2) {
        this.assignment = assignment;
        this.until = until;
        this.instructions = instructions;

        this.l1 = l1;
        this.l2 = l2;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        SymbolTable local = new SymbolTable();
        local.addAll(table);
        assignment.run(local, operation);
        Variable variable = local.getVariable(this.assignment.getId().getValue());
        Integer valor = Integer.valueOf(variable.getValue());
        int v = valor;
        while (v < Integer.valueOf(until.run(local, operation).getValue())) {
            SymbolTable localFor = new SymbolTable();
            localFor.addAll(local);

            for (Instruction i : instructions) {
                Object o = i.run(localFor, operation);

                if (o != null) {
                    if (o instanceof Exit) {
                        return o;
                    }
                }

            }
            v++;
            variable.setValue(String.valueOf(v));
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        SymbolTable local = new SymbolTable();
        local.addAll(table);
        assignment.test(local, operation);

        // Verificar declaracion/asignacion repeat
        Variable variable = local.getVariable(this.assignment.getId().getValue());
        operation.getEh().checkForVariable(this.assignment.getType(), this.assignment.getId(), variable, local);

        Variable un = until.test(local, operation);

        // Verificar until
        if (un != null) {
            if (un.getType() != Var.INTEGER) {
                Err err = new Err(l2.getLine(), l2.getColumn() + 1, "SEMANTICO", un.getValue());
                err.setDescription("Se esperaba variable de tipo integer, se encontro variable de tipo " + un.getType().toString().toLowerCase() + ", no se puede evaluar REPEAT.");
                operation.getErrors().add(err);
            }
        } else {
            System.out.println("Until Null");
        }

        // Verificar instrucciones
        SymbolTable localFor = new SymbolTable();
        localFor.addAll(local);
        for (Instruction i : instructions) {
            i.test(localFor, operation);
        }

        return null;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Operation getUntil() {
        return until;
    }

    public void setUntil(Operation until) {
        this.until = until;
    }

    public LinkedList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public Token getL1() {
        return l1;
    }

    public void setL1(Token l1) {
        this.l1 = l1;
    }

    public Token getL2() {
        return l2;
    }

    public void setL2(Token l2) {
        this.l2 = l2;
    }
}
