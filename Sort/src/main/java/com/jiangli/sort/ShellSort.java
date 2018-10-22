package com.jiangli.sort;

import com.jiangli.common.core.Sorter;
import org.springframework.stereotype.Component;

/**
 * 希尔排序（插入排序是希尔的一般情形，此时group=1）
 * 希尔排序实质=间隔依次为len/2,len/4,len/8....1的插入排序
 *
 *
 * 希尔排序是把记录按下标的一定增量分组，对每组使用直接插入排序算法排序；
 * 随着增量逐渐减少，每组包含的关键词越来越多，当增量减至1时，
 * 整个文件恰被分成一组，算法便终止。 [1]
 *
 * 希尔排序是基于插入排序的以下两点性质而提出改进方法的：
 插入排序在对几乎已经排好序的数据操作时，效率高，即可以达到线性排序的效率。
 但插入排序一般来说是低效的，因为插入排序每次只能将数据移动一位。


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
        int group, i, j;
        for (group = len / 2; group > 0; group /= 2) {
            for (i = group; i < len; i++) {
                int card = unsorted[i];

                j = i - group;
                while (j>=0 && card < unsorted[j] ) {
                    unsorted[j + group] = unsorted[j] ;
                    j -= group;
                }

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
