package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 *
 * 选择排序
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class SelectionSort extends Sorter<Integer> {

    public SelectionSort() {

    }

    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        for (int i = 0; i < dest.length-1 ; i++) {
            int min_idx = i;
            int min_val = dest[i];
            for (int j = i+1; j < dest.length ; j++) {
                if (dest[j] < min_val) {
                    min_val = dest[j];
                    min_idx  = j;
                }
            }
            if (i != min_idx) {
                dest[min_idx] = dest[i];
                dest[i] = min_val;
            }
        }

        return dest;
    }

}
