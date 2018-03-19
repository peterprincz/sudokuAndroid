package com.example.kocka.sudoku.game.Rule;

public class Field {
    
    public Line line;

    public Row row;

    public Square square;

    public int position;

    public int number;

    public boolean editable;


    public Field(Line line, Row row, Square square, int number, int position) {
        this.editable = false;
        this.line = line;
        line.fields.add(this);
        this.row = row;
        row.fields.add(this);
        this.square = square;
        square.fields.add(this);
        this.number = number;
        this.position = position;
    }

    @Override
    public String toString(){
        return(editable?"\u001B[31m" + this.number + "\u001B[0m":String.valueOf(this.number));
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
}
