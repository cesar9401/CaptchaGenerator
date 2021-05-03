package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.OperationType;
import static com.cesar31.captchaweb.model.Var.*;
import static com.cesar31.captchaweb.model.OperationType.*;
import com.cesar31.captchaweb.model.Err;
import com.cesar31.captchaweb.model.Token;
import com.cesar31.captchaweb.model.Variable;
import com.cesar31.captchaweb.parser.CaptchaParser;

/**
 *
 * @author cesar31
 */
public class MakeOperation {

    private CaptchaParser parser;

    public MakeOperation() {
    }

    public MakeOperation(CaptchaParser parser) {
        this.parser = parser;
    }

    /**
     * Operacion suma
     *
     * @param a
     * @param b
     * @param op
     * @return
     */
    public Variable sum(Variable a, Variable b, Token op) {
        Variable v = null;

        if (a == null || b == null) {
            return null;
        }

//        if (a.getValue() == null || b.getValue() == null) {
//            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
//            String description = "";
//            if (a.getValue() == null && b.getValue() == null) {
//                description = "No es posible realizar la operacion: null + null";
//            } else if (a.getValue() == null) {
//                description = "No es posible realizar la operacion: null + " + b.getValue();
//            } else if (b.getValue() == null) {
//                description = "No es posible realizar la operacion: " + a.getValue() + " + null";
//            }
//            err.setDescription(description);
//            this.parser.getErrors().add(err);
//            return null;
//        }
        // integer + integer
        if (a.getType() == INTEGER && b.getType() == INTEGER) {
            Integer value = getInteger(a) + getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // integer/decimal + string OR string + integer/decimal
        if (((a.getType() == INTEGER || a.getType() == DECIMAL) && b.getType() == STRING) || (a.getType() == STRING && (b.getType() == INTEGER || b.getType() == DECIMAL))) {
            String value = a.getValue().concat(b.getValue());
            v = new Variable(STRING, value);
        }

        // integer + decimal OR decimal + integer
        if ((a.getType() == DECIMAL && b.getType() == INTEGER) || (a.getType() == INTEGER && b.getType() == DECIMAL)) {
            Double value = formatDouble(getDouble(a) + getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // integer + char
        if (a.getType() == INTEGER && b.getType() == CHAR) {
            Integer value = getInteger(a) + getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // char + integer
        if (a.getType() == CHAR && b.getType() == INTEGER) {
            Integer value = getASCII(a) + getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // decimal + decimal
        if (a.getType() == DECIMAL && b.getType() == DECIMAL) {
            Double value = formatDouble(getDouble(a) + getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // decimal + char
        if (a.getType() == DECIMAL && b.getType() == CHAR) {
            Double value = formatDouble(getDouble(a) + getASCII(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // char + decimal
        if (a.getType() == CHAR && b.getType() == DECIMAL) {
            Double value = formatDouble(getASCII(a) + getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // string + string
        if (a.getType() == STRING && b.getType() == STRING) {
            String value = a.getValue().concat(b.getValue());
            v = new Variable(STRING, value);
        }

        // string + char OR char + string
        if ((a.getType() == STRING && b.getType() == CHAR) || (a.getType() == CHAR && b.getType() == STRING)) {
            String value = a.getValue().concat(b.getValue());
            v = new Variable(STRING, value);
        }

        // char + char
        if (a.getType() == CHAR && b.getType() == CHAR) {
            Integer value = getASCII(a) + getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // integer + boolean
        if (a.getType() == INTEGER && b.getType() == BOOLEAN) {
            Integer value = getInteger(a) + getIntValue(b);
            v = new Variable(INTEGER, value.toString());
        }

        // boolean + integer
        if (a.getType() == BOOLEAN && b.getType() == INTEGER) {
            Integer value = getIntValue(a) + getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // decimal + boolean
        if (a.getType() == DECIMAL && b.getType() == BOOLEAN) {
            Double value = formatDouble(getDouble(a) + getIntValue(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // boolean + decimal
        if (a.getType() == BOOLEAN && b.getType() == DECIMAL) {
            Double value = formatDouble(getIntValue(a) + getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // char + boolean
        if (a.getType() == CHAR && b.getType() == BOOLEAN) {
            Integer value = getASCII(a) + getIntValue(b);
            v = new Variable(INTEGER, value.toString());
        }

        // boolean + char
        if (a.getType() == BOOLEAN && b.getType() == CHAR) {
            Integer value = getIntValue(a) + getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // boolean + boolean
        if (a.getType() == BOOLEAN && b.getType() == BOOLEAN) {
            Boolean value = getBoolean(a) || getBoolean(b);
            v = new Variable(BOOLEAN, value.toString());
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar suma entre variables de tipo " + a.getType() + " y " + b.getType() + ", (" + a.getValue() + " + " + b.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    /**
     * Operacion resta
     *
     * @param a
     * @param b
     * @param op
     * @return
     */
    public Variable subtraction(Variable a, Variable b, Token op) {
        Variable v = null;

        if (a == null || b == null) {
            return null;
        }

        // integer - integer
        if (a.getType() == INTEGER && b.getType() == INTEGER) {
            Integer value = getInteger(a) - getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // integer - decimal OR decimal - integer
        if ((a.getType() == DECIMAL && b.getType() == INTEGER) || (a.getType() == INTEGER && b.getType() == DECIMAL)) {
            Double value = formatDouble(getDouble(a) - getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // integer - char
        if (a.getType() == INTEGER && b.getType() == CHAR) {
            Integer value = getInteger(a) - getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // char - integer
        if (a.getType() == CHAR && b.getType() == INTEGER) {
            Integer value = getASCII(a) - getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // decimal - decimal
        if (a.getType() == DECIMAL && b.getType() == DECIMAL) {
            Double value = formatDouble(getDouble(a) - getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // decimal - char
        if (a.getType() == DECIMAL && b.getType() == CHAR) {
            Double value = formatDouble(getDouble(a) - getASCII(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // char - decimal
        if (a.getType() == CHAR && b.getType() == DECIMAL) {
            Double value = formatDouble(getASCII(a) - getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // char - char
        if (a.getType() == CHAR && b.getType() == CHAR) {
            Integer value = getASCII(a) - getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // integer - boolean
        if (a.getType() == INTEGER && b.getType() == BOOLEAN) {
            Integer value = getInteger(a) - getIntValue(b);
            v = new Variable(INTEGER, value.toString());
        }

        // boolean - integer
        if (a.getType() == BOOLEAN && b.getType() == INTEGER) {
            Integer value = getIntValue(a) - getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // decimal - boolean
        if (a.getType() == DECIMAL && b.getType() == BOOLEAN) {
            Double value = formatDouble(getDouble(a) - getIntValue(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // boolean - decimal
        if (a.getType() == BOOLEAN && b.getType() == DECIMAL) {
            Double value = formatDouble(getIntValue(a) - getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar resta entre variables de tipo " + a.getType() + " y " + b.getType() + ", (" + a.getValue() + " - " + b.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    /**
     * Operacion multiplicacion
     *
     * @param a
     * @param b
     * @param op
     * @return
     */
    public Variable multiplication(Variable a, Variable b, Token op) {
        Variable v = null;

        if (a == null || b == null) {
            return null;
        }

        // integer * integer
        if (a.getType() == INTEGER && b.getType() == INTEGER) {
            Integer value = getInteger(a) * getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // integer * decimal OR decimal * integer
        if ((a.getType() == DECIMAL && b.getType() == INTEGER) || (a.getType() == INTEGER && b.getType() == DECIMAL)) {
            Double value = formatDouble(getDouble(a) * getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // integer * char
        if (a.getType() == INTEGER && b.getType() == CHAR) {
            Integer value = getInteger(a) * getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // char * integer
        if (a.getType() == CHAR && b.getType() == INTEGER) {
            Integer value = getASCII(a) * getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // decimal * decimal
        if (a.getType() == DECIMAL && b.getType() == DECIMAL) {
            Double value = formatDouble(getDouble(a) * getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // decimal * char
        if (a.getType() == DECIMAL && b.getType() == CHAR) {
            Double value = formatDouble(getDouble(a) * getASCII(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // char * decimal
        if (a.getType() == CHAR && b.getType() == DECIMAL) {
            Double value = formatDouble(getASCII(a) * getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // char * char
        if (a.getType() == CHAR && b.getType() == CHAR) {
            Integer value = getASCII(a) * getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // integer * boolean
        if (a.getType() == INTEGER && b.getType() == BOOLEAN) {
            Integer value = getInteger(a) * getIntValue(b);
            v = new Variable(INTEGER, value.toString());
        }

        // boolean * integer
        if (a.getType() == BOOLEAN && b.getType() == INTEGER) {
            Integer value = getIntValue(a) * getInteger(b);
            v = new Variable(INTEGER, value.toString());
        }

        // decimal * boolean
        if (a.getType() == DECIMAL && b.getType() == BOOLEAN) {
            Double value = formatDouble(getDouble(a) * getIntValue(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // boolean * decimal
        if (a.getType() == BOOLEAN && b.getType() == DECIMAL) {
            Double value = formatDouble(getIntValue(a) * getDouble(b));
            v = new Variable(DECIMAL, value.toString());
        }

        // char * boolean
        if (a.getType() == CHAR && b.getType() == BOOLEAN) {
            Integer value = getASCII(a) * getIntValue(b);
            v = new Variable(INTEGER, value.toString());
        }

        // boolean * char
        if (a.getType() == BOOLEAN && b.getType() == CHAR) {
            Integer value = getIntValue(a) * getASCII(b);
            v = new Variable(INTEGER, value.toString());
        }

        // boolean * boolean
        if (a.getType() == BOOLEAN && b.getType() == BOOLEAN) {
            Boolean value = getBoolean(a) && getBoolean(b);
            v = new Variable(BOOLEAN, value.toString());
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar multiplicacion entre variables de tipo " + a.getType() + " y " + b.getType() + ", (" + a.getValue() + " * " + b.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    /**
     * Operacion division
     *
     * @param a
     * @param b
     * @param op
     * @return
     */
    public Variable division(Variable a, Variable b, Token op) {
        Variable v = null;

        if (a == null || b == null) {
            return null;
        }

        // integer / integer
        if (a.getType() == INTEGER && b.getType() == INTEGER) {
            if (getInteger(b) != 0) {
                Double value = formatDouble(getDouble(a) / getDouble(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // integer / decimal OR decimal / integer
        if ((a.getType() == DECIMAL && b.getType() == INTEGER) || (a.getType() == INTEGER && b.getType() == DECIMAL)) {
            if (getDouble(b) != 0) {
                Double value = formatDouble(getDouble(a) / getDouble(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // integer / char
        if (a.getType() == INTEGER && b.getType() == CHAR) {
            if (getASCII(b) != 0) {
                Double value = formatDouble(getDouble(a) / getASCII(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // char / integer
        if (a.getType() == CHAR && b.getType() == INTEGER) {
            if (getDouble(b) != 0) {
                Double value = formatDouble(getASCII(a) / getDouble(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // decimal / decimal
        if (a.getType() == DECIMAL && b.getType() == DECIMAL) {
            if (getDouble(b) != 0) {
                Double value = formatDouble(getDouble(a) / getDouble(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // decimal / char
        if (a.getType() == DECIMAL && b.getType() == CHAR) {
            if (getASCII(b) != 0) {
                Double value = formatDouble(getDouble(a) / getASCII(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // char / decimal
        if (a.getType() == CHAR && b.getType() == DECIMAL) {
            if (getDouble(b) != 0) {
                Double value = formatDouble(getASCII(a) / getDouble(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // char / char
        if (a.getType() == CHAR && b.getType() == CHAR) {
            if (getASCII(b) != 0) {
                Double value = formatDouble(getASCII(a).doubleValue() / getASCII(b).doubleValue());
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // integer / boolean
        if (a.getType() == INTEGER && b.getType() == BOOLEAN) {
            if (getIntValue(b) != 0) {
                Integer value = getInteger(a) / getIntValue(b);
                v = new Variable(INTEGER, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // boolean / integer
        if (a.getType() == BOOLEAN && b.getType() == INTEGER) {
            if (getInteger(b) != 0) {
                Double value = formatDouble(getIntValue(a).doubleValue() / getDouble(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // decimal / boolean
        if (a.getType() == DECIMAL && b.getType() == BOOLEAN) {
            if (getIntValue(b) != 0) {
                Double value = formatDouble(getDouble(a) / getIntValue(b).doubleValue());
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // boolean / decimal
        if (a.getType() == BOOLEAN && b.getType() == DECIMAL) {
            if (getDouble(b) != 0) {
                Double value = formatDouble(getIntValue(a).doubleValue() / getDouble(b));
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // char / boolean
        if (a.getType() == CHAR && b.getType() == BOOLEAN) {
            if (getIntValue(b) != 0) {
                Integer value = getASCII(a) / getIntValue(b);
                v = new Variable(INTEGER, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        // boolean / char
        if (a.getType() == BOOLEAN && b.getType() == CHAR) {
            if (getASCII(b) != 0) {
                Double value = getIntValue(a).doubleValue() / getASCII(b).doubleValue();
                v = new Variable(DECIMAL, value.toString());
            } else {
                System.out.println("Division entre cero");
            }
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar division entre variables de tipo " + a.getType() + " y " + b.getType() + ", (" + a.getValue() + " / " + b.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    /**
     *
     * @param a
     * @param op
     * @return
     */
    public Variable uminus(Variable a, Token op) {
        Variable v = null;

        if (a == null) {
            return null;
        }

        if (a.getType() == INTEGER) {
            Integer value = -getInteger(a);
            v = new Variable(INTEGER, value.toString());
        }

        if (a.getType() == DECIMAL) {
            Double value = -getDouble(a);
            v = new Variable(DECIMAL, value.toString());
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar la negacion numerica de " + a.getType() + ", ( -" + a.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    /**
     * Or entre booleanos
     *
     * @param a
     * @param b
     * @param op
     * @return
     */
    public Variable or(Variable a, Variable b, Token op) {
        Variable v = null;

        if (a == null || b == null) {
            return null;
        }

        if (a.getType() == BOOLEAN && b.getType() == BOOLEAN) {
            Boolean value = getBoolean(a) || getBoolean(b);
            v = new Variable(BOOLEAN, value.toString());
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar la operacion OR entre variables de tipo " + a.getType() + " y " + b.getType() + ", (" + a.getValue() + " || " + b.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    /**
     * AND entre booleanos
     *
     * @param a
     * @param b
     * @param op
     * @return
     */
    public Variable and(Variable a, Variable b, Token op) {
        Variable v = null;

        if (a == null || b == null) {
            return null;
        }

        if (a.getType() == BOOLEAN && b.getType() == BOOLEAN) {
            Boolean value = getBoolean(a) && getBoolean(b);
            v = new Variable(BOOLEAN, value.toString());
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar la operacion AND entre variables de tipo " + a.getType() + " y " + b.getType() + ", (" + a.getValue() + " && " + b.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    /**
     * Negacion booleano
     *
     * @param a
     * @param op
     * @return
     */
    public Variable not(Variable a, Token op) {
        Variable v = null;

        if (a == null) {
            return null;
        }

        if (a.getType() == BOOLEAN) {
            Boolean value = !getBoolean(a);
            v = new Variable(BOOLEAN, value.toString());
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar la negacion booleana de " + a.getType() + ", ( !" + a.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    public Variable compare(Variable a, Variable b, OperationType comp, Token op) {
        Variable v = null;
        
        if (a == null || b == null) {
            return null;
        }

        // OperationType numbers
        if ((a.getType() == INTEGER || a.getType() == DECIMAL) && (b.getType() == INTEGER || b.getType() == DECIMAL)) {
            Boolean value;
            switch (comp) {
                case SMALLER:
                    value = getDouble(a) < getDouble(b);
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case GREATER:
                    value = getDouble(a) > getDouble(b);
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case LESS_OR_EQUAL:
                    value = getDouble(a) <= getDouble(b);
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case GREATER_OR_EQUAL:
                    value = getDouble(a) >= getDouble(b);
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case NOT_EQUAL:
                    value = getDouble(a).doubleValue() != getDouble(b).doubleValue();
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case EQUAL:
                    value = getDouble(a).doubleValue() == getDouble(b).doubleValue();
                    v = new Variable(BOOLEAN, value.toString());
                    break;
            }
        }

        // OperationType string
        if (a.getType() == STRING && b.getType() == STRING) {
            Boolean value;
            switch (comp) {
                case EQUAL:
                    value = a.getValue().equals(b.getValue());
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case NOT_EQUAL:
                    value = !a.getValue().equals(b.getValue());
                    v = new Variable(BOOLEAN, value.toString());
                    break;
            }

        }

        // OperationType char
        if (a.getType() == CHAR && b.getType() == CHAR) {
            Boolean value;
            switch (comp) {
                case EQUAL:
                    value = a.getValue().equals(b.getValue());
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case NOT_EQUAL:
                    value = !a.getValue().equals(b.getValue());
                    v = new Variable(BOOLEAN, value.toString());
                    break;
            }
        }

        if (a.getType() == BOOLEAN && b.getType() == BOOLEAN) {
            Boolean value;
            switch (comp) {
                case EQUAL:
                    value = a.getValue().equals(b.getValue());
                    v = new Variable(BOOLEAN, value.toString());
                    break;

                case NOT_EQUAL:
                    value = !a.getValue().equals(b.getValue());
                    v = new Variable(BOOLEAN, value.toString());
                    break;
            }
        }

        if (v == null) {
            Err err = new Err(op.getLine(), op.getColumn(), "SEMANTICO", op.getValue());
            String description = "No es posible realizar la operacion " + op.getValue() + " entre variables de tipo " + a.getType() + " y " + b.getType() + ", (" + a.getValue() + " " + op.getValue() + " " + b.getValue() + ")";
            err.setDescription(description);
            this.parser.getErrors().add(err);
        }

        return v;
    }

    private Integer getIntValue(Variable v) {
        return (getBoolean(v)) ? 1 : 0;
    }

    private Integer getInteger(Variable v) {
        return Integer.valueOf(v.getValue());
    }

    private Double getDouble(Variable v) {
        return Double.valueOf(v.getValue());
    }

    private Integer getASCII(Variable v) {
        return (int) v.getValue().charAt(0);
    }

    private Double formatDouble(Double value) {
        return Math.round(value * 10_000d) / 10_000d;
    }

    private Boolean getBoolean(Variable v) {
        return Boolean.valueOf(v.getValue());
    }

    private String getCompareSymbol(OperationType comp) {
        switch (comp) {
            case GREATER:
                return ">";
            case SMALLER:
                return "<";
            case GREATER_OR_EQUAL:
                return ">=";
            case LESS_OR_EQUAL:
                return "<=";
            case EQUAL:
                return "==";
            case NOT_EQUAL:
                return "!=";
        }
        return "";
    }
}
