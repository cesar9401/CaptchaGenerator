package com.cesar31.captchaweb.control;

import static com.cesar31.captchaweb.model.Var.*;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.SymbolTable;
import com.cesar31.captchaweb.model.Token;
import com.cesar31.captchaweb.model.Var;
import com.cesar31.captchaweb.model.Variable;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class EnviromentHandler {

    private List<Err> errors;

    public EnviromentHandler() {
    }

    public EnviromentHandler(List<Err> errors) {
        this.errors = errors;
    }

    /**
     * Verificar variables para repeat
     *
     * @param type
     * @param id
     * @param a
     * @param e
     */
    public void checkForVariable(Token type, Token id, Variable a, SymbolTable e) {
        if (type != null) {
            /* Declaracion */
            if (a != null) {
                if (a.getType() == INTEGER) {
                    //this.addSymbolTable(type, id, a, false, e, true);
                } else {
                    /* Error, se esperaba tipo integar */
                    Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                    err.setDescription("Esta intentando asignar un valor de tipo " + a.getType().toString().toLowerCase() + "a una variable de tipo entero, no se puede evaluar condicion para REPEAT, se espera variable de tipo entero.");
                    this.errors.add(err);
                    // this.parser.getErrors().add(err);
                }
            }
        } else {
            /* Asignacion */
            Variable v = this.getFromSymbolTable(id, e, false);
            if (v != null) {
                if (v.getType() != INTEGER) {
                    /* Error, se espera variable de tipo entero */
                    Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                    err.setDescription("La variable " + v.getId() + ", es de tipo " + v.getType().toString().toLowerCase() + ", se necesita variable de tipo integer para evaluar condicion para REPEAT.");
                    this.errors.add(err);
                    // this.parser.getErrors().add(err);
                }
            } else {
                /* Error se verifica en metodo getFromSymbolTable */
            }

            /* Hacer asignacion, errores se verifican en metodos getFromSymbolTable & makeAssignment*/
            // makeAssignment(id, a, e);
        }
    }

    public void checkBooleanVariable(String condition, Token lparen, Variable v) {
        if (v != null) {
            if (v.getType() == BOOLEAN) {
                // System.out.println(condition + " -> " + v.getValue());
            } else {
                /* Se esperaba variabla tipo boolean, no se puede evaluar condicion */
                Err err = new Err(lparen.getLine(), lparen.getColumn() + 1, "SEMANTICO", v.getValue());
                String description = "Se esperaba variable de tipo boolean, se encontro con variable de tipo " + v.getType().toString().toLowerCase() + "(value = " + v.getValue() + "), no es posible evaluar " + condition + ".";
                err.setDescription(description);
                this.errors.add(err);
                // this.parser.getErrors().add(err);
            }
        } else {
            /* Error, no se puede evaluar la condicion */
            Err err = new Err(lparen.getLine(), lparen.getColumn() + 1, "SEMANTICO", "null");
            String description = "Se encontro valor null. No es posible evaluar " + condition;
            err.setDescription(description);
            this.errors.add(err);
            // this.parser.getErrors().add(err);
        }
    }

    /**
     * Hacer asignacion
     *
     * @param id
     * @param a
     * @param e
     */
    public void makeAssignment(Token id, Variable a, SymbolTable e) {
        Variable v = this.getFromSymbolTable(id, e, false);
        if (v != null) {
            if (a != null) {
                if (v.getType() == a.getType()) {
                    if (a.getValue() != null) {
                        v.setValue(a.getValue());
                    } else {
                        /* Revisar else */
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        err.setDescription("Esta intentando asignar un valor null a la variable " + v.getId() + ". Verifique que alguno de los operadores no tenga valor nulo.");
                        this.errors.add(err);
                    }

                } else if (v.getType() == INTEGER && a.getType() == BOOLEAN) {
                    if(a.getValue() != null) {
                        String value = Boolean.valueOf(a.getValue()) ? "1" : "0";
                        v.setValue(value);
                    } else {
                    
                    }
                } else {
                    /* Error tipos distintos */
                    Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                    String description = "Esta intentando asignar una variable de tipo " + a.getType().toString().toLowerCase() + "(value = " + a.getValue() + ") a una variable de tipo " + v.getType().toString().toLowerCase() + "(id = " + v.getId() + ")";
                    err.setDescription(description);
                    this.errors.add(err);
                }
            }
        } else {
            /* Error se verifica en getFromSymbolTable */
        }
    }

    /**
     * Agregar a tabla de simbolos
     *
     * @param type
     * @param id
     * @param value
     * @param global
     * @param e
     * @param assignment
     * @param operation
     */
    public void addSymbolTable(Token type, Token id, Variable value, boolean global, SymbolTable e, boolean assignment, AstOperation operation) {
        if (type != null) {
            if (value != null) {
                // Verificar que tipo declarado sea igual al tipo de variable
                if (getVar(type) == value.getType() && value.getValue() != null) {
                    Variable v = new Variable(getVar(type), id.getValue(), global, value.getValue());
                    v.setLine(id.getLine());

                    /* scope */
                    v.setScope(operation.getScope().peek());
                    if (!e.contains(v.getId())) {
                        e.add(v);

                        /* Tabla de simbolos para reporte */
                        operation.getMain().add(v);
                    } else {
                        /* La variable ya esta definida */
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        err.setDescription("La variable " + id.getValue() + ", ya esta definida, intente con un id distinto.");
                        this.errors.add(err);
                    }
                } else {
                    /* Asignacion de booleano a entero */
                    if (getVar(type) == INTEGER && value.getType() == BOOLEAN) {
                        if (value.getValue() != null) {
                            String val = Boolean.valueOf(value.getValue()) ? "1" : "0";

                            Variable v = new Variable(INTEGER, id.getValue(), global, val);
                            v.setLine(id.getLine());
                            v.setScope(operation.getScope().peek());
                            
                            if (!e.contains(v.getId())) {
                                e.add(v);

                                /* Tabla de simbolos para reporte */
                                operation.getMain().add(v);
                            } else {
                                /* La variable ya esta definida */
                                Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                                err.setDescription("La variable " + id.getValue() + ", ya esta definida, intente con un id distinto.");
                                this.errors.add(err);
                            }
                        }

                        /* No son variables del mismo tipo */
                    } else if (getVar(type) != value.getType()) {
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        err.setDescription("Esta intentando asignar una variable de tipo " + value.getType().toString().toLowerCase() + " a una variable de tipo " + type.getValue());
                        this.errors.add(err);
                    }

                    /* La variable no esta definida */
                    if (value.getValue() == null) {
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        String description = "Esta intentando agregar un valor null a la variable " + id.getValue() + ".";
                        if (value.getId() != null) {
                            description += "La variable " + value.getId() + ", no esta definida.";
                        }
                        err.setDescription(description);
                        this.errors.add(err);
                    }
                }
            } else {
                if (!assignment) {
                    Variable v = new Variable(getVar(type), id.getValue(), global, null);
                    v.setLine(id.getLine());
                    /* scope */
                    v.setScope(operation.getScope().peek());
                    if (!e.contains(v.getId())) {
                        e.add(v);

                        /* Tabla de simbolos para reporte */
                        operation.getMain().add(v);
                    } else {
                        /* La variable ya esta definida */
                        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                        err.setDescription("La variable " + id.getValue() + ", ya esta definida intente con un id distinto.");
                        this.errors.add(err);
                    }
                } else {
                    /* No se puede asignar por valor nulo */
                    Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                    err.setDescription("Uno de los operadores es nulo, no es posible obtener el valor para la variable " + id.getValue());
                    this.errors.add(err);
                }
            }
        } else {
            /* Indicar tipo */
            System.out.println("No se indico tipo");
        }
    }

    /**
     * Obtener variable de tabla de simbolos
     *
     * @param id
     * @param e
     * @param assignment
     * @return
     */
    public Variable getFromSymbolTable(Token id, SymbolTable e, boolean assignment) {
        Variable v = e.getVariable(id.getValue());
        //Variable v = e.get(id.getValue());

        if (v != null) {
            if (v.getValue() == null && assignment) {
                Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
                String description = "La variable " + id.getValue() + ", no tiene un valor definido, no es posible realizar la asignacion.";
                err.setDescription(description);
                this.errors.add(err);
                // this.parser.getErrors().add(err);

                return null;
            }

            return v;
        }

        /* Error la variable no existe */
        Err err = new Err(id.getLine(), id.getColumn(), "SEMANTICO", id.getValue());
        String description = "No se puede encontrar la variable " + id.getValue() + ", esta no se ha definido.";
        err.setDescription(description);
        this.errors.add(err);
        // this.parser.getErrors().add(err);

        return null;
    }

    /**
     * Obtener tipo de variable
     *
     * @param type
     * @return
     */
    private Var getVar(Token type) {
        return Var.valueOf(type.getValue().toUpperCase());
    }
}
