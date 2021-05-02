package com.cesar31.captchaweb.control;

import static com.cesar31.captchaweb.model.Var.*;
import com.cesar31.captchaweb.model.Variable;
import com.cesar31.captchaweb.parser.CaptchaParser;

/**
 *
 * @author cesar31
 */
public class Function {

    private CaptchaParser parser;

    public Function() {
    }

    public Function(CaptchaParser parser) {
        this.parser = parser;
    }

    public static void main(String[] args) {
        Function f = new Function();
        System.out.println(f.ASC(new Variable(STRING, "concatenar 1 + valor")));
        System.out.println(f.DESC(new Variable(STRING, "concatenar 1 + valor")));
        System.out.println(f.REVERSE(new Variable(STRING, "palabra" + 12 * 2)));
        System.out.println(f.LETPAR_NUM(new Variable(STRING, "cadena")));
        System.out.println(f.LETIMPAR_NUM(new Variable(STRING, "1 + 3 arroz mas 1")));

        System.out.println(f.CARACTER_ALEATORIO());
        System.out.println(f.CARACTER_ALEATORIO());
        System.out.println(f.CARACTER_ALEATORIO());
        System.out.println(f.CARACTER_ALEATORIO());
        System.out.println(f.CARACTER_ALEATORIO());
        System.out.println(f.CARACTER_ALEATORIO());
        System.out.println(f.CARACTER_ALEATORIO());

        System.out.println(f.NUM_ALEATORIO());
        System.out.println(f.NUM_ALEATORIO());
        System.out.println(f.NUM_ALEATORIO());
        System.out.println(f.NUM_ALEATORIO());
        System.out.println(f.NUM_ALEATORIO());
        System.out.println(f.NUM_ALEATORIO());
        System.out.println(f.NUM_ALEATORIO());
        System.out.println(f.NUM_ALEATORIO());

    }

    /**
     * Ordenar palabra en orden ascendente
     *
     * @param a
     * @return
     */
    public Variable ASC(Variable a) {

        if (a == null) {
            return null;
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

        } else {
            /* Error */
            System.out.println("Variable no es de tipo string");
        }

        return null;
    }

    public Variable DESC(Variable a) {
        if (a == null) {
            return null;
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
        } else {
            /* Error */
            System.out.println("Variable no es de tipo string");
        }

        return null;
    }

    public Variable LETPAR_NUM(Variable a) {
        if (a == null) {
            return null;
        }

        if (a.getType() == STRING) {
            String value = "";
            for (int i = 0; i < a.getValue().length(); i++) {
                if (i % 2 == 0) {
                    value += (int) a.getValue().charAt(i);
                }
            }

            return new Variable(STRING, value);
        } else {
            /* Error */
            System.out.println("Variable no es de tipo string");
        }

        return null;
    }

    public Variable LETIMPAR_NUM(Variable a) {
        if (a == null) {
            return null;
        }

        if (a.getType() == STRING) {
            String value = "";
            for (int i = 0; i < a.getValue().length(); i++) {
                if (i % 2 == 1) {
                    value += (int) a.getValue().charAt(i);
                }
            }

            return new Variable(STRING, value);
        } else {
            /* Error */
            System.out.println("Variable no es de tipo string");
        }

        return null;
    }

    public Variable REVERSE(Variable a) {
        if (a == null) {
            return null;
        }

        if (a.getType() == STRING) {
            String value = "";
            for (int i = a.getValue().length() - 1; i >= 0; i--) {
                value += a.getValue().charAt(i);
            }

            return new Variable(STRING, value);
        } else {
            /* Error */
            System.out.println("Variable no es de tipo string");
        }

        return null;
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

}
