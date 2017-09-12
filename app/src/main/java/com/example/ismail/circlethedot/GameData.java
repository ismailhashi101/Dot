package com.example.ismail.circlethedot;

/**
 * Created by ismail on 08/09/17.
 */

public class GameData {

    private int[] circle = new int[9*9];

    //DotµÄ³õÊŒÎ»ÖÃ
    private int dotX;
    private int dotY;

    //Ëæ»úÉú³ÉºìµãžöÊý
    private int redDotNums = 15;

    //ŒÇÂŒËæ»ú15žöºìµãµÄÎ»ÖÃ
    int[] randomOne = new int[redDotNums];

    public GameData(){
        init();

    }

    //³õÊŒ»¯Êý×é
    public void init(){

        //³õÊŒ»¯ÊýÖµ
        dotX = 4;
        dotY = 4;

        //Éú³É15žöºìµãÎ»ÖÃ
        generateRandomNums();

        for (int i = 0; i < circle.length; i++) {
            if(i == (dotY*9 + dotX)){
                //œ«DotµÄ×ø±êÖµÉèÖÃÎª2
                circle[i] = 2;

            }else{

                boolean flag = true;

                for (int j : randomOne) {
                    if(i == j){

                        flag = false;
                        break;

                    }
                }

                if(flag){

                    //¿É×ßµÄµãµÄ×ø±êÖµÉèÖÃÎª0
                    circle[i] = 0;
                }else{

                    //²»¿É×ßµÄµãµÄ×ø±êÉèÖÃÎª1
                    circle[i] = 1;
                }

            }

        }
    }

    //ŒÇÂŒÓÃ»§µã»÷µÄÔ²
    private void setBlockNum(int x, int y){

        circle[y*9 + x] = 1;
    }

    public boolean getIsBlocked(int x, int y){
        if(circle[y*9 + x] == 0){

            setBlockNum(x, y);
            return true;

        }else{

            return false;
        }
    }

    //žùŸÝ×ø±êÈ¡µÃ×ø±êµÄÖµ
    public int getNumByCoordinate(int x, int y){

        return circle[y*9 + x];
    }

    public int[] getCircle() {
        return circle;
    }

    //Ëæ»úÉú³É15žöºìµã
    public void generateRandomNums(){

        for (int i = 0; i < randomOne.length; i++) {

            //Éú³É0-80µÄËæ»úÊý£¬¶ÔÓŠcircleµÄ×ø±ê
            int rd = (int)(Math.random()*81);

            //ÅÅ³ýDotµÄÎ»ÖÃ
            if(rd == (dotY*9 + dotX)){
                i--;

            }else{

                if(i == 0){
                    //µÚÒ»Î»²»ÓÃÅÐ¶ÏÖØžŽ
                    randomOne[i] = rd;

                }else{
                    boolean same = false;

                    for (int j = 0; j < i; j++) {
                        //È¥µôÖØžŽµÄÖµ
                        if(rd == randomOne[j]){
                            i--;
                            same = true;
                            break;

                        }

                    }
                    //Èç¹ûÓëÇ°ÃæÉú³ÉµÄËæ»úÖµ²»ÖØžŽÔòÌíŒÓ
                    if(!same){
                        randomOne[i] = rd;
                    }
                }
            }
        }

    }

    public void setDotCoor(int currentPos, int nextPos){
        circle[currentPos] = 0;
        circle[nextPos] = 2;

    }
}
