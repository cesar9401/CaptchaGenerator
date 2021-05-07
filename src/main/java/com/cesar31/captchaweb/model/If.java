package com.cesar31.captchaweb.model;

import java.util.LinkedList;

/**
 *
 * @author cesar31
 */
public class If {

    private String name;
    private Operation condition;
    private LinkedList<Instruction> intructions;
    private Token l, r;

    public If() {
    }

    public If(String name, Operation condition, LinkedList<Instruction> intructions, Token l, Token r) {
        this.name = name;
        this.condition = condition;
        this.intructions = intructions;
        this.l = l;
        this.r = r;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Operation getCondition() {
        return condition;
    }

    public void setCondition(Operation condition) {
        this.condition = condition;
    }

    public LinkedList<Instruction> getIntructions() {
        return intructions;
    }

    public void setIntructions(LinkedList<Instruction> intructions) {
        this.intructions = intructions;
    }

    public Token getL() {
        return l;
    }

    public void setL(Token l) {
        this.l = l;
    }

    public Token getR() {
        return r;
    }

    public void setR(Token r) {
        this.r = r;
    }
}
