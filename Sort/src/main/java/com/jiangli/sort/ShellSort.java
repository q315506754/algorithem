package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class ShellSort extends Sorter<Integer> {

    public ShellSort() {
        logger.debug("construct");
    }

    void shell_sort(Integer[] unsorted, int len) {
        int group, i, j, temp;
        for (group = len / 2; group > 0; group /= 2) {
            for (i = group; i < len; i++) {
                int card = unsorted[i];

                j = i - group;
                while (j>=0 && card < unsorted[j] ) {
                    unsorted[j + group] = unsorted[j] ;
                    j -= group;
                }

//                    if (unsorted[j] > unsorted[j + group]) {
//                        temp = unsorted[j];
//                        unsorted[j] = unsorted[j + group];
//                        unsorted[j + group] = temp;
//                    }

                unsorted[j + group] = card;
            }
        }
    }


    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        shell_sort(dest,dest.length);
        return dest;
    }

}
