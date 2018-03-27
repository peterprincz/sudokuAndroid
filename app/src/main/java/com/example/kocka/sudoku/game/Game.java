package com.example.kocka.sudoku.game;


import java.util.List;
import java.util.Scanner;

public class Game {

    Table table;
    Scanner reader = new Scanner(System.in);


    public void startGame(int quantityToRemove){
        table = Table.createTable();
        table.removeNodes(quantityToRemove);
        //gameLoop();
    }

    public void gameLoop(){
        while(!isGameOver()){
            System.out.println(isGameOver());
            table.printTable();
            editField();
        }
        System.out.println("you have won");
    }


    private void editField(){
        System.out.println("Enter a position: ");
        int position = reader.nextInt();
        if(position > 81 || position < 0){
            throw new IllegalStateException("Invalid input, number must be between , 81");
        }
        System.out.println("Enter a number: ");
        int number = reader.nextInt();
        if(number > 9 || number < 0){
            throw new IllegalStateException("Invalid input, number must be between , 9");
        }
        try {
            table.editNode(position, number);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    public boolean isGameOver(){
        return table.checkIfTableCorrect();
    }

    public Table getTable() {
        return table;
    }


}
