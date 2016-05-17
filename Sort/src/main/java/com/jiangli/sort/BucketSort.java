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
