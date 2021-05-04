package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Err;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class AstOperation {

    private List<Err> errors;
    private MakeOperation make;
    private Function function;
    private EnviromentHandler eh;

    public AstOperation() {
        this.errors = new ArrayList<>();
        this.make = new MakeOperation(errors);
        this.function = new Function(errors);
        this.eh = new EnviromentHandler(errors);
    }

    public List<Err> getErrors() {
        return errors;
    }

    public MakeOperation getMake() {
        return make;
    }

    public Function getFunction() {
        return function;
    }

    public EnviromentHandler getEh() {
        return eh;
    }
}
