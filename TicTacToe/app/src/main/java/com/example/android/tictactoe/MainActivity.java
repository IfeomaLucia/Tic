package com.example.android.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] buttons = new Button[3][3];
    boolean playerXTurn = true;
    int Count;
    int points1;
    int points2;

    TextView textViewPlayerX;
    TextView textViewPlayerO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayerX = (TextView) findViewById(R.id.text_view_p1);
        textViewPlayerO = (TextView) findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                String buttonID = "b_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = (Button) findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button reset = (Button) findViewById(R.id.button_reset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(!((Button) view).getText().toString().equals("")){
            return;
        }

        if (playerXTurn){
            ((Button) view).setText("X");
        }else{
            ((Button) view).setText("O");
        }

        Count++;

        if (Win()){
            if (playerXTurn){
                playerXWins();
            }
            else{
                playerOWins();
            }
        }else if( Count == 9){
            draw();
        }else {
            playerXTurn = !playerXTurn;
        }


    }

    private boolean Win(){
        String[][] select = new String[3][3];

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                select[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++){
            if(select[i][0].equals(select[i][1]) && select[i][0].equals(select[i][2]) && !select[i][0].equals("")){
                return true;
            }
        }

        for (int i = 0; i < 3; i++){
            if(select[0][i].equals(select[1][i]) && select[0][i].equals(select[2][i]) && !select[0][i].equals("")){
                return true;
            }
        }
            if(select[0][0].equals(select[1][1]) && select[0][0].equals(select[2][2]) && !select[0][0].equals("")){
                return true;
            }

        if(select[0][2].equals(select[1][1]) && select[0][2].equals(select[2][0]) && !select[0][2].equals("")){
            return true;
        }
        return false;
    }

    private void playerXWins(){
        points1++;
        Toast.makeText(this, "Player X wins!", Toast.LENGTH_SHORT).show();
        textViewPlayerX.setText("Player X: " + points1);
        resetBoard();
    }

    private void playerOWins(){
        points2++;
        Toast.makeText(this, "Player O wins!", Toast.LENGTH_SHORT).show();
        textViewPlayerO.setText("Player O: " + points2);
        resetBoard();
    }

    private void draw(){
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
     Count = 0;
        playerXTurn = true;
    }

    public void reset(){
        points1 = 0;
        points2 = 0;
        textViewPlayerX.setText("Player X: " + points1);
        textViewPlayerO.setText("Player O: " + points2);
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("Count", Count);
        outState.putInt("PlayerXPoints", points1);
        outState.putInt("PlayerOPoints", points2);
        outState.putBoolean("PlayerXTurn", playerXTurn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Count = savedInstanceState.getInt("Count");
        points1 = savedInstanceState.getInt("PlayerXPoints");
        points2 = savedInstanceState.getInt("PlayerOPoints");
        playerXTurn = savedInstanceState.getBoolean("PlayerXTurn");
    }
}

