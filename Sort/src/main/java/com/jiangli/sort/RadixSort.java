package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * 基数排序
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@Component
public class RadixSort extends Sorter<Integer> {

    public RadixSort() {

    }

    /**
     * 我们先根据序列的个位数的数字来进行分类，将其分到指定的桶中。
     * 分类后，我们在从各个桶中，将这些数按照从编号0到编号9的顺序依次将所有数取出来。
     这时，得到的序列就是个位数上呈递增趋势的序列。
     * 接下来，可以对十位数、百位数也按照这种方法进行排序，最后就能得到排序完成的序列。
     *
     * 缺点是需要知道数字最长有多少位，否则还得循环一趟来获取
     * 使用的空间为 n+r  r为基数，n为数组长度，这里是指有效空间，还有浪费掉的空间
     * @param unsorted
     * @param radix
     * @param dupListSize
     */

    void radix_sort(Integer[] unsorted, int radix, int dupListSize) {
        /* 最大数字不超过999999999...(array_x个9) */
        //循环越到后面,对应的位越起决定性的作用，符合数字规律，也就是位越高，越能决定顺序
        //假设从数字的左边开始取值，到最后导致个位主导顺序

        //radix代表基数  这里取10
        int maxLength=10;//数字最长有多少位
        for (int i = 0; i < maxLength; i++) {
            Integer[][] bucket = new Integer[radix][dupListSize];
            for (int item : unsorted) {
                int temp = (item / (int) Math.pow(10, i)) % 10;
                for (int l = 0; l < dupListSize; l++) {
                    if (bucket[temp][l] == null) {
                        bucket[temp][l] = item;
                        break;
                    }
                }
            }

            //每一次循环
            for (int o = 0, x = 0; x < radix; x++) {
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
