package com.jiangli.practice.eleme.core;

import com.jiangli.common.utils.ArrayUtil;
import org.springframework.util.Assert;

import java.util.Iterator;

/**
 * 排列
 * @author Jiangli
 * @date 2017/4/20 9:01
 */
public class ArrangementSupport implements  Iterable<int[]>{
    private final int chooseNum;
    private final int poolLength;
    private final int[] pool;

    public ArrangementSupport(int chooseNum, int poolLength) {
        Assert.isTrue(chooseNum>=0);
        Assert.isTrue(poolLength>=0);
//        Assert.notNull(pool);

        this.chooseNum = chooseNum;
        this.poolLength = poolLength;
        this.pool = ArrayUtil.newArray(poolLength,0);
    }

    private ArrangementSupport(int chooseNum, int[] pool) {
        Assert.isTrue(chooseNum>=0);
//        Assert.isTrue(poolLength>=0);
        Assert.notNull(pool);

        this.chooseNum = chooseNum;
        this.pool = pool;
        this.poolLength = pool.length;
    }

    @Override
    public Iterator<int[]> iterator() {
        if (chooseNum<=poolLength) {
            return new ArrangementSupportNormalIterator();
        }else {
            return new ArrangementSupportNegativeIterator();
        }
    }

    class ArrangementSupportNegativeIterator implements Iterator<int[]>{
        private  Iterator<int[]> children;
        private boolean zeroInit=false;
        public ArrangementSupportNegativeIterator() {
            children = new ArrangementSupport(poolLength,chooseNum).iterator();
        }

        @Override
        public boolean hasNext() {
            if (poolLength==0) {
                return !zeroInit;
            }
            return children.hasNext();
        }

        @Override
        public int[] next() {
            int[] ret = new int[chooseNum];
            ArrayUtil.init(ret,-1);

            if (poolLength==0) {
                zeroInit=true;
                return ret;
            }

            int[] next = children.next();

            for (int i = 0; i < next.length; i++) {
                ret[next[i]]=i;
            }
            return ret;
        }
    }

    class ArrangementSupportNormalIterator implements Iterator<int[]>{
        private  int MAX;
        private  int cur;
        private  Iterator<int[]> children;

        public ArrangementSupportNormalIterator() {
            this.MAX=pool.length;
            this.cur=0;
        }

        @Override
        public boolean hasNext() {
            if (chooseNum==0) {
                return false;
            }
            return this.cur< this.MAX || (children!=null && children.hasNext());
        }


        public void refreshIterator() {
            if (chooseNum>0 && this.cur< this.MAX) {
                int[] ints = ArrayUtil.substractBiSearch(pool, new int[]{pool[this.cur]});
                children = new ArrangementSupport(chooseNum - 1, ints).iterator();
            }
        }

        @Override
        public int[] next() {
            if (children==null) {
                refreshIterator();
            }

            int[] ret ;
            if (children!= null && children.hasNext()) {
                int[] next = children.next();
                ret = new int[next.length+1];
                System.arraycopy(next,0,ret,1,next.length);
            }else {
                ret = new int[1];
            }

            ret[0]=pool[this.cur];

            if (children!=null && !children.hasNext()) {
                this.cur++;
                refreshIterator();
            }

            return ret;
        }
    }
}
