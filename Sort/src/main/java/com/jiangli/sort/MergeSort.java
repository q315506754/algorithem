package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class MergeSort extends Sorter<Integer> {

    public MergeSort() {

    }

    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        Integer[] sorted = new Integer[dest.length];
        mergeMain(dest, sorted, 0, dest.length - 1);

        return dest;
    }

    public void merge(final Integer[] src, final Integer[] sorted, int lowIdx, int midIdx, int highIdx) {
        int i = lowIdx;
        int j = midIdx+1;
        int count = 0;
        while (i <= midIdx && j <= highIdx) {
            if (src[i] < src[j]) {
                sorted[count++] = src[i++];
            } else {
                sorted[count++] = src[j++];
            }
        }

        while (i <= midIdx) {
            sorted[count++] = src[i++];
        }

        while (j <= highIdx) {
            sorted[count++] = src[j++];
        }

        for (int n = 0; n < count; n++) {
            src[n + lowIdx] = sorted[n];
        }
    }

    public void mergeMain(final Integer[] src, final Integer[] sorted, int lowIdx, int highIdx) {
        int midIdx = (lowIdx + highIdx) / 2;

        if (lowIdx < highIdx) {
            mergeMain(src, sorted, lowIdx, midIdx);
            mergeMain(src, sorted, midIdx + 1, highIdx);
            merge(src, sorted, lowIdx, midIdx, highIdx);
        }
    }
}
