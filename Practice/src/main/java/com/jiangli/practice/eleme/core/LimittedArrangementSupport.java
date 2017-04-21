package com.jiangli.practice.eleme.core;

import com.jiangli.common.utils.ArrayUtil;
import org.springframework.util.Assert;

import java.util.Iterator;

/**
 * 排列
 * @author Jiangli
 * @date 2017/4/20 9:01
 */
public class LimittedArrangementSupport implements  Iterable<int[]>{
    private final int chooseNum;
    private final int maxPool;
    private final int poolLength;

    public LimittedArrangementSupport(int chooseNum, int poolLength, int maxPool) {
        Assert.isTrue(chooseNum>=0);
        Assert.isTrue(poolLength>=0);
        Assert.isTrue(maxPool>=0);
//        Assert.isTrue(maxPool<=poolLength);
//        Assert.notNull(pool);

        this.chooseNum = chooseNum;
        this.poolLength = poolLength;
        this.maxPool = maxPool;
    }


    @Override
    public Iterator<int[]> iterator() {
        if (maxPool<=poolLength) {
            return new LimittedArrangementSupportIterator();
        }else {
            return new ArrangementSupport(chooseNum,poolLength).iterator();
        }
    }

    class LimittedArrangementSupportIterator implements Iterator<int[]>{
        private final Iterator<int[]> chooseItemIter;
        private Iterator<int[]> distributeIter;
        private int[] chosenItems;

        public LimittedArrangementSupportIterator() {
            chooseItemIter = new Combination(maxPool, ArrayUtil.newArray(poolLength, 0)).iterator();
        }

        public void refreshIterator() {
            if (chooseItemIter.hasNext()) {
                chosenItems = chooseItemIter.next();
                distributeIter = new Arrangement(chooseNum, chosenItems).iterator();
            }
        }

        @Override
        public boolean hasNext() {
            return chooseItemIter.hasNext() || (distributeIter!=null && distributeIter.hasNext());
        }

        @Override
        public int[] next() {
            if(distributeIter==null){
                refreshIterator();
            }
            else if (!distributeIter.hasNext()) {
                refreshIterator();
            }

            int[] ret = distributeIter.next();

            if (!distributeIter.hasNext()) {
                refreshIterator();
            }

            return ret;
        }
    }

}
