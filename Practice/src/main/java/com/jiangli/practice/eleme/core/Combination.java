package com.jiangli.practice.eleme.core;

import org.springframework.util.Assert;

import java.util.Iterator;

/**
 * @author Jiangli
 * @date 2017/4/19 8:55
 */
public class Combination implements Iterable<int[]>{
    private final int chooseNum;
    private final int[] pool;

    public Combination(int chooseNum, int[] pool) {
        this.chooseNum = chooseNum;
        this.pool = pool;

        Assert.isTrue(chooseNum>=1);
        Assert.notNull(pool);
        Assert.isTrue(pool.length>=1);
        Assert.isTrue(chooseNum<=pool.length);
    }

    @Override
    public Iterator<int[]> iterator() {
        return new CombinationIterator();
    }

    class CombinationIterator implements Iterator<int[]>{
        private final int[] record = new int[chooseNum];

        public CombinationIterator() {
            for (int i = 0; i < chooseNum; i++) {
                record[i]=i;
            }
        }

        @Override
        public boolean hasNext() {
            return record[0]<=pool.length-chooseNum;
        }

        @Override
        public int[] next() {
            int[] ints = new int[chooseNum];
            for (int i = 0; i < chooseNum; i++) {
                ints[i]=pool[record[i]];
            }
            //inc last
            inc(chooseNum-1);
            return ints;
        }

        public void inc(int ord) {
            int cur = ++record[ord];
            if (cur>pool.length-(chooseNum-ord) && ord > 0) {
                inc(ord-1);
                record[ord]=record[ord-1]+1;
            }
        }
    }
}
