package com.jiangli.thread.main.fork;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class RecursiveTest2 {
    // 定义最小区间为10
    private final static int MAX_THRESHOLD = 10;

    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        int[] ints = {8, 3, 1, 4, 2, 6, 9};
        ForkJoinTask<int[]> future = forkJoinPool.submit(new MergeSortRecursiveTask(ints,0, ints.length-1));
        try {
            int[] result = future.get();
            System.out.println(Arrays.toString(result));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class MergeSortRecursiveTask extends RecursiveTask<int[]> {
        private int[] ints;
        // 起始
        private int start;
        // 结束
        private int end;

        public MergeSortRecursiveTask(int[] ints, int start, int end) {
            this.ints = ints;
            this.start = start;
            this.end = end;
        }

        @Override
        protected int[] compute() {
            // 如果起始和结束范围小于我们定义的区间范围，则直接计算
            if ((end - start) < 1) {
                return new int[]{ints[start]};
            } else {
                // 否则，将范围一分为二，分成两个子任务
                int middle = (start + end) / 2;
                MergeSortRecursiveTask leftTask = new MergeSortRecursiveTask(ints, start, middle);
                MergeSortRecursiveTask rightTask = new MergeSortRecursiveTask(ints, middle + 1, end);
                // 执行子任务
                leftTask.fork();
                rightTask.fork();


                // 汇总子任务
                int[] larr = leftTask.join();
                int[] rarr = rightTask.join();

                int[] ret = new int[larr.length+rarr.length];

                int i = 0;
                int j = 0;
                int count = 0;
                while (i < larr.length && j < rarr.length) {
                    if (larr[i] < rarr[j]) {
                        ret[count++] = larr[i++];
                    } else {
                        ret[count++] = rarr[j++];
                    }
                }

                while (i < larr.length) {
                    ret[count++] = larr[i++];
                }

                while (j < rarr.length) {
                    ret[count++] = rarr[j++];
                }

                return ret;
            }
        }
    }
}