package com.example.ismail.circlethedot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.ismail.circlethedot.R.id.textView;
import static com.example.ismail.circlethedot.R.id.textView2;
import static com.example.ismail.circlethedot.R.id.textView5;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                String returnedResult = data.getData().toString();

                if (returnedResult.equalsIgnoreCase("Game Over!")) {
                    TextView txtView = (TextView) findViewById(textView2);
                    txtView.setText(returnedResult);
                    txtView.animate();
                    txtView.setTextSize(45);

                    TextView textView3 = (TextView)  findViewById(textView5);
                    textView3.setText("The dot escaped the board");
                    textView3.setTextSize(20);

                    TextView textView2 = (TextView) findViewById(R.id.textView7);
                    textView2.setText("Try again?");
                }else if (returnedResult.equalsIgnoreCase("You Won!!!")) {
                    TextView txtView = (TextView) findViewById(textView2);
                    txtView.setText(returnedResult);
                    txtView.animate();
                    txtView.setTextSize(45);

                    TextView textView3 = (TextView)  findViewById(textView5);
                    textView3.setText("Can you do any better?");
                    textView3.setTextSize(20);

                    TextView textView2 = (TextView) findViewById(R.id.textView7);
                    textView2.setText("Try again?");
                }
            }
        }
    }


    /** Called when the user clicks the new game button */
    public void newGame(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, BoardActivity.class);
        startActivityForResult(intent, 1);
    }
    /** When called change gameDifficulty/background **/
    public void changeDifficulty() {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(3);
        //view.setBackgroundColor(Color.parseColor("#ffffff"));
    }
}
