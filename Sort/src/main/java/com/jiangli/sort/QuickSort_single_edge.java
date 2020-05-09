package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class QuickSort_single_edge extends Sorter<Integer> {

    public QuickSort_single_edge() {

    }

    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        quickSort(dest, 0, dest.length - 1);

        return dest;
    }

    public int pivoting(final Integer[] arr, int lowIdx, int highIdx) {
        int pivot = arr[lowIdx];
        int position = lowIdx;

        for (int i = lowIdx+1; i <= highIdx ; i++) {
            if (arr[i] < pivot) {
                position++;

                if (position != i) {
                    int t = arr[position];
                    arr[position] =  arr[i];
                    arr[i] = t;
                }
            }
        }

        int t = arr[position];
        arr[position] =  arr[lowIdx];
        arr[lowIdx] =  t;

        return position;
    }

    public void quickSort(final Integer[] arr, int lowIdx, int highIdx) {
        int pivotIdx = pivoting(arr, lowIdx, highIdx);

        if (lowIdx < highIdx) {
            if (pivotIdx - 1 > lowIdx) {
                quickSort(arr, lowIdx, pivotIdx - 1);
            }

            if (pivotIdx + 1 < highIdx) {
                quickSort(arr, pivotIdx + 1, highIdx);
            }
        }



}
}
