package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class BucketSort extends Sorter<Integer> {

    public BucketSort() {

    }

    /**
     * 缺点是对于含元素较大的数组，不得不浪费很大的内存,
     * 因为必须将元素尽可能地发散到数组内，不能产生碰撞
     *
     * 比如{@link Integer.MAX_VALUE}
     * @param arr
     * @return
     */
    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        Integer[] bucket = new Integer[10000];
        for (Integer eachVal : arr) {
            bucket[eachVal] =eachVal;
        }

//        logger.debug(Arrays.toString(bucket));

        int count = 0;
        for (Integer eachOne : bucket) {
            if (eachOne != null) {
                dest[count++] = eachOne;
            }
        }

        return dest;
    }

}
