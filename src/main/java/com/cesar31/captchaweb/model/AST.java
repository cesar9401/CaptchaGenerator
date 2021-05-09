package com.cesar31.captchaweb.model;

import java.util.LinkedList;

/**
 *
 * @author cesar31
 */
public class AST {

    private String name;
    private Integer script;
    private LinkedList<Instruction> instructions;

    public AST() {
        this.instructions = new LinkedList<>();
    }

    public AST(String name, Integer script) {
        this();
        this.name = name;
        this.script = script;
    }

    public String getName() {
        return name;
    }

    public Integer getScript() {
        return script;
    }

    public LinkedList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public void addAll(LinkedList<Instruction> list) {
        this.instructions.addAll(list);
    }
}
