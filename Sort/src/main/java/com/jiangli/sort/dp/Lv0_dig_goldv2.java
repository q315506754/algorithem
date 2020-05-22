package com.jiangli.sort.dp;

/**
 *
 * 挖金矿问题,g座金矿，每座需要不同的人员p，
 * 给定n座矿，w名成员，最多能挖多少矿
 *
 * 状态转移：
 *               挖                不挖
 * F(n,w)=max( F(n-1,w-p[n-1]) ,F(n-1,w) )
 *
 * 边界：
 * n=0 or w=0 代表人分完了
 * w<p[n-1] 人不够挖这个矿，直接跳过
 */
public class Lv0_dig_goldv2 {
    static int loopTimes = 0;

    public static int fun(int n,int w,int[] p,int[] g) {
        loopTimes++;

        int[][] arr = new int[n][w];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < w; j++) {
                if (i-1<0) {
                    if (j+1 < p[i]) {
                        arr[i][j] = 0;
                    } else {
                        arr[i][j] = g[i];
                    }
                    continue;
                }

                if (j+1 < p[i] || j < p[i]) {
                    arr[i][j] = arr[i-1][j];
                } else {
                    arr[i][j] = Math.max(
                         arr[i-1][j-p[i]]+g[i]
                         ,arr[i-1][j]
                    );
                }
            }
        }

        return arr[n-1][w-1];
    }

    public static void main(String[] args) {
        //含金量
        int[] g = new int[]{400,500,200,300,350};
        //需要多少人
        int[] p = new int[]{5,5,3,4,3};

        System.out.println(fun(g.length,10,p,g));
        System.out.println(fun(g.length,20,p,g));
        System.out.println(loopTimes);
    }

}
