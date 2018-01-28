package com.example.ismail.circlethedot;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import static android.R.color.background_dark;
import static android.R.color.white;
import static android.R.id.message;
import static android.app.Activity.RESULT_OK;

/**
 * Created by ismail on 08/09/17.
 */

public class GameView extends View {

    private Paint background;
    private Paint movePaint;
    private Paint blockPaint;
    private Paint dotPaint;
    private Paint dotIsSurrPaint;

    private int screenWidth;
    private int screenHeight;

    private int radius;

    private int clickX = 0;
    private int clickY = 0;

    private GameData gameData;
    private Dot dot;
    private Context context;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //setting up the gameData/dot object
        gameData = new GameData();
        dot = new Dot();

        //±³Ÿ°É«»­±Ê
        background = new Paint();
        background.setColor(ContextCompat.getColor(context, white));

        //¿ÉÒÆ¶¯µã»­±Ê
        movePaint = new Paint();
        movePaint.setColor(ContextCompat.getColor(context, R.color.movePaint));
        movePaint.setAntiAlias(true);

        //²»¿ÉÒÆ¶¯µã»­±Ê
        blockPaint = new Paint();
        blockPaint.setColor(ContextCompat.getColor(context, R.color.blockPaint));
        blockPaint.setAntiAlias(true);

        //Dot»­±Ê
        dotPaint = new Paint();
        dotPaint.setColor(ContextCompat.getColor(context, R.color.dotPaint));
        dotPaint.setAntiAlias(true);

        //±»Î§×¡ºóµÄDot»­±Ê
        dotIsSurrPaint = new Paint();
        dotIsSurrPaint.setColor(ContextCompat.getColor(context, R.color.dotIsSurrPaint));
        dotIsSurrPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);

        //È¡µÃÆÁÄ»¿ížß
        screenWidth = w;
        screenHeight = h;
        radius = w/(9*2 + 1);
    }

    public void startGame(){
        gameData.init();
        dot.setPos(40);
        dot.setIsSurrounded(false);
        invalidate();
    }

    /*draws the initial view of the board,
     * with all the dots. */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawRect(0, 0, screenWidth, screenHeight, background);

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {

                if(gameData.getNumByCoordinate(x, y) == 0){
                    if(y % 2 == 0){
                        canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius, movePaint);
                    }else{
                        canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius, movePaint);
                    }

                }else if(gameData.getNumByCoordinate(x, y) == 1){

                    if(y%2 == 0){
                        canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius, blockPaint);
                    }else{
                        canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius, blockPaint);
                    }

                }else if(gameData.getNumByCoordinate(x, y) == 2){

                    if(y%2 == 0){
                        canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius, dotPaint);

                        if(dot.getIsSurrounded()) canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius/2, dotIsSurrPaint);
                    }else{
                        canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius, dotPaint);

                        if(dot.getIsSurrounded())  canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius/2, dotIsSurrPaint);
                    }
                }
            }
        }
    }

    public static int count = 10;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() != MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }

        if(event.getY() < radius*18){
            if((int)(event.getY()/(radius*2))%2 == 0){
                if(event.getX()/(radius*2)<9){
                    clickX = (int)(event.getX()/(radius*2));
                    clickY = (int)(event.getY()/(radius*2));
                }
            }else{
                if(event.getX()/radius>1){
                    clickX = (int)((event.getX()-radius)/(radius*2));
                    clickY = (int)(event.getY()/(radius*2));
                }
            }

            System.out.println("clickX:" + clickX + "  clickY:" + clickY);

            if(gameData.getIsBlocked(clickX, clickY)){

                //increment the number of steps needed, to escape.
                count++;
                System.out.println(count);

                invalidate();
                gameComplete();

                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    private void gameComplete(){

        dot.setCircle(gameData.getCircle());
        int currentPos = dot.getPos();

        if(dot.escaped()){
            //if player looses
            //advance back to main screen
            Activity activity = (Activity) getContext();
            Intent data = new Intent();
            String text = "Game Over!";
            //---set the data to pass back---
            data.setData(Uri.parse(text));
            activity.setResult(RESULT_OK, data);
            //---close the activity---
            activity.finish();
        }else{
            if(dot.tryMove()){
                int nextPos = dot.getPos();
                gameData.setDotColour(currentPos, nextPos);
                invalidate();
            }else{
                //win
                Activity activity = (Activity) getContext();
                Intent data = new Intent();
                String text = "You Won!!!";
                //---set the data to pass back---
                data.setData(Uri.parse(text));
                activity.setResult(RESULT_OK, data);
                //---close the activity---
                activity.finish();
            }
        }
    }
}
