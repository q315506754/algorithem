package com.jiangli.sort.daily;

import java.util.*;

/**
 *

 火影手游花灯小游戏算法

 花灯如图

 例1：
 0 0 1 1 0 0
 0 1 1 1 1 0
 0 1 1 1 1 0

 例2：
 0 0 1 1 0 0
 0 1 1 1 1 0
 1 2 1 2 1 1


 0是不可点击区域,1是可点击区域；
 每次点击后该格子和其上下左右可点击格子的取值会在[1,2]之间反转。

 求：
 输出一个点击序列，使得最后花灯中的可点区域全翻转成2

 */
public class NarutoLanternTest {
    static int[][] directions = new int[][]{
            {0,1}
            ,{0,-1}
            ,{1,0}
            ,{-1,0}
    };

    static class Ord implements Comparable<Ord>{
        int x;
        int y;
        int total2;//2的数量
        Ord parent;//

        @Override
        public int compareTo(Ord o) {
            return Integer.valueOf(total2).compareTo(o.total2);
        }

        public Ord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" +
                    "" + x +
                    "," + y +
                    ')';
        }
    }

    /**


     设点击序列有1,2,3...n个

     问题转换成了Cn的问题


     */
    public static List<Ord> calcSeq(int[][] input) {
        int total = calcFinal2(input);

        for (int num = 2; num <= total; num++) {
            //System.out.println("序列数:"+num);
            int n = 1;

            //加入栈中
            int add = num;
            Ord prev = null;

            List<Ord> seq = new ArrayList<>();
            while (add-->0) {
                Ord oldPrev = prev;
                prev = getNext(input,prev);
                prev.parent = oldPrev;
                seq.add(prev);

                //点击
                click(input,prev);
            }

            //System.out.println(seq);

            while (true) {
                //System.out.println("套用:"+n+" "+seq);
                n++;

                int current2 = calcCurrent2(input);

                if (total == current2) {
                    for (Ord ord : seq) {
                        //还原
                        click(input,ord);
                    }
                    return seq;
                }

                if(!tryMoveNextAndClick(input,seq)){
                    break;
                }
            }
        }

        return null;
    }

    private static boolean tryMoveNextAndClick(int[][] input, List<Ord> seq) {
        for (Ord ord : seq) {
            //还原
            click(input,ord);
        }

        boolean ret = tryMoveNextAndClickone(input, seq, seq.size() - 1);

        if (ret) {
            //套用
            for (Ord ord : seq) {
                //还原
                click(input,ord);
            }
        }

        return ret;
    }


    private static boolean tryMoveNextAndClickone(int[][] input, List<Ord> seq, int  n) {
        if (n < 0 ) {
            return false;
        }

        Ord prev = seq.get(n);
        for (int i = n; i < seq.size(); i++) {
            Ord current = seq.get(i);
            Ord next = getNext(input, prev);

            if (next == null) {
                return tryMoveNextAndClickone(input,seq,n-1);
            }

            current.x = next.x;
            current.y = next.y;

            prev = current;
        }

        return true;
    }

    private static Ord getNext(int[][] input, Ord prev) {
        if (prev != null) {
            for (int i = prev.x; i < input.length; i++) {
                for (int j = 0; j < input[i].length; j++) {
                    if (input[i][j] == 0 ) {
                        continue;
                    }

                    if (i == prev.x && j <= prev.y) {
                        continue;
                    }

                    return new Ord(i,j);
                }
            }
        } else {
            for (int i = 0; i < input.length; i++) {
                for (int j = 0; j < input[i].length; j++) {
                    if (input[i][j] == 0 ) {
                        continue;
                    }

                    return new Ord(i,j);
                }
            }
        }

        return null;
    }

    private static int calcFinal2(int[][] input) {
        int total = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] > 0) {
                    total++;
                }
            }
        }

        return total;
    }
    private static int calcCurrent2(int[][] input) {
        int total = 0;

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (input[i][j] == 2) {
                    total++;
                }
            }
        }

        return total;
    }

    /**
    normal 9:[(0,2), (1,2), (2,0)]
    normal 10:[(0,2), (1,1), (1,2), (2,0), (2,1)]

    chal 1:
    chal 2:[(0,0), (1,1), (2,0), (2,1), (2,2)]
    chal 3:[(0,0), (0,1), (1,2), (2,0), (2,2)]
    chal 4:[(1,0), (1,1), (1,2), (2,0), (2,1)]
    chal 5:
    chal 6:[(0,2), (1,2), (2,2)]
    chal 7:[(1,0), (1,2), (2,2)]
    chal 8:[(1,2), (2,1), (2,2)]
    chal 9:[(0,0), (0,2), (1,0), (1,2), (2,0)]
    chal 10:[(0,0), (1,0), (1,1), (2,0), (2,1)]
    chal 11:[(1,2), (3,0), (3,3)]
    chal 12:
    chal 13:[(0,2), (0,3), (1,2)]
    chal 14:[(0,2), (2,1), (3,0), (3,2), (3,3)]
    chal 15:[(0,0), (0,1), (0,2), (0,3), (1,2), (2,3), (3,1)]
    chal 16:[(0,1), (2,1), (3,0)]
    chal 17:[(0,1), (1,4), (2,2), (4,0), (4,4)]
    chal 18:[(0,0), (0,4), (1,3), (2,3), (3,1), (3,2), (3,3), (4,0), (4,1), (4,2), (4,4)]
    chal 19:[(0,1), (0,3), (1,2), (1,3), (2,1), (2,2), (2,3), (3,2), (3,3)]
    chal 20:[(1,4), (2,3), (4,2), (4,5), (5,5)]
    chal 21:[(0,0), (0,1), (0,2), (0,5), (1,3), (1,4), (3,0), (3,1), (5,3)]

     */
    public static void main(String[] args) {
        //int[][] input = new int[][]{
        //        { 0 ,0 ,1, 1, 0, 0}
        //        ,{ 0 ,1 ,1, 1, 1, 0}
        //        ,{ 0 ,1 ,1, 1, 1, 0}
        //};
        //int[][] input = new int[][]{
        //        { 0 ,0 ,1, 1, 0, 0}
        //        ,{ 0 ,1 ,1, 1, 1, 0}
        //        ,{ 1 ,2 ,1, 2, 1, 1}
        //};
        int[][] input = new int[][]{
                { 2 ,1 ,2,2,2,1}
                ,{ 1 ,1 ,2,2,2,2}
                ,{ 1 ,1 ,2,1,1,2}
                ,{ 2 ,2 ,1,2,2,2}
                ,{ 1 ,1 ,2,1,2,2}
                ,{ 2 ,2 ,1,1,1,2}
        };

        //{ 1 ,1 ,1,1}
        //,{ 1 ,1 ,1,1}
        //,{ 1 ,1 ,1,1}
        //,{ 1 ,1 ,1,1}

        //{ 1 ,1 ,1,1,1}
        //,{ 1 ,1 ,1,1,1}
        //,{ 1 ,1 ,1,1,1}
        //,{ 1 ,1 ,1,1,1}
        //,{ 1 ,1 ,1,1,1}

        //{ 1 ,1 ,1,1,1,1}
        //,{ 1 ,1 ,1,1,1,1}
        //,{ 1 ,1 ,1,1,1,1}
        //,{ 1 ,1 ,1,1,1,1}
        //,{ 1 ,1 ,1,1,1,1}
        //,{ 1 ,1 ,1,1,1,1}

        //{ 2 ,2 ,2,2}
        //,{ 2 ,2 ,2,2}
        //,{ 2 ,2 ,2,2}
        //,{ 2 ,2 ,2,2}

        //{ 2 ,2 ,2,2,2}
        //,{ 2 ,2 ,2,2,2}
        //,{ 2 ,2 ,2,2,2}
        //,{ 2 ,2 ,2,2,2}
        //,{ 2 ,2 ,2,2,2}

        //{ 2 ,2 ,2,2,2,2}
        //,{ 2 ,2 ,2,2,2,2}
        //,{ 2 ,2 ,2,2,2,2}
        //,{ 2 ,2 ,2,2,2,2}
        //,{ 2 ,2 ,2,2,2,2}
        //,{ 2 ,2 ,2,2,2,2}



        List<Ord> ords = calcSeq(input);
        System.out.println(ords);

        //模拟点击
        //clicks(
        //        input
        //        ,new Ord(1,2)
        //        ,new Ord(1,3)
        //        ,new Ord(2,2)
        //        ,new Ord(2,3)
        //);
        clicks(
                input
                ,ords.toArray(new Ord[ords.size()])
        );
    }

    private static void clicks(int[][] input, Ord... points) {
        System.out.println("initial...");

        print(input);
        int c = 1;
        for (Ord point : points) {
            click(input, point);
            System.out.println("after click "+ c ++ + " " + point);
            print(input);
        }
    }

    private static void click(int[][] input, Ord point) {
        int x = point.x;
        int y = point.y;

        input[x][y] = Math.abs(input[x][y]-2)+1;

        for (int[] ints : directions) {
            x = point.x+ints[0];
            y = point.y+ints[1];
            if (x >=0 && y >=0 && x <input.length && y<input[0].length &&  input[x][y] > 0) {
                input[x][y] = Math.abs(input[x][y]-2)+1;
            }
        }
    }

    private static void print(int[][] input) {
        for (int[] ints : input) {
            System.out.println(Arrays.toString(ints));
        }
    }

}

