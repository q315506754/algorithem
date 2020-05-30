package com.jiangli.common.utils;

/**
 * @author Jiangli
 * @date 2018/7/5 15:44
 */
public class SplitUtil2 {
    public interface SplitTask {
        void processPage(int from ,int to,int pageNum);
    }

    public static void splitByPageSize(int from ,int to,int pageSize,SplitTask task) {
        if (from < to) {
            int n = to - from + 1;
            int pageNum = n % pageSize == 0 ? n / pageSize : n / pageSize + 1;

            for (int i = 0; i < pageNum; i++) {
                int tstart = from + i * pageSize;
                int tto = tstart + pageSize - 1;
                if (tto > to) {
                    tto = to;
                }
                task.processPage(tstart,tto,i);
            }
        }
    }
    public static void splitByPageNum(int from ,int to,int pageNum,SplitTask task) {
        if (from < to) {
            int n = to - from + 1;
            int pageSize = n % pageNum == 0 ? n / pageNum : n / pageNum + 1;

            splitByPageSize(from,to,pageSize,task);
        }
    }


    public static void main(String[] args) {
        SplitUtil2.splitByPageSize(1, 3657, 50, (from1, to1,pageNum1) -> {
            System.out.println(Thread.currentThread()+"当前页码:"+pageNum1+" "+from1 +" -> "+ to1);
        });
        //SplitUtil2.splitByPageSize(1, 48, 50, (from1, to1,pageNum1) -> {
        //    System.out.println(Thread.currentThread()+"当前页码:"+pageNum1+" "+from1 +" -> "+ to1);
        //});
        //SplitUtil2.splitByPageSize(1, 0, 50, (from1, to1,pageNum1) -> {
        //    System.out.println(Thread.currentThread()+"当前页码:"+pageNum1+" "+from1 +" -> "+ to1);
        //});

        //SplitUtil2.splitByPageNum(1,1000001,10,(from, to,pageNum) -> {
        //    //new Thread(() -> {
        //        SplitUtil2.splitByPageSize(from, to, 1000, (from1, to1,pageNum1) -> {
        //            System.out.println(Thread.currentThread()+"当前页码:"+pageNum1+" "+from1 +" -> "+ to1);
        //        });
        //    //}).start();
        //});
    }
}
