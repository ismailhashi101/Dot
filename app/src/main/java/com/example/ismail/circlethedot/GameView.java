package com.example.ismail.circlethedot;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

        gameData = new GameData();
        dot = new Dot();

        background = new Paint();
        background.setColor(ContextCompat.getColor(context, R.color.background));

        movePaint = new Paint();
        movePaint.setColor(ContextCompat.getColor(context, R.color.movePaint));
        movePaint.setAntiAlias(true);

        blockPaint = new Paint();
        blockPaint.setColor(ContextCompat.getColor(context, R.color.blockPaint));
        blockPaint.setAntiAlias(true);

        dotPaint = new Paint();
        dotPaint.setColor(ContextCompat.getColor(context, R.color.dotPaint));
        dotPaint.setAntiAlias(true);

        dotIsSurrPaint = new Paint();
        dotIsSurrPaint.setColor(ContextCompat.getColor(context, R.color.dotIsSurrPaint));
        dotIsSurrPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // TODO Auto-generated method stub
        super.onSizeChanged(w, h, oldw, oldh);

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, screenWidth, screenHeight, background);

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
               
                if(gameData.getNumByCoordinate(x, y) == 0){
                    if(y%2 == 0){
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
                        if(dot.getIsSurrounded())
                            canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius/2, dotIsSurrPaint);
                    }else{
                        canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius, dotPaint);
                        if(dot.getIsSurrounded())
                            canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius/2, dotIsSurrPaint);
                    }
                }
            }
        }

    }

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

            //lose
            new AlertDialog.Builder(context)
                    .setMessage("Game Over£¡")
                    .setPositiveButton("È·¶š", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                            startGame();
                        }
                    })
                    .setCancelable(false).show();

        }else{
            //ÅÐ¶ÏDotÊÇ·ñ»¹ÓÐÂ·Ïß¿É×ß
            if(dot.tryMove()){

                //Èç¹û¿É×ß£štryMoveÖÐÒÑŸ­œ«ÏÂÒ»žöÎ»ÖÃž³Öµžøµ±Ç°Î»ÖÃÁË£©£¬ÔòÈ¡µÃÏÂÒ»žöÎ»ÖÃ
                int nextPos = dot.getPos();
                gameData.setDotCoor(currentPos, nextPos);
                invalidate();

            }else{
                //win
                new AlertDialog.Builder(context)
                        .setMessage("¹§Ï²Äã£¡ÄãÓ®ÁË£¡")
                        .setPositiveButton("È·¶š", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                startGame();
                            }
                        })
                        .setCancelable(false).show();
            }
        }
    }
}
