package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public enum OperationType {
    /* Typos de variables */
    integer,
    decimal,
    string,
    character,
    bool,
    id,
    /* Operaciones y comparaciones */
    SUM,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    UMINUS,
    AND,
    OR,
    NOT,
    SMALLER,
    GREATER,
    LESS_OR_EQUAL,
    GREATER_OR_EQUAL,
    EQUAL,
    NOT_EQUAL
}
