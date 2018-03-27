package com.example.kocka.sudoku.game;
import com.example.kocka.sudoku.game.Rule.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Table {

    ArrayList<Line> lines;
    ArrayList<Row> rows;
    ArrayList<Square> squares;
    ArrayList<Field> fields;
    ArrayList<RuleModel> ruleModels;

    public Table(){
        ruleModels = new ArrayList<>();
        fields = new ArrayList<>();
        lines = new ArrayList<>();
        squares = new ArrayList<>();
        rows = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Line line = new Line(i);
            Row row = new Row(i);
            ruleModels.add(line);
            ruleModels.add(row);
            lines.add(line);
            rows.add(row);
        }
        for (int blockLine = 0; blockLine < 3; blockLine++) {
            for (int i = 0; i < 3; i++) {
                Square square = new Square(i + blockLine * 3);
                for (int j = 0; j < 3; j++) {
                    square.addRow(this.rows.get(j + i * 3));
                    square.addLine(this.lines.get(blockLine * 3 + i));
                }
                squares.add(square);
                ruleModels.add(square);
            }
        }
    }

    public boolean insertNodes(){
        int fieldCounter = 0;
        this.fields.clear();
        for (int i = 0; i < 9; i++) {
            this.rows.get(i).fields.clear();
            this.lines.get(i).fields.clear();
            this.squares.get(i).fields.clear();
        }

        for (int squareNumber = 0; squareNumber < 9; squareNumber++) {
            ArrayList<Integer> randomNumbers = new ArrayList<>();
            for (int i = 1; i < 10; i++) {
                randomNumbers.add(i);
                Collections.shuffle(randomNumbers);
            }
            for (int lineNumber = 0; lineNumber < 3; lineNumber++) {
                for (int rowNumber = 0; rowNumber < 3; rowNumber++) {
                    Square square = this.squares.get(squareNumber);
                    Line line = this.lines.get(squareNumber / 3 * 3 + lineNumber);
                    Row row = this.rows.get((squareNumber % 3 * 3) + rowNumber);
                    Random random = new Random();
                    int randomnumber = -1;
                    int randomIndex = -5;
                    int insertionTry = 0;
                    //finding an insertable number for node
                    boolean correctInsertation = false;
                    while(!correctInsertation) {
                        insertionTry ++;
                        if(insertionTry > 9){
                            return false;
                        }
                        randomIndex = random.nextInt(randomNumbers.size());
                        randomnumber = randomNumbers.get(randomIndex);
                        if(!line.hasNodeWithNumber(randomnumber) && !row.hasNodeWithNumber(randomnumber)){
                            correctInsertation = true;
                        }
                    }
                    Field newField = new Field(line, row, square, randomnumber,fieldCounter );
                    fieldCounter++;
                    this.fields.add(newField);
                    randomNumbers.remove(randomIndex);
                }
            //Closing the current square
            }
        }
        return true;
    }

    public void printTable(){
        for (Line line:this.lines) {
            if(line.fields.size() > 1)
            System.out.print("---------------------------------------");
            System.out.println();
            for (Field field :line.fields) {
                System.out.print(" | " + field.toString());
            }
            System.out.println();
        }
    }

    public static Table createTable(){
        Table table = new Table();
        boolean succesfullInseration = false;
        while(!succesfullInseration){
            succesfullInseration = table.insertNodes();
        }
        return table;
    }


    public void removeNodes(int quantityToRemove){
        Random rnd = new Random();
        for (int i = 0; i < quantityToRemove; i++) {
            int randomInt = rnd.nextInt(81);
            Field field = fields.get(randomInt);
            if(!field.isEditable()) {
                field.setNumber(0);
                field.setEditable(true);
            } else {
                i--;
            }
        }
    }

    public void editNode(int position, int number) throws IllegalArgumentException{
        Field field = null;
        for(Field fieldToSearch:fields){
            if(fieldToSearch.position == position){
                field = fieldToSearch;
            }
        }
        if(field == null){
            throw new IllegalArgumentException("No Field found with that position");
        }

        if(field.editable){
            field.number = number;
        } else {
            throw new IllegalArgumentException("That field is not editable");
        }
    }

    public ArrayList<Field> getFields() {
        return fields;
    }

    public boolean checkIfTableCorrect() {
        for (RuleModel rulemodel : ruleModels) {
            if (!rulemodel.isCorrectRuleModelForBuilding()) {
                return false;
            }
        }
        return true;
    }
//        return !ruleModels.stream().map(RuleModel::isCorrectRuleModelForBuilding)
//                .anyMatch(ruleModels -> ruleModels.equals(false));

    //TODO
    //public List<Integer> getInCorrectRuleModal(){


}
