package com.example.herizo.clickgame;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int score;
    private int nextNumber;
    private int selectedNumber;

    private static int BILLION = 1_000_000_000;
    private static int TIME_MAX = 10;
    private long startTimer;
    private Handler handler;

    private TextView scoreView;
    private TextView numberView;
    private TextView timeView;
    private GridLayout buttonsGrid;

    private class Time {

    }

    private Runnable timeElapser = new Runnable() {
        @Override
        public void run() {
            long current_time = TIME_MAX - (System.nanoTime() - startTimer)/BILLION;
            updateTimerDisplay(current_time);
            if(current_time < 0){
                end();
            }else {
                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();
        startTimer = System.nanoTime();

        scoreView = (TextView) findViewById(R.id.SCORE);
        numberView = (TextView) findViewById(R.id.NUMBER);
        timeView = (TextView) findViewById(R.id.TIME);
        buttonsGrid = (GridLayout) findViewById(R.id.BUTTONS_GRID);

        handler.post(timeElapser);
        updateDisplay();

    }

    private void updateTimerDisplay(long currentTime){
        timeView.setText("Time left :" + currentTime);
    }

    private void updateDisplay() {
        scoreView.setText("Score :" + score);
        numberView.setText("Next number :" + nextNumber);
    }

    public void choseNumber(View view) {
        Random r = new Random();
        Button selectedButton = (Button) view;
        selectedNumber = Integer.parseInt(selectedButton.getText().toString());

        if (selectedNumber == nextNumber) {
            score++;
        } else {
            score = 0;
        }

        nextNumber = r.nextInt(10);
        shuffleDigits();
        updateDisplay();
    }

    private void end(){
        Intent i = new Intent(MainActivity.this, EndGame.class);
        i.putExtra("score", score);
        System.out.println("CHANGEMENT D'ACTIVITE");
        startActivityForResult(i, 1);
    }

    private void shuffleDigits(){
        Integer[] numbersArray = new Integer[]{0,1,2,3,4,5,6,7,8,9};
        List<Integer> numbers = new ArrayList<>(Arrays.asList(numbersArray));
        Collections.shuffle(numbers);

        for(int i = 0; i < buttonsGrid.getChildCount(); i++){
            Button button =(Button) buttonsGrid.getChildAt(i);

            button.setText(Integer.toString(numbers.get(0)));
            numbers.remove(0);
        }
    }

    private void resetGame() {
        score = 0;
        startTimer = System.nanoTime();
        updateDisplay();
        handler.post(timeElapser);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            boolean replay = data.getBooleanExtra("replay", false);
            if(replay)
                resetGame();
            else
                System.exit(0);
        }
    }
}
