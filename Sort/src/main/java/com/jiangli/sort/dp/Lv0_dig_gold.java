package com.jiangli.sort.dp;

/**

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
 *
 *

  类似：
 0-1背包问题
 有一个容量为 V 的背包，和一些物品。这些物品分别有两个属性，体积 w 和价值 v，
 每种物品只有一个。要求用这个背包装下价值尽可能多的物品，求该最大价值，背包可以不被装满。

完全背包问题
 我们扩展0-1背包问题，使每种物品的数量无限增加，便得到完全背包问题：
 有一个容积为 V 的背包，同时有 n 个物品，每个物品均有各自的体积 w 和价值 v，每个物品的数量均为无限个，求使用该背包最多能装的物品价值总和。

 多重背包
 多重背包问题介于 0-1 背包和完全背包之间：有容积为V的背包，给定一些物品，每种物品包含体积 w、价值 v、和数量 k，求用该背包能装下的最大价值总量。

 */
public class Lv0_dig_gold {
    static int loopTimes = 0;

    public static int fun(int n,int w,int[] p,int[] g) {
        loopTimes++;
        if (n == 0 || w == 0) {
            return 0;
        }
        if ( w < p[n-1]) {
            return fun(n-1,w,p,g);
        }

        return Math.max(
                fun(n-1,w-p[n-1],p,g)+g[n-1]
                ,fun(n-1,w,p,g)
        );
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
