package com.example.ismail.circlethedot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BoardActivity extends AppCompatActivity {

    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        // Get the Intent that started this activity and extract the string
        /*Intent intent = getIntent();*/

        gameView = (GameView) findViewById(R.id.gameview);
    }
}
