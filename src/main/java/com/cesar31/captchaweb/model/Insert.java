package com.cesar31.captchaweb.model;

import com.cesar31.captchaweb.control.AstOperation;
import com.cesar31.captchaweb.control.ParserControl;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Insert implements Instruction {

    private int script;
    private List<Operation> operations;
    private Component component;

    public Insert() {
    }

    public Insert(Component component, int script) {
        this.component = component;
        this.script = script;
    }

    public Insert(List<Operation> operations, int script) {
        this.operations = operations;
        this.script = script;
    }

    @Override
    public Object test(SymbolTable table, AstOperation operation) {
        if(this.operations != null) {
            for(Operation o : operations) {
                o.test(table, operation);
            }
        }
        return null;
    }

    @Override
    public Object run(SymbolTable table, AstOperation operation) {
        if (this.component != null) {
            String html = ParserControl.getChilds(component);
            String ins = "document.getElementById('__script__" + script + "__').innerHTML += \"" + html + "\";";
            operation.getInserts().add(ins);
        } else if (this.operations != null) {
            String insert = "";
            for (Operation o : operations) {
                Variable v = o.run(table, operation);
                insert += v.getValue().concat(" ");
            }
            String ins = "document.getElementById('__script__" + script + "__').innerHTML += \"<p class='fs-4 text-warning bg-dark my-2 text-center'>" + insert + "</p>\";";
            operation.getInserts().add(ins);
        }

        return null;
    }

    public int getScript() {
        return script;
    }

    public void setScript(int script) {
        this.script = script;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public void setOperations(List<Operation> operations) {
        this.operations = operations;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }
}
