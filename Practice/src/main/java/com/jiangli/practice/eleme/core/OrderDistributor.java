package com.jiangli.practice.eleme.core;

import java.util.Iterator;

/**
 * Created by Jiangli on 2017/4/18.
 */
public class OrderDistributor implements Iterable<int[][]>{
    private final int orderNum;
    private final int[] pool;


    public OrderDistributor(int orderNum, int[] pool) {
        this.orderNum = orderNum;
        this.pool = pool;
    }

    @Override
    public Iterator<int[][]> iterator() {
        int[][] orderIndexes=new int[orderNum][];

        return new OrderDistributorIterator();
    }

    class OrderDistributorIterator implements Iterator<int[][]>{
        private int max;

        public OrderDistributorIterator() {
            if (orderNum==1) {

            } else {
                max=pool.length-orderNum+1;
            }
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public int[][] next() {

            return new int[0][];
        }
    }


}
