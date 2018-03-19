package com.example.kocka.sudoku.game.Rule;


import java.util.ArrayList;

public class Line extends RuleModel {

    public ArrayList<Field> fields;

    public int lineNumber;

    public Line(int lineNumber) {
        this.fields = new ArrayList<>();
        this.lineNumber = lineNumber;
    }

    @Override
    public ArrayList<Field> getFields(){
        return this.fields;
    }


}

