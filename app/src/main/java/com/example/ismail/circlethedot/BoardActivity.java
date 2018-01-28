package com.example.ismail.circlethedot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BoardActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //displays the gameView view
        gameView = (GameView) findViewById(R.id.gameview);
        TextView textView = (TextView) findViewById(R.id.score);
        textView.setText(Integer.toString(gameView.count));
        System.out.println("Count in boardview: " +gameView.count);
    }

    public void updateTextView() {
        TextView textView = (TextView) findViewById(R.id.score);
        textView.setText(Integer.toString(gameView.count));
    }
}
