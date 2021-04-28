
package com.cesar31.captchaweb.model;

/**
 *
 * @author cesar31
 */
public class Err {
    
    private int line;
    private int column;
    private String type;
    private String description;

    public Err() {
    }

    public Err(int line, int column) {
        this.line = line;
        this.column = column;
    }

    public Err(int line, int column, String type) {
        this.line = line;
        this.column = column;
        this.type = type;
    }
    
    public Err(int line, int column, String type, String description) {
        this.line = line;
        this.column = column;
        this.type = type;
        this.description = description;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Error{" + "line=" + line + ", column=" + column + ", type=" + type + ", description=" + description + '}';
    }
}
