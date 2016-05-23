package com.jiangli.common.core;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/23 0023 16:22
 */
public class DefaultSort extends Sorter<Integer> {

    public DefaultSort() {

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
