package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class RadixSort extends Sorter<Integer> {

    public RadixSort() {

    }

    void radix_sort(Integer[] unsorted, int numLength, int dupListSize) {
        /* 最大数字不超过999999999...(array_x个9) */
        for (int i = 0; i < numLength; i++) {
            Integer[][] bucket = new Integer[10][dupListSize];
            for (int item : unsorted) {
                int temp = (item / (int) Math.pow(10, i)) % 10;
                for (int l = 0; l < dupListSize; l++) {
                    if (bucket[temp][l] == null) {
                        bucket[temp][l] = item;
                        break;
                    }
                }
            }
            for (int o = 0, x = 0; x < numLength; x++) {
                for (int y = 0; y < dupListSize; y++) {
                    if (bucket[x][y] == null) {
                        break;
                    }
                    unsorted[o++] = bucket[x][y];
                }
            }
        }
    }


    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        radix_sort(dest, 10, 100);
        return dest;
    }

}
