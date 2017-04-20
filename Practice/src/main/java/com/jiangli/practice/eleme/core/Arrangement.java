package com.jiangli.practice.eleme.core;

import org.springframework.util.Assert;

import java.util.Iterator;

/**
 * @author Jiangli
 * @date 2017/4/20 10:35
 */
public class Arrangement implements  Iterable<int[]>{
    private final int chooseNum;
    private final int[] pool;

    public Arrangement(int chooseNum, int[] pool) {
        Assert.isTrue(chooseNum>=0);
        Assert.notNull(pool);

        this.chooseNum = chooseNum;
        this.pool = pool;
    }

    @Override
    public Iterator<int[]> iterator() {
        return new ArrangementIterator();
    }

    class ArrangementIterator implements Iterator<int[]>{

        private final Iterator<int[]> arrangementSupport;

        public ArrangementIterator() {
            arrangementSupport = new ArrangementSupport(chooseNum,pool.length).iterator();
        }

        @Override
        public boolean hasNext() {
            return arrangementSupport.hasNext();
        }

        @Override
        public int[] next() {
            int[] next = arrangementSupport.next();
            int[] ret = new int[next.length];
            for (int i = 0; i < next.length; i++) {
                ret[i]=pool[next[i]];
            }
            return ret;
        }
    }
}
