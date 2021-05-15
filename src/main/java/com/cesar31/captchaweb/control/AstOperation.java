package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.SymbolTable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cesar31
 */
public class AstOperation {

    private SymbolTable main;
    private Stack<String> scope; 
    
    private List<Err> errors;
    private MakeOperation make;
    private Function function;
    private EnviromentHandler eh;
    private List<String> alerts;

    private List<String> inserts;
    private boolean redirect;

    private HttpServletRequest request;
    private HttpServletResponse response;

    public AstOperation() {
        this.errors = new ArrayList<>();
        this.make = new MakeOperation(errors);
        this.function = new Function(errors);
        this.eh = new EnviromentHandler(errors);
        this.alerts = new ArrayList<>();
        this.inserts = new ArrayList<>();
        this.redirect = false;
        this.main = new SymbolTable();
        this.scope = new Stack<>();
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

    public List<String> getAlerts() {
        return alerts;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public List<String> getInserts() {
        return inserts;
    }

    public void setInserts(List<String> inserts) {
        this.inserts = inserts;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }

    public Stack<String> getScope() {
        return scope;
    }

    public SymbolTable getMain() {
        return main;
    }
}
