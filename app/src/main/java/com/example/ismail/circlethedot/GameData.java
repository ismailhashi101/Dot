package com.example.ismail.circlethedot;

/**
 * Created by ismail on 08/09/17.
 */

public class GameData {

    private int[] circle = new int[9*9];

    //coordinates
    private int dotX;
    private int dotY;

    //# of initial surrounding dots
    private int redDotNums = 15;

    //array of rand
    int[] randomOne = new int[redDotNums];

    public GameData(){
        init();
    }

    //setting up the dots positioning on the board
    public void init(){

        //(dotY * 9 + dotX) == 40 (middle of the board, where the blue dot starts)
        dotX = 4;
        dotY = 4;

        //generates random numbers, to positions the red dots accordingly on the board.
        generateRandomNums();

        for (int i = 0; i < circle.length; i++) {
            if(i == (dotY*9 + dotX)){
                //if positioned at the center of the board, mark it with the number 2.
                //blue dot
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
                    //empty dot is marked with a 0.
                    circle[i] = 0;
                }else{
                    //red dots is marked with a 1.
                    circle[i] = 1;
                }

            }
        }
    }

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

    //determines if the dot is either a blue, red or white dot.
    public int getNumByCoordinate(int x, int y){
        return circle[y*9 + x];
    }

    public int[] getCircle() {
        return circle;
    }

    public void generateRandomNums(){

        for (int i = 0; i < randomOne.length; i++) {

            //generates a random number between 0 to 81
            int rd = (int)(Math.random()*81);

            //not on the middle of the board
            if(rd == (dotY*9 + dotX)){
                i--;
            }else{
                if(i == 0){
                    randomOne[i] = rd;
                }else{
                    boolean same = false;

                    for (int j = 0; j < i; j++) {
                        if(rd == randomOne[j]){
                            i--;
                            same = true;
                            break;
                        }
                    }

                    if(!same){
                        randomOne[i] = rd;
                    }
                }
            }
        }
    }

    public void setDotColour(int currentPos, int nextPos){
        circle[currentPos] = 0;
        circle[nextPos] = 2;
    }
}
