package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class QuickSort extends Sorter<Integer> {

    public QuickSort() {

    }

    @Override
    public Integer[] sort(final Integer[] arr) {
        Integer[] dest = getClonedArray(arr);

        quickSort(dest, 0, dest.length - 1);

        return dest;
    }

    public int pivoting(final Integer[] arr, int lowIdx, int highIdx) {
        int pivot = arr[lowIdx];
        while (lowIdx < highIdx) {
            while (lowIdx < highIdx && arr[highIdx] > pivot) {
                highIdx--;
            }
            arr[lowIdx] = arr[highIdx];
            while (lowIdx < highIdx && arr[lowIdx] <= pivot) {
                lowIdx++;
            }
            arr[highIdx] = arr[lowIdx];
        }
        arr[lowIdx] = pivot;
        return lowIdx;
    }

    public void quickSort(final Integer[] arr, int lowIdx, int highIdx) {
        int pivotIdx = pivoting(arr, lowIdx, highIdx);

        if (lowIdx < highIdx) {
            if (lowIdx < pivotIdx - 1) {
                quickSort(arr, lowIdx, pivotIdx - 1);
            }

            if (pivotIdx + 1 < highIdx) {
                quickSort(arr, pivotIdx + 1, highIdx);
            }
        }


    }

}
