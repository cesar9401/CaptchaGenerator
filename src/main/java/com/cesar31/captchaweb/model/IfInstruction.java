package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class IfInstruction implements Instruction {

    private List<If> instructions;

    public IfInstruction() {
    }

    public IfInstruction(List<If> instructions) {
        this.instructions = instructions;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        for (If if_ : instructions) {
            boolean condition = (if_.getCondition() != null) ? Boolean.valueOf(if_.getCondition().run(table, operation).getValue()) : true;
            LinkedList<Instruction> ins = if_.getIntructions();

            if (condition) {
                SymbolTable local = new SymbolTable();
                local.addAll(table);
                for (Instruction i : ins) {
                    Object o = i.run(local, operation);
                    
                    if(o != null) {
                        if(o instanceof Exit) {
                            return o;
                        }
                    }
                }
                return null;
            }
        }

        return null;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        for (If if_ : this.instructions) {
            if (if_.getCondition() != null) {
                Variable v = if_.getCondition().test(table, operation);
                operation.getEh().checkBooleanVariable(if_.getName(), if_.getL(), v);
            }

            SymbolTable local = new SymbolTable();
            local.addAll(table);
            for (Instruction i : if_.getIntructions()) {
                i.test(local, operation);
            }
        }

        return null;
    }

    public List<If> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<If> instructions) {
        this.instructions = instructions;
    }
}
