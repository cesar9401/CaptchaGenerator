package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.MakeOperation;

/**
 *
 * @author cesar31
 */
public class Operation implements Instruction {

    private Variable v;
    private Token op;

    private OperationType type;
    private Operation left;
    private Operation right;

    private MakeOperation make;

    private Operation() {
        this.make = new MakeOperation();
    }

    public Operation(OperationType type, Operation left, Operation right, Token op) {
        this();
        this.type = type;
        this.left = left;
        this.right = right;
    }

    public Operation(OperationType type, Operation right, Token op) {
        this();
        this.type = type;
        this.right = right;
    }

    public Operation(OperationType type, Variable v) {
        this();
        this.type = type;
        this.v = v;
    }

    @Override
    public Variable run(SymbolTable table) {
        switch (type) {
            case integer:
            case decimal:
            case character:
            case string:
            case bool:
                return this.v;
            case id:
                return table.getVariable(this.v.getId());
            case SUM:
                return make.sum(left.v, right.v, op);
            case SUBTRACTION:
                return make.subtraction(left.v, right.v, op);
            case MULTIPLICATION:
                return make.multiplication(left.v, right.v, op);
            case DIVISION:
                return make.division(left.v, right.v, op);
            case UMINUS:
                return make.uminus(right.v, op);
            case AND:
                return make.and(left.v, right.v, op);
            case OR:
                return make.or(left.v, right.v, op);
            case NOT:
                return make.not(right.v, op);
            case EQUAL:
            case GREATER:
            case GREATER_OR_EQUAL:
            case LESS_OR_EQUAL:
            case NOT_EQUAL:
            case SMALLER:
                return make.compare(v, v, type, op);
        }
        return null;
    }

}
