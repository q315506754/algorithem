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
public class Lv0_dig_goldv3 {
    static int loopTimes = 0;

    public static int fun(int n,int w,int[] p,int[] g) {
        loopTimes++;

        int[] arr = new int[w+1];

        for (int i = 0; i < n; i++) {
            for (int j = w; j >0; j--) {
                if (j >= p[i]) {
                    arr[j] = Math.max(arr[j-p[i]]+g[i],arr[j]);
                }
            }
        }

        return arr[w];
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
