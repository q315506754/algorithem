package com.jiangli.sort.algo;

import java.util.Objects;

/**
 * @author Jiangli
 * @date 2018/6/17 9:45
 */
public class PicRecombination {
    public static int[][] metric(int n) {
        int[][] input = new int[n][];
        int c= 1;
        for (int i = 0; i < n; i++) {
            input[i] = new int[n];
            for (int j = 0; j < n; j++) {
                input[i][j] = c++;
            }
        }
        return input;
    }
    public static void print(int[][] input) {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                System.out.print(input[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] input = {{2,9,1},{5,8,7},{4,3,6}};
        int[][] output = metric(3);
//        print(input);
//        print(output);

        move1(input,output);
    }
    private static Point calc(Point from,Point offset) {
        return new Point(from.x+offset.x,from.y+offset.y);
    }
    private static Point readablePoint(Point from) {
        return calc(from,new Point(1,1));
    }

    private static void swap(int[][] input,Point from,Point to) {
        int old_to = input[to.x][to.y];
//        int old_from = input[from.x][from.y];
        input[to.x][to.y]=input[from.x][from.y];
        input[from.x][from.y] = old_to;

        System.out.println("move:"+readablePoint(from)+" -> " + readablePoint(to) );
        print(input);
    }

    static class Point{
        public int x;
        public int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public String toString() {
            return "(" + x +
                    "," + y +
                    ')';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }
        public boolean equals(int  x,int y) {
            return equals(new Point(x, y));
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private static Point find(int[][] input, int v) {
        int f_m=0;
        int f_n=0;
        for (f_m = 0; f_m < input.length; f_m++) {
            for (f_n = 0; f_n < input[f_m].length; f_n++) {
                if (input[f_m][f_n] == v) {
                    return new  Point(f_m,f_n);
                }
            }
        }
        throw new IllegalArgumentException("can't find v:"+v);
    }

    private static int weight(int[][] input, int[][] output) {
        int times=0;
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                if (output[i][j] == input[i][j]) {
                    times++;
                }
            }
        }
        return times;
    }

    //iteration move
    private static void move1(int[][] input, int[][] output) {
        int times=0;
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                int v = output[i][j];

                Point find = find(input, v);

                //move
                if (i!=find.x || j!=find.y) {
                    swap(input,find,new Point(i,j));
                    times++;
                }

            }
            System.out.println();
        }
        System.out.println("total times:"+times);
    }


    //weight move
    private static void move2(int[][] input, int[][] output) {
        int times=0;
        for (int i = 0; i < output.length; i++) {
            for (int j = 0; j < output[i].length; j++) {
                int v = output[i][j];

                Point find = find(input, v);

                //move
                if (i!=find.x || j!=find.y) {
                    swap(input,find,new Point(i,j));
                    times++;
                }

            }
            System.out.println();
        }
        System.out.println("total times:"+times);
    }

}
