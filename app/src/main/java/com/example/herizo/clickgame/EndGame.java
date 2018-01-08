package com.example.herizo.clickgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class EndGame extends Activity {

    private int score;
    private boolean replay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("END GAME");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game);

        Intent intent = getIntent();
        score = intent.getExtras().getInt("score", 0);
        updateDisplay();
    }

    private void updateDisplay(){
        TextView view = (TextView) findViewById(R.id.SCORE);
        view.setText("Score :" + score);
    }

    public void replay(View view){
        replay = true;
        returnIntent();
    }

    public void quit(View view){
        returnIntent();
    }

    private void returnIntent(){
        Intent intent = new Intent();
        intent.putExtra("replay", replay);
        setResult(RESULT_OK, intent);
        finish();
    }
}
