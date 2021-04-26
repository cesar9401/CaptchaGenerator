package com.cesar31.captchaweb.control;

import com.cesar31.captchaweb.model.Compare;
import static com.cesar31.captchaweb.model.Var.*;
import static com.cesar31.captchaweb.model.Compare.*;
import com.cesar31.captchaweb.model.Variable;

/**
 *
 * @author cesar31
 */
public class Operation {

    /**
     * Operacion suma
     *
     * @param a
     * @param b
     * @return
     */
    public Variable sum(Variable a, Variable b) {
        Variable v = null;

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

        return v;
    }

    /**
     * Operacion resta
     *
     * @param a
     * @param b
     * @return
     */
    public Variable subtraction(Variable a, Variable b) {
        Variable v = null;

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

        return v;
    }

    /**
     * Operacion multiplicacion
     *
     * @param a
     * @param b
     * @return
     */
    public Variable multiplication(Variable a, Variable b) {
        Variable v = null;

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

        return v;
    }

    /**
     * Operacion division
     *
     * @param a
     * @param b
     * @return
     */
    public Variable division(Variable a, Variable b) {
        Variable v = null;

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

        return v;
    }

    /**
     *
     * @param a
     * @return
     */
    public Variable uminus(Variable a) {
        Variable v = null;

        if (a.getType() == INTEGER) {
            Integer value = -getInteger(a);
            v = new Variable(INTEGER, value.toString());
        }

        if (a.getType() == DECIMAL) {
            Double value = -getDouble(a);
            v = new Variable(DECIMAL, value.toString());
        }

        return v;
    }

    /**
     * Or entre booleanos
     *
     * @param a
     * @param b
     * @return
     */
    public Variable or(Variable a, Variable b) {
        Variable v = null;

        if (a.getType() == BOOLEAN && b.getType() == BOOLEAN) {
            Boolean value = getBoolean(a) || getBoolean(b);
            v = new Variable(BOOLEAN, value.toString());
        }

        return v;
    }

    /**
     * AND entre booleanos
     *
     * @param a
     * @param b
     * @return
     */
    public Variable and(Variable a, Variable b) {
        Variable v = null;

        if (a.getType() == BOOLEAN && b.getType() == BOOLEAN) {
            Boolean value = getBoolean(a) && getBoolean(b);
            v = new Variable(BOOLEAN, value.toString());
        }

        if (v == null) {
            System.out.println("No posible realizar: " + a.getType() + " AND " + b.getType());
        }

        return v;
    }

    /**
     * Negacion booleano
     *
     * @param a
     * @return
     */
    public Variable not(Variable a) {
        Variable v = null;

        if (a.getType() == BOOLEAN) {
            Boolean value = !getBoolean(a);
            v = new Variable(BOOLEAN, value.toString());
        }

        return v;
    }

    public Variable compare(Variable a, Variable b, Compare comp) {
        Variable v = null;
        // Compare numbers
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

        // Compare string
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

        // Compare char
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

        if (v == null) {
            System.out.println("No es posible efectuar: " + a.getValue() + " " + comp + " " + b.getValue());
        }

        return v;
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
}