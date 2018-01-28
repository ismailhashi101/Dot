package com.example.ismail.circlethedot;

import java.util.ArrayList;

/**
 * Created by ismail on 08/09/17.
 */

public class Dot {

    private int pos = 40;
    protected ArrayList<ArrayList<Integer>> edges;

    private boolean isSurrounded = false;

    private int[] circle = new int[81];

    public void setCircle(int[] circle) {
        System.arraycopy(circle, 0, this.circle, 0, circle.length);
        getDots();
    }

    public void getDots() {
        edges = new ArrayList<>(81);
        int i, j;
        for (int n = 0; n < 81; n++) {
            edges.add(new ArrayList<Integer>());
            i = n / 9; //y
            j = n % 9; //x
            if (j != 0 && circle[i * 9 + j - 1] != 1) edges.get(n).add(i * 9 + j - 1);

            if (i % 2 == 1 && circle[(i - 1) * 9 + j] != 1) edges.get(n).add((i - 1) * 9 + j);
            else if (i % 2 == 0 && i != 0 && j != 0 && circle[(i - 1) * 9 + j - 1] != 1) edges.get(n).add((i - 1) * 9 + j - 1);

            if (i % 2 == 1 && j != 8 && circle[(i - 1) * 9 + j + 1] != 1) edges.get(n).add((i - 1) * 9 + j + 1);
            else if (i % 2 == 0 && i != 0 && circle[(i - 1) * 9 + j] != 1) edges.get(n).add((i - 1) * 9 + j);

            if (j != 8 && circle[i * 9 + j + 1] != 1) edges.get(n).add(i * 9 + j + 1);

            if (i % 2 == 1 && j != 8 && circle[(i + 1) * 9 + j + 1] != 1) edges.get(n).add((i + 1) * 9 + j + 1);
            else if (i % 2 == 0 && i != 8 && circle[(i + 1) * 9 + j] != 1) edges.get(n).add((i + 1) * 9 + j);

            if (i % 2 == 1 && circle[(i + 1) * 9 + j] != 1) edges.get(n).add((i + 1) * 9 + j);
            else if (i % 2 == 0 && i != 8 && j != 0 && circle[(i + 1) * 9 + j - 1] != 1) edges.get(n).add((i + 1) * 9 + j - 1);
        }
    }

    public int getPos() {
        return pos;
    }

    protected void setPos(int n) {
        pos = n;
    }

    public boolean tryMove() {
        int n = next(pos);
        if (n == pos) return false;
        pos = n;
        return true;
    }

    /*public void close(int i, int j) {
        int v = i * 9 + j;
        for (int w : edges.get(v)) {
            edges.get(w).remove(Integer.valueOf(v));
        }
        edges.get(v).clear();
    }*/

    protected boolean escaped(int n) {
        return n < 9 || n > 71 || n % 9 == 0 || n % 9 == 8;
    }

    public boolean escaped() {
        return escaped(pos);
    }

    public int next(int n) {

        ArrayList<Integer> reachable = new ArrayList<Integer>();
        int[] orient = new int[81]; // ŒÇÂŒÌÓÅÜ·œÏòµÄµÚÒ»²œ
        for (int w : edges.get(n)) {  //Ñ­»·ÌíŒÓµÚÒ»²ãŒ¶ÉÏµÄ¿ÉÌÓÅÜÎ»ÖÃ
            if (escaped(w)) return w;
            reachable.add(w);
            orient[w] = w;
        }
        int num = 0;
        while (num < reachable.size()) {
            // ŽÓreachableÖÐÈ¡³öÏÂÒ»žönum£¬Œì²éÆäÁ¬œÓµÄžñ×Ó£¬Ò»žö²ãŒ¶Ò»žö²ãŒ¶µÄŒì²é£¬
            int v = reachable.get(num++);
            for (int w : edges.get(v)) {

                //ÅÐ¶ÏÒ»Ìõ¿ÉÓÃµÄÂ·Ïß£¬Ö±µœµœŽï×îÍâ²ã±ßœç£¬È»ºó·µ»ØžÃ×Ó²ãŒ¶±êŒÇµÄµÚÒ»²ãŒ¶µÄÎ»ÖÃ£šÆäÊµÊÇËÑË÷Áùžö·œÏòÉÏµœŽï±ßœçµÄ×îÐ¡Â·Ÿ¶£©
                if (escaped(w)) return orient[v];
                if (!reachable.contains(w)) {
                    reachable.add(w);  //œ«×Ó²ãŒ¶¿ÉÌÓÅÜµÄÎ»ÖÃÌíŒÓœøÈ¥£¬ÕâÀïÊÇ¹ØŒü
                    orient[w] = orient[v]; //œ«×Ó²ãŒ¶ÉÏµÄ·œÏò±êŒÇÎªµÚÒ»²ãŒ¶µÄÎ»ÖÃ
                }
            }
        }

        //±»°üÎ§ºó²Å»áÖŽÐÐÒÔÏÂŽúÂë
        if (reachable.size() == 0) return n; // Ò»²œ¶ŒÎÞ·š¶¯

        isSurrounded = true; // ÒÑ±»Î§×¡µ«ÈÔ¿É¶¯
        return reachable.get(0); // ÒÑ±»Î§×¡µ«ÈÔ¿É¶¯ ÔòËæ±ãÈ¡µÚÒ»žöÖµ
    }

    public boolean getIsSurrounded(){
        return isSurrounded;
    }

    public void setIsSurrounded(boolean bool){
        this.isSurrounded = bool;
    }

    /*public ArrayList<Integer> around(int n) {
        return edges.get(n);
    }*/
}
