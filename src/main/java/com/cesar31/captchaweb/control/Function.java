package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Token;
import static com.cesar31.captchaweb.model.Var.*;
import com.cesar31.captchaweb.model.Variable;
import java.util.List;

/**
 *
 * @author cesar31
 */
public class Function {

    private List<Err> errors;
    
    public Function() {
    }

    public Function(List<Err> errors) {
        this.errors = errors;
    }

    /**
     * Ordenar palabra en orden ascendente
     *
     * @param a
     * @param l
     * @return
     */
    public Variable ASC(Variable a, Token l) {

        if (a == null) {
            return addErrorVariableNull(l, "ASC");
        }

        if (a.getType() == STRING) {
            char ch[] = a.getValue().toCharArray();

            for (int i = 1; i < ch.length; i++) {
                for (int j = 0; j < ch.length - i; j++) {
                    if ((int) ch[j] > (int) ch[j + 1]) {
                        char aux = ch[j];
                        ch[j] = ch[j + 1];
                        ch[j + 1] = aux;
                    }
                }
            }

            String value = "";
            for (char c : ch) {
                value += c;
            }

            Variable b = new Variable(STRING, value);
            return b;

        }

        /* Error, variable no es de tipo string */
        return addErrorNoString(a, l, "ASC");
    }

    public Variable DESC(Variable a, Token l) {
        if (a == null) {
            return addErrorVariableNull(l, "DESC");
        }

        if (a.getType() == STRING) {
            char ch[] = a.getValue().toCharArray();
            for (int i = 1; i < ch.length; i++) {
                for (int j = 0; j < ch.length - i; j++) {
                    if ((int) ch[j] < (int) ch[j + 1]) {
                        char aux = ch[j];
                        ch[j] = ch[j + 1];
                        ch[j + 1] = aux;
                    }
                }
            }

            String value = "";
            for (char c : ch) {
                value += c;
            }

            return new Variable(STRING, value);
        }

        /* Error, variable no es de tipo string */
        return addErrorNoString(a, l, "DESC");
    }

    public Variable LETPAR_NUM(Variable a, Token l) {
        if (a == null) {
            return addErrorVariableNull(l, "LETPAR_NUM");
        }

        if (a.getType() == STRING) {
            String value = "";
            for (int i = 0; i < a.getValue().length(); i++) {
                if ((i + 1) % 2 == 0) {
                    value += (int) a.getValue().charAt(i);
                } else {
                    value += a.getValue().charAt(i);
                }
            }

            return new Variable(STRING, value);
        }

        /* Error, variable no es de tipo string */
        return addErrorNoString(a, l, "LETPAR_NUM");
    }

    public Variable LETIMPAR_NUM(Variable a, Token l) {
        if (a == null) {
            return addErrorVariableNull(l, "LETIMPAR_NUM");
        }

        if (a.getType() == STRING) {
            String value = "";
            for (int i = 0; i < a.getValue().length(); i++) {
                if ((i + 1) % 2 == 1) {
                    value += (int) a.getValue().charAt(i);
                } else {
                    value += a.getValue().charAt(i);
                }
            }

            return new Variable(STRING, value);
        }

        /* Error, variable no es de tipo string */
        return addErrorNoString(a, l, "LETIMPAR_NUM");
    }

    public Variable REVERSE(Variable a, Token l) {
        if (a == null) {
            return addErrorVariableNull(l, "REVERSE");
        }

        if (a.getType() == STRING) {
            String value = "";
            for (int i = a.getValue().length() - 1; i >= 0; i--) {
                value += a.getValue().charAt(i);
            }

            return new Variable(STRING, value);
        }

        /* Error, variable no es de tipo string */
        return addErrorNoString(a, l, "REVERSE");
    }

    public Variable CARACTER_ALEATORIO() {
        int random = (int) (Math.random() * 52);
        char base = (random < 26) ? 'A' : 'a';
        char ch = (char) (base + random % 26);

        return new Variable(CHAR, String.valueOf(ch));
    }

    public Variable NUM_ALEATORIO() {
        int random = (int) (Math.random() * 10);
        return new Variable(INTEGER, String.valueOf(random));
    }

    private Variable addErrorVariableNull(Token l, String function) {
        Err err = new Err(l.getLine(), l.getColumn() + 1, "SEMANTICO", "null");
        err.setDescription("Se encontro argumento null en la funcion: " + function + ", no se puede evaluar.");
        this.errors.add(err);
        return null;
    }

    private Variable addErrorNoString(Variable a, Token l, String function) {
        Err e = new Err(l.getLine(), l.getColumn() + 1, "SEMANTICO", a.getValue());
        e.setDescription("Se encontro como argumento de la funcion " + function + " una variable de tipo " + a.getType().toString().toLowerCase() + ", se esperaba argumento de tipo string.");
        this.errors.add(e);
        return null;
    }
}
