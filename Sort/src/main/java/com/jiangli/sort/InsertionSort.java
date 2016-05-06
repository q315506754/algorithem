package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class InsertionSort extends Sorter<Integer> {

    public InsertionSort() {
        logger.debug("construct");
    }

    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        for (int i = 1; i < dest.length ; i++) {
            int card = dest[i];
            int j = i-1;
            while (j>=0 && card < dest[j]) {
                dest[j + 1] = dest[j];
                j--;
            }
            dest[j + 1] = card;
        }
        return dest;
    }

}
