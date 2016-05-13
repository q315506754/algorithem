package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class BubbleSort extends Sorter<Integer> {

    public BubbleSort() {

    }

    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        for (int i = 0; i < dest.length-1 ; i++) {
            for (int j = 0; j < dest.length-i-1 ; j++) {
                if (dest[j] > dest[j+1]) {
                    Integer temp = dest[j+1];
                    dest[j+1] = dest[j];
                    dest[j] = temp;
                }
            }
        }

        return dest;
    }

}
