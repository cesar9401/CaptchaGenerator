package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;

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

    private Token token;

    private Operation() {
    }

    public Operation(OperationType type, Operation left, Operation right, Token op) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.op = op;
    }

    /**
     * Constructor para operaciones unarias y funciones predefinidas
     *
     * @param type
     * @param right
     * @param op
     */
    public Operation(OperationType type, Operation right, Token op) {
        this.type = type;
        this.right = right;
        this.op = op;
    }

    public Operation(OperationType type, Variable v) {
        this.type = type;
        this.v = v;
    }

    public Operation(OperationType type, Token token) {
        this.type = type;
        this.token = token;
    }
    
    @Override
    public Variable run(SymbolTable table, AstOperation operation) {
        switch (type) {
            case integer:
            case decimal:
            case character:
            case string:
            case bool:
                return this.v;
            case id:
                Variable variable = table.getVariable(this.token.getValue());
                if (variable == null) {
                    /* La variable no existe */
                    Err err = new Err(token.getLine(), token.getColumn(), "SEMANTICO", token.getValue());
                    String description = "No se puede encontrar la variable " + token.getValue() + ", esta no se ha definido.";
                    err.setDescription(description);
                    operation.getErrors().add(err);
                    
                    return null;
                } else if (variable.getValue() == null) {
                    /* La variable no tiene un valor definido */
                    Err err = new Err(token.getLine(), token.getColumn(), "SEMANTICO", token.getValue());
                    String description = "La variable " + token.getValue() + ", no tiene un valor definido, no es posible realizar la asignacion.";
                    err.setDescription(description);
                    operation.getErrors().add(err);
                    
                    return null;
                }

                return variable;
            case SUM:
                return operation.getMake().sum(left.run(table, operation), right.run(table, operation), op);
            case SUBTRACTION:
                return operation.getMake().subtraction(left.run(table, operation), right.run(table, operation), op);
            case MULTIPLICATION:
                return operation.getMake().multiplication(left.run(table, operation), right.run(table, operation), op);
            case DIVISION:
                return operation.getMake().division(left.run(table, operation), right.run(table, operation), op);
            case UMINUS:
                return operation.getMake().uminus(right.run(table, operation), op);
            case AND:
                return operation.getMake().and(left.run(table, operation), right.run(table, operation), op);
            case OR:
                return operation.getMake().or(left.run(table, operation), right.run(table, operation), op);
            case NOT:
                return operation.getMake().not(right.run(table, operation), op);
            case EQUAL:
            case GREATER:
            case GREATER_OR_EQUAL:
            case LESS_OR_EQUAL:
            case NOT_EQUAL:
            case SMALLER:
                return operation.getMake().compare(left.run(table, operation), right.run(table, operation), type, op);
            case ASC:
                return operation.getFunction().ASC(right.run(table, operation), op);
            case DESC:
                return operation.getFunction().DESC(right.run(table, operation), op);
            case LETIMPAR:
                return operation.getFunction().LETIMPAR_NUM(right.run(table, operation), op);
            case LETPAR:
                return operation.getFunction().LETPAR_NUM(right.run(table, operation), op);
            case REVERSE:
                return operation.getFunction().REVERSE(right.run(table, operation), op);
            case RANDOM_C:
                return operation.getFunction().CARACTER_ALEATORIO();
            case RANDOM_N:
                return operation.getFunction().NUM_ALEATORIO();
        }
        return null;
    }

    @Override
    public Variable test(SymbolTable table, AstOperation operation) {
        return this.run(table, operation);
    }

    public Variable getV() {
        return v;
    }

    public void setV(Variable v) {
        this.v = v;
    }

    public Token getOp() {
        return op;
    }

    public void setOp(Token op) {
        this.op = op;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public Operation getLeft() {
        return left;
    }

    public void setLeft(Operation left) {
        this.left = left;
    }

    public Operation getRight() {
        return right;
    }

    public void setRight(Operation right) {
        this.right = right;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
