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

    //»­±Ê
    private Paint background;
    private Paint movePaint;
    private Paint blockPaint;
    private Paint dotPaint;
    private Paint dotIsSurrPaint;

    //ÆÁÄ»¿ížß
    private int screenWidth;
    private int screenHeight;

    //Ô²°ëŸ¶
    private int radius;

    //Ñ¡ÔñµÄ×ø±ê
    private int clickX = 0;
    private int clickY = 0;

    private GameData gameData;
    private Dot dot;
    private Context context;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //³õÊŒ»¯
        gameData = new GameData();
        dot = new Dot();

        //±³Ÿ°É«»­±Ê
        background = new Paint();
        background.setColor(ContextCompat.getColor(context, R.color.background));

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

        //»ÖžŽ³õÊŒÖµ
        gameData.init();
        dot.setPos(40);
        dot.setIsSurrounded(false);

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //»­³ö±³Ÿ°É«
        canvas.drawRect(0, 0, screenWidth, screenHeight, background);

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                //ÎŽµã»÷¹ýµÄÔ²
                if(gameData.getNumByCoordinate(x, y) == 0){
                    if(y%2 == 0){
                        //ÅŒÊýÐÐ
                        canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius, movePaint);

                    }else{

                        //ÆæÊýÐÐÑÓºóÒ»žö°ëŸ¶
                        canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius, movePaint);
                    }

                }else if(gameData.getNumByCoordinate(x, y) == 1){

                    if(y%2 == 0){
                        //ÅŒÊýÐÐ
                        canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius, blockPaint);

                    }else{

                        //ÆæÊýÐÐÑÓºóÒ»žö°ëŸ¶
                        canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius, blockPaint);
                    }

                }else if(gameData.getNumByCoordinate(x, y) == 2){

                    //ÖÐÐÄÔ²µã
                    if(y%2 == 0){
                        //ÅŒÊýÐÐ
                        canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius, dotPaint);

                        //ÅÐ¶ÏÊÇ·ñÒÑŸ­±»Î§×¡
                        if(dot.getIsSurrounded())
                            canvas.drawCircle(radius*(x*2 + 1), radius*(y*2 + 1), radius/2, dotIsSurrPaint);

                    }else{

                        //ÆæÊýÐÐÑÓºóÒ»žö°ëŸ¶
                        canvas.drawCircle(radius*(x*2 + 2), radius*(y*2 + 1), radius, dotPaint);

                        //ÅÐ¶ÏÊÇ·ñÒÑŸ­±»Î§×¡
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

        //ÉèÖÃ¿Éµã»÷·¶Î§£¬²»ÄÜÓÃÆÁÄ»¿í¶ÈµÄÖµ£šŽó1žö°ëŸ¶£©
        if(event.getY() < radius*18){

            if((int)(event.getY()/(radius*2))%2 == 0){
                if(event.getX()/(radius*2)<9){
                    //ÅŒÊýÐÐ
                    clickX = (int)(event.getX()/(radius*2));
                    clickY = (int)(event.getY()/(radius*2));
                }
            }else{
                if(event.getX()/radius>1){
                    //ÆæÊýÐÐ
                    clickX = (int)((event.getX()-radius)/(radius*2));
                    clickY = (int)(event.getY()/(radius*2));
                }
            }

            System.out.println("clickX:" + clickX + "  clickY:" + clickY);

            //µã»÷ºóÉèÖÃ×ø±êÖµÎª1
            if(gameData.getIsBlocked(clickX, clickY)){

                invalidate();

                //ÅÐ¶ÏÓÎÏ·ÊÇ·ñœáÊø
                gameComplete();

                return true;
            }else{

                return false;
            }

        }else{

            return false;
        }

    }

    //ÅÐ¶ÏÓÎÏ·œáÊø
    private void gameComplete(){

        //œ«µ±Ç°µØÍŒÊýŸÝŽ«Èë
        dot.setCircle(gameData.getCircle());
        int currentPos = dot.getPos();  //È¡µÃµ±Ç°Î»ÖÃ
        //ÅÐ¶Ïµ±Ç°DotÎ»ÖÃÊÇ·ñÒÑÌÓÍÑ
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
