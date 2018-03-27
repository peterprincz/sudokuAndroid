package com.example.kocka.sudoku;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.kocka.sudoku.game.Game;
import com.example.kocka.sudoku.game.Rule.Field;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity {

    HashMap<Integer, EditText> editTextHashMap;

    Game sudokuGame;

    @TargetApi(16)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startUpDialog();
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        builder.setNegativeButton("of course not", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog ad = builder.create();
        ad.setTitle("Quitting");
        ad.show();
    }

    private void startUpDialogaaa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Choose a difficulty");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("Easy", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startGame(25);
            }
        });
        AlertDialog ad = builder.create();
        ad.setTitle("new Game");
        ad.show();
    }

    private void startUpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("How many Fields needs to be removed?(1-80)");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("Start game", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Integer quantity = Integer.valueOf(input.getText().toString());
                if(quantity < 1 || quantity > 81){
                    startUpDialog();
                } else {
                    startGame(quantity);
                }
            }
        });
        AlertDialog ad = builder.create();
        ad.setTitle("new Game");
        ad.show();
    }

    public void startGame(int quantityToRemove){
        Integer quantity = Integer.valueOf(quantityToRemove);
        if(quantity < 1 || quantity > 81){
            startActivity(new Intent(GameActivity.this, GameActivity.class));
        }
        editTextHashMap = new HashMap<>();
        setContentView(R.layout.activity_game);
        TableLayout table = findViewById(R.id.tableLayout);
        setUpTable(table);
        sudokuGame = new Game();
        sudokuGame.startGame(Integer.valueOf(quantityToRemove));
        setEditableFields();
    }

    private void setEditableFields() {
        for(Field field:sudokuGame.getTable().getFields()){
            final EditText editText = editTextHashMap.get(field.position);
            if(field.number == 0) {
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setTextColor(Color.RED);
                setOnFocusListener(sudokuGame, editText);
                setEditorActionListener(sudokuGame, editText);
            } else {
                editText.setText(String.valueOf(field.getNumber()));
            }
        }
    }

    private void setEditorActionListener(final Game sudokuGame,final EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                if (keyCode == EditorInfo.IME_ACTION_DONE){
                    String number = editText.getText().toString();
                    if(!number.equals("")) {
                        sudokuGame.getTable().editNode((Integer) editText.getTag(), Integer.valueOf(number));
                    }
                    closeInput(v.getWindowToken());
                    gameOverCheck();
                    return true;
                }
                return false;
            }
        });
    }


    private void setOnFocusListener(final Game sudokuGame, final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String number = editText.getText().toString();
                if(!number.equals("")){
                    if(!b){
                        sudokuGame.getTable().editNode((Integer) editText.getTag(), Integer.valueOf(number));
                    }
                }
                closeInput(view.getWindowToken());
                gameOverCheck();
            }
        });
    }

    @TargetApi(17)
    private void setUpTable(TableLayout table) {
        TableLayout.LayoutParams tableParams =  new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);
        tableParams.weight = 1;
        TableRow.LayoutParams rowparams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        rowparams.weight = 1;
        fillTableLayout(table, tableParams, rowparams);
        table.setStretchAllColumns(true);
        table.setShrinkAllColumns(true);
    }

    private void fillTableLayout(TableLayout table, TableLayout.LayoutParams tableParams, TableRow.LayoutParams rowparams) {
        for(int i = 0; i < 9; i ++){
            TableRow row = new TableRow(this);
            row.setLayoutParams(tableParams);
            int realI = 0;
            if(i / 3 == 0) {realI = i * 3;}
            if(i / 3 == 1) {realI = (i + 6)  * 3;}
            if(i / 3 == 2) {realI = (i + 12) * 3;}
            for (int j = 0;j < 9;j++){
                EditText field = new EditText(this);
                if(j / 3 == 0){
                    field.setTag(realI + j);
                    editTextHashMap.put(realI + j, field);
                }
                if(j / 3 == 1){
                    field.setTag(realI + j + 6);
                    editTextHashMap.put(realI + j + 6, field);
                }
                if(j / 3 == 2){
                    field.setTag(realI + j + 12);
                    editTextHashMap.put(realI + j + 12, field);
                }
                setEditableField(rowparams, row, field);
            }

            table.addView(row);
        }
    }

    @TargetApi(17)
    private void setEditableField(TableRow.LayoutParams rowparams, TableRow row, EditText field) {
        field.getTag();
        field.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        field.setBackgroundColor(Color.RED);
        field.setInputType(InputType.TYPE_NULL);
        field.setBackgroundResource(android.R.color.transparent);
        field.setSingleLine(true);
        field.setLayoutParams(rowparams);
        field.setImeOptions(EditorInfo.IME_ACTION_DONE);
        row.addView(field);
        field.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
    }

    private void gameOverCheck(){
        if(sudokuGame.isGameOver()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You have won !!!!!!");
            builder.setPositiveButton("new Game", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                    startActivity(new Intent(GameActivity.this, GameActivity.class));
                }
            });
            AlertDialog ad = builder.create();
            ad.setTitle("Congrat!!!");
            ad.show();
        }
    }



    private void closeInput(IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }
}
