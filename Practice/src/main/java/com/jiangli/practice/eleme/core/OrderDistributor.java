package com.jiangli.practice.eleme.core;

import com.jiangli.common.utils.ArrayUtil;

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
        return new OrderDistributorIterator();
    }

    class OrderDistributorIterator implements Iterator<int[][]>{
        private int maxC=pool.length-orderNum+1;
        private int curC=1;
        private Iterator<int[]> combination;
        private Iterator<int[][]> children;
        private int[] curCombine;

        public OrderDistributorIterator() {
            if (orderNum==1) {
                maxC=curC=pool.length;
            }
             combination = new Combination(curC,pool).iterator();
        }

        private void refreshChildrenIter() {
            if (combination.hasNext()) {
                curCombine = combination.next();

                int[] rest = ArrayUtil.substractBiSearch(pool, curCombine);
                if (rest.length>0) {
                    children = new OrderDistributor(orderNum-1,rest).iterator();
                }
            }

        }

        @Override
        public boolean hasNext() {
            if (orderNum==0) {
                return false;
            }
            boolean ret = combination.hasNext() || (children!=null && children.hasNext());

            return ret;
        }

        @Override
        public int[][] next() {
            if (children== null) {
                refreshChildrenIter();
            }
            else if (children != null && !children.hasNext()) {
                refreshChildrenIter();
            }

            int[][] childrenDist = null;
            int[][] ret = null;
            if (children!=null && children.hasNext()) {
                childrenDist=this.children.next();
            }

            if (childrenDist!=null) {
                ret = new int[childrenDist.length+1][];

                for (int i = 0; i < childrenDist.length; i++) {
                    ret[i+1]=childrenDist[i];
                }
            }else {
                ret = new int[1][];
            }

            ret[0]=curCombine;

            if (!combination.hasNext()  && curC<maxC) {
                curC++;
                combination = new Combination(curC,pool).iterator();
            }

            return ret;
        }
    }


}
