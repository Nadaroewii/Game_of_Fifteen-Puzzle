package com.lat1.nadavsga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class Menu extends AppCompatActivity {

    private int emptyX=3;
    private int emptyY=3;
    private RelativeLayout group;
    private Button[][] buttons;
    private int[] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        loadViews();
        loadAlpha();
        generateAlphabet();
        loadDataToViews();
    }

    private void loadDataToViews(){
        emptyX=3;
        emptyY=3;
        for (int i = 0; i < group.getChildCount() - 1; i++){
            buttons[i/4][i%4].setText(String.valueOf(tiles[i]));
            buttons[i/4][i%4].setBackgroundResource(android.R.drawable.btn_default);
        }
        buttons[emptyX][emptyY].setText("");
        buttons[emptyX][emptyY].setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }
    private void generateAlphabet(){
        int n=15;
        Random random= new Random();
        while (n>1){
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum]=tiles[n];
            tiles[n] = temp;
        }

        if (!isSolvable())
            generateAlphabet();
    }

    private boolean isSolvable(){
        int countInversions = 0;
        for (int i = 0; i < 15; i++){
            for (int j = 0; j < i; j++){
                if (tiles[j]>tiles[i])
                    countInversions++;
            }
        }
        return countInversions % 2 == 0;
    }

    private void loadAlpha(){
        tiles = new int[16];
        for (int i = 0; i < group.getChildCount() - 1; i++){
            tiles[i] = i + 1;
        }
    }
    private void loadViews(){
        group=findViewById(R.id.group);
        buttons = new Button[4][4];

        for (int i = 0; i < group.getChildCount(); i++) {
            buttons[i/4][i%4] = (Button) group.getChildAt(i);
        }
    }
    public void buttonClick(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if ((Math.abs(emptyX-x)==1&&emptyY==y)||(Math.abs(emptyY-y)==1&&emptyX==x)){
            buttons[emptyX][emptyY].setText(button.getText().toString());
            buttons[emptyX][emptyY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            emptyX=x;
            emptyY=y;
            checkWin();
        }
    }
    private void checkWin(){
        boolean isWin = false;
        if (emptyX==3&&emptyY==3){
            for (int i = 0; i < group.getChildCount() - 1; i++) {
                if (buttons[i / 4][i % 4].getText().toString().equals(String.valueOf(i + 1))){
                    isWin = true;
                } else {
                    isWin=false;
                    break;
                }
            }
        }
        if (isWin){
            Toast.makeText(this, "You Win !!!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < group.getChildCount(); i++) {
                buttons[i/4][i%4].setClickable(false);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(android.view.MenuItem item){
        if (item.getItemId() == R.id.start) {
            loadViews();
            loadAlpha();
            generateAlphabet();
            loadDataToViews();
           // startActivity(new Intent(this, Menu.class));
        } else if (item.getItemId() == R.id.out) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return true;
    }
}