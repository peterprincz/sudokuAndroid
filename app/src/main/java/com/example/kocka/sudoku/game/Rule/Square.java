package com.example.kocka.sudoku.game.Rule;


import java.util.ArrayList;


public class Square extends RuleModel {

    public ArrayList<Field> fields;
    public ArrayList<Row> rows;
    public ArrayList<Line> lines;
    public int squarenumber;


    public Square(int squarenumber){
        this.fields = new ArrayList<>();
        this.rows = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.squarenumber = squarenumber;
    }

    public void addRow(Row row){
        this.rows.add(row);
    }

    public void addLine(Line line){
        this.lines.add(line);
    }


    @Override
    public ArrayList<Field> getFields(){
        return this.fields;
    }


}
