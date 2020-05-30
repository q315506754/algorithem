package com.jiangli.sort.daily;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *
 A * 寻路算法
  y
x 0   1    2  3   4   5
 1
 2          障碍
 3   起点   障碍    终点
 4          障碍
 5

  y
x 0   1    2  3   4   5
 1
 2   障碍障碍障碍
 3   起点    障碍    终点
 4   障碍障碍障碍
 5

 * @author Jiangli
 * @date 2020/5/22 11:11
 */
public class AFindPathTest {

    static class Ord{
        int x;
        int y;
        int step=0;
        int distance=0;
        int score=0;
        Ord prev = null;

        public Ord(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public Ord(int[] arr) {
            this(arr[0],arr[1]);
        }

        //@Override
        //public String toString() {
        //    Gson gson = new Gson();
        //    return gson.toJson(this);
        //}

        @Override
        public String toString() {
            return "Ord{" +
                    "x=" + x +
                    ", y=" + y +
                    ", step=" + step +
                    ", distance=" + distance +
                    ", score=" + score +
                    '}';
        }
    }

    public static Ord point(int x, int y) {
        return new Ord(x,y);
    }
    public static Ord point(int[] arr) {
        return new Ord(arr);
    }

    public static void main(String[] args) {
        //int[][] obstacles = new int[][]{
        //        {3,2}
        //        ,{3,3}
        //        ,{3,4}
        //};
        int[][] obstacles = new int[][]{
                {1,2}
                ,{2,2}
                ,{3,2}

                ,{3,3}

                ,{1,4}
                ,{2,4}
                ,{3,4}
        };
        int[][] directions = new int[][]{
                {0,1}
                ,{0,-1}
                ,{1,0}
                ,{-1,0}
        };
        int[] start = {1,3};
        int[] end = {5,3};

        //可到达
        LinkedList<Ord> openList = new LinkedList<>();
        openList.add(new Ord(start));

        //已到达
        LinkedList<Ord> closeList = new LinkedList<>();
        //closeList.add(new Ord(start));

        Ord endOrd = null;

        w:
        while (!openList.isEmpty()) {
            int i =  findLeastScore(openList);
            //openList.addAll(closeList);

            Ord ord = openList.remove(i);
            System.out.println("closest:"+ord);

            closeList.add(ord);
            //closeList.clear();

            //达到终点
            if (ord.x == end[0] && ord.y == end[1]  ) {
                endOrd = ord;
                break;
            }

            for (int[] direction : directions) {
                int nX = ord.x + direction[0];
                int nY = ord.y + direction[1];

                if (nX>=0 && nY >= 0
                        && !inObstacles(nX,nY,obstacles)
                        && !contains(openList,nX,nY)
                        && !contains(closeList,nX,nY)) {
                    Ord newOne = new Ord(nX,nY);
                    newOne.prev = ord;
                    newOne.step = ord.step + 1;
                    newOne.distance = calcDistance(newOne,point(end));
                    newOne.score = newOne.step + newOne.distance;
                    openList.add(newOne);

                    //达到终点
                    if (newOne.x == end[0] && newOne.y == end[1]  ) {
                        endOrd = newOne;
                        break w;
                    }
                }


            }
        }

        int n = 1;
        //for (Ord ord : closeList) {
        //    System.out.println((n++) +  " " + ord.x + "," + ord.y);
        //}

        while (endOrd != null) {
            System.out.println((n++) +  " " + endOrd.x + "," + endOrd.y);
            endOrd = endOrd.prev;
        }
    }

    private static boolean contains(List<Ord> openList, int nX, int nY) {
        for (Ord ord : openList) {
            if (nX == ord.x && nY == ord.y  ) {
                return true;
            }
        }
        return false;
    }

    private static boolean inObstacles(int nX, int nY, int[][] obstacles) {
        for (int[] obstacle : obstacles) {
            if (nX == obstacle[0] && nY == obstacle[1]  ) {
                return true;
            }
        }
        return false;
    }

    private static int calcDistance(Ord from, Ord to) {
        return Math.abs(to.x - from.x) +  Math.abs(to.y - from.y);
    }

    private static int findLeastScore(LinkedList<Ord> openList) {
        Integer idx = 0;
        for (int i = 0; i < openList.size(); i++) {
            Ord ord = openList.get(i);
            if (ord.score< openList.get(idx).score) {
                idx = i;
            }
        }
        return idx;
    }
}
