package com.jiangli.thread.main.fork;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

public class RecursiveActionTest {
    public static void main(String[] args) {
        final ForkJoinPool forkJoinPool = new ForkJoinPool();

        int[] ints = {8, 3, 1, 4, 2, 6, 9};
        ForkJoinTask future = forkJoinPool.submit(new MergeSortRecursiveTask(ints,0, ints.length-1));
        try {
            Object o = future.get();
            System.out.println(o);
            System.out.println(Arrays.toString(ints));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class MergeSortRecursiveTask extends RecursiveAction {
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
        protected void compute() {
            // 如果起始和结束范围小于我们定义的区间范围，则直接计算
            if ((end - start) < 1) {
                //return new int[]{ints[start]};
            } else {
                // 否则，将范围一分为二，分成两个子任务
                int middle = (start + end) / 2;
                MergeSortRecursiveTask leftTask = new MergeSortRecursiveTask(ints, start, middle);
                MergeSortRecursiveTask rightTask = new MergeSortRecursiveTask(ints, middle + 1, end);

                invokeAll(leftTask,rightTask);

                // 汇总子任务
                int[] ret = new int[end - start + 1];

                int i = start;
                int j = middle + 1;
                int count = 0;
                while (i <= middle && j <= end) {
                    if (ints[i] < ints[j]) {
                        ret[count++] = ints[i++];
                    } else {
                        ret[count++] = ints[j++];
                    }
                }

                while (i <=middle) {
                    ret[count++] = ints[i++];
                }

                while (j <= end) {
                    ret[count++] = ints[j++];
                }

                for (int k = 0; k < ret.length; k++) {
                    ints[k + start] = ret[k];
                }
            }
        }
    }
}