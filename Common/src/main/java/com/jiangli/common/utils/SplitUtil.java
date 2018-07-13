package com.jiangli.common.utils;

/**
 * @author Jiangli
 * @date 2018/7/5 15:44
 */
public class SplitUtil {
    public interface SplitTask {
        void processPage(int from ,int to);
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
                task.processPage(tstart,tto);
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
        SplitUtil.splitByPageNum(1,1000001,10,(from, to) -> {
            new Thread(() -> {
                SplitUtil.splitByPageSize(from, to, 1000, (from1, to1) -> {
                    System.out.println(Thread.currentThread()+" "+from1 +" -> "+ to1);
                });
            }).start();
        });
    }
}
