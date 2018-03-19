package com.example.kocka.sudoku.game.Rule;

import java.util.ArrayList;

public abstract class RuleModel {

    public ArrayList<Field> fields;

    public boolean hasNodeWithNumber(int number) {
        for (Field field :getFields()) {
            if(field.getNumber() == number){
                return true;
            }
        }
        return false;
    }

    public boolean isCorrectRuleModel(){
        for (int i = 1; i < 10; i++) {
            if(!hasNodeWithNumber(i)){
                return false;
            }
        }
        return true;
    }

    public ArrayList<Field> getFields(){
        return this.fields;
    }

}
