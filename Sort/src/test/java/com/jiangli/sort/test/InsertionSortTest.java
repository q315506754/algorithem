package com.jiangli.sort.test;

import com.jiangli.common.core.Sort;
import com.jiangli.junit.spring.StatisticsSpringJunitRunner;
import com.jiangli.sort.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(StatisticsSpringJunitRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class InsertionSortTest implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(InsertionSortTest.class);
    private ApplicationContext applicationContext;
    @Autowired
    private InsertionSort insertionSort;

    @Autowired
    private BubbleSort bubbleSort;

    @Autowired
    private SelectionSort selectionSort;

    @Autowired
    private QuickSort quickSort;

    @Autowired
    private MergeSort mergeSort;

    @Autowired
    private ShellSort shellSort;

    @Autowired
    private BucketSort bucketSort;

    @Autowired
    private RadixSort radixSort;

    private static final int REPEAT_TIMES = 1;
    private static final int FUNC_TIMES = 100;
    private static final List<ParamAndExpect> cases = new LinkedList<ParamAndExpect>();

    static {
        cases.add(createPAEObject(new Integer[]{5, 2, 1, 3, 4}, new Integer[]{1, 2, 3, 4, 5}));
        cases.add(createPAEObject(new Integer[]{4, 3, 1, 2, 5}, new Integer[]{1, 2, 3, 4, 5}));
        cases.add(createPAEObject(new Integer[]{9999981, 44668, 1456455, 29, 5658}, new Integer[]{29, 5658, 44668, 1456455, 9999981}));
    }

    private static ParamAndExpect createPAEObject(Integer[] params, Integer[] expectedArrays1) {
        return new ParamAndExpect(params, expectedArrays1);
    }

    static class ParamAndExpect {
        Integer[] testParams;
        Integer[] expectedArrays;

        public ParamAndExpect(Integer[] testParams, Integer[] expectedArrays) {
            this.testParams = testParams;
            this.expectedArrays = expectedArrays;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Before
    public void before() {
        for (ParamAndExpect aCase : cases) {
            logger.debug(Arrays.toString(aCase.testParams)+"=>"+Arrays.toString(aCase.expectedArrays));
        }
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_insertionSort() {
        expect(insertionSort);
    }

    private void expect(Sort<Integer> sorter) {
        System.currentTimeMillis();
        for (ParamAndExpect aCase : cases) {
            Integer[] output = sorter.sort(Arrays.copyOf(aCase.testParams,aCase.testParams.length));
            Assert.assertArrayEquals("算法"+sorter+" "+Arrays.toString(output) + "与预期不服:" + Arrays.toString(aCase.testParams) + "=>" + Arrays.toString(aCase.expectedArrays), aCase.expectedArrays, output);
        }
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_bubbleSort() {
        expect(bubbleSort);
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_selectionSort() {
        expect(selectionSort);
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_quickSort() {
        expect(quickSort);
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_mergeSort() {
        expect(mergeSort);
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_shellSort() {
        expect(shellSort);
    }


    @Test
    @Repeat(REPEAT_TIMES)
    public void test_bucketSort() {
        expect(bucketSort);
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_radixSort() {
        expect(radixSort);
    }


//    practice
    @Test
    @Repeat(REPEAT_TIMES)
    public void test_practice() {
        //冒泡
        expect((arr)->{

            for(int i=1;i<arr.length;i++){
                for (int j = 0; j < arr.length - i; j++) {
                    if(arr[j+1] < arr[j]){
                        swap(arr,j,j+1);
                    }
                }
            }

            return arr;
        });


        //选择
        expect((arr)->{
            for(int i=0;i<arr.length-1;i++){
                int min = i;
                for (int j = i+1; j < arr.length; j++) {
                    if(arr[j] < arr[min]){
                        min = j;
                    }
                }

                if (min != i) {
                    swap(arr,min,i);
                }
            }
            return arr;
        });

        //插入
        expect((arr)->{
            for(int i=0;i<arr.length;i++){
                int tmp = arr[i];

                int j = i-1;
                while (j>=0 && tmp < arr[j] ) {
                    arr[j+1] = arr[j];
                    j--;
                }

                if (j+1 != i) {
                    arr[j+1] = tmp;
                }
            }

            return arr;
        });

        //希尔
        expect((arr)->{
            int gap = 1;
            for (; gap < arr.length;) {
                gap = gap * 3 + 1;
                System.out.println("gap:"+gap);
            }

            for(int g=gap;g>=1;g=g/3){
                System.out.println("gap c:"+g);
                for(int i=g;i<arr.length;i+=g){
                    int tmp = arr[i];

                    int j = i-g;
                    while (j>=0 && tmp < arr[j] ) {
                        arr[j+g] = arr[j];
                        j-=g;
                    }

                    if (j+g != i) {
                        arr[j+g] = tmp;
                    }
                }
            }

            return arr;
        });

        //归并
        expect((arr)->{
            mergeSort(arr,0,arr.length-1);

            return arr;
        });

        //快速
        expect((arr)->{
            quickSort(arr,0,arr.length-1);

            return arr;
        });

        //快速 2
        expect((arr)->{
            quickSort2(arr,0,arr.length-1);

            return arr;
        });

        //堆排序
        expect((arr)->{
            MaxHeap heap = new MaxHeap(arr);

            int n = arr.length-1;
            Integer pop = heap.pop();

            while (pop !=null) {
                arr[n--]=pop;
                pop = heap.pop();
            }

            return arr;
        });


        //计数排序
        expect((arr)->{
            int min = arr[0];
            int max = arr[0];
            for (Integer one : arr) {
                if (one<min) {
                    min = one;
                }
                if (one > max ) {
                    max = one;
                }
            }

            int length = max - min + 1;
            System.out.println("bucket length:"+length);
            Integer[] bucket = new Integer[length];
            for (Integer one : arr) {
                Integer integer = bucket[one-min];
                if (integer == null) {
                    integer = 1;
                } else {
                    integer ++;
                }
                bucket[one-min] = integer;
            }


            int n = 0;
            for (int i =0 ; i < bucket.length; i++) {
                Integer one = bucket[i];

                if (one != null) {
                    while (one-- > 0) {
                        arr[n++] = i + min;
                    }
                }
            }

            return arr;
        });

        ////桶排序
        expect((arr)->{
            int min = arr[0];
            int max = arr[0];
            for (Integer one : arr) {
                if (one<min) {
                    min = one;
                }
                if (one > max ) {
                    max = one;
                }
            }

            final int bucketSize = 5 ;
            final int bucketCount = ((max - min + 1 )/ bucketSize) + 1 ;
            System.out.println("bucketCount:"+bucketCount);
            final int[][] buckets = new int[bucketCount][];

            for (Integer one : arr) {
                int idx = (one - min) / bucketSize;

                int[] bucket = buckets[idx];

                if (bucket == null) {
                    bucket = new int[bucketSize];
                    buckets[idx] = bucket;
                }

                //append
                buckets[idx] = arrAppend(bucket,one);

            }

            int n = 0;
            for (int[] bucket : buckets) {
                if (bucket != null) {
                    Arrays.sort(bucket);

                    for (int i : bucket) {
                        if (i!=0) {
                           arr[n++] = i;
                        }
                    }
                }
            }

            return arr;
        });


        //基数排序
        expect((arr)->{
            int maxLoopTimes =0;
            for (Integer one : arr) {
                int length = 1;
                while (one / 10 > 0) {
                    one /= 10;
                    length++;
                }

                if (length > maxLoopTimes) {
                    maxLoopTimes = length;
                }
            }

            int num1=1;
            int num2=num1 * 10;
            for (int i = 0; i < maxLoopTimes; i++,num1*=10,num2*=10) {
                int[][] buckets = new int[10][arr.length];
                for (Integer one : arr) {
                    //int iNum = one/num1 - (one /num2) * 10;
                    //int iNum = (one/num1)%10;
                    int iNum = one/num1%10; // %的计算优先级小于等于/
                    buckets[iNum] = arrAppend(buckets[iNum], one);
                }

                int n = 0;
                for (int[] bucket : buckets) {
                    for (int one : bucket) {
                        if (one!=0) {
                            arr[n++] = one;
                        } else {
                            break;
                        }
                    }
                }
            }

            return arr;
        });

    }

    public int[] arrAppend(int[] arr,int one) {
        boolean insert = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                arr[i] = one;
                insert = true;
                break;
            }
        }

        if (!insert) {
            arr = Arrays.copyOf(arr,arr.length+1);
            arr[arr.length-1] = one;
        }
        return arr;
    }

    @Test
    public void test_heap() {
        MaxHeap hp = new MaxHeap();

        Integer[] ar = {4, 12,55,100,3, 1, 2,8,6,7,9, 5};
        for (Integer one : ar) {
            hp.push(one);

            System.out.println("add:"+one + " result:"+ hp);
        }

        System.out.println("iter in one line:");
        int n = 0;
        for (Integer i : hp) {
            n++;
            System.out.print(i+" ");
        }
        System.out.println("times:"+n);

        System.out.println("pop in one line:");
        n = 0;
        Integer pop = hp.pop();
        while (pop !=null) {
            n++;
            System.out.print(pop+" ");
            pop = hp.pop();
        }
        System.out.println("times:"+n);
    }


    class MaxHeap implements Iterable<Integer>{
        private Integer[] arr;

        public MaxHeap() {
            this(null);
        }

        public MaxHeap(Integer[] arr) {
            if (arr == null) {
                this.arr = new Integer[0];
            } else {
                this.arr = Arrays.copyOf(arr,arr.length);

                for (int i = this.arr.length-1; i > 0; i--) {
                    shiftUp((i-1)/2);
                }
            }
        }

        public void push(int one) {
            this.arr = Arrays.copyOf(this.arr, this.arr.length + 1);
            this.arr[this.arr.length - 1] = one;

            shiftUp((this.arr.length-1-1)/2);
        }

        private void shiftUp(int idx) {
            if (idx >=0 ) {
                int maxIdx = idx;
                int leftIdx = 2*idx+1;
                int rightIdx = 2*idx+2;
                if (leftIdx < arr.length && arr[leftIdx] > arr[maxIdx] ) {
                    maxIdx = leftIdx;
                }
                if (rightIdx < arr.length && arr[rightIdx] > arr[maxIdx] ) {
                    maxIdx = rightIdx;
                }
                if (maxIdx!= idx) {
                    swap(arr,maxIdx,idx);
                    shiftUp((idx-1)/2);
                }
            }
        }

        private void shiftDown(int idx) {
            if (idx < this.arr.length ) {
                int maxIdx = idx;
                int leftIdx = 2*idx+1;
                int rightIdx = 2*idx+2;
                if (leftIdx < arr.length && arr[leftIdx] > arr[maxIdx] ) {
                    maxIdx = leftIdx;
                }
                if (rightIdx < arr.length && arr[rightIdx] > arr[maxIdx] ) {
                    maxIdx = rightIdx;
                }
                if (maxIdx!= idx) {
                    swap(arr,maxIdx,idx);
                    shiftDown(maxIdx);
                }
            }
        }

        public Integer pop() {
            if (this.arr.length > 0) {
                int ret = this.arr[0];

                swap(this.arr, 0, this.arr.length - 1);

                this.arr = Arrays.copyOf(this.arr, this.arr.length-1);

                shiftDown(0);

                return ret;
            }
            return null;
        }


        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            int layerMax=2;
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]);

                if(i < arr.length-1){
                    if (i == layerMax - 2 ) {
                        sb.append("\n");
                        layerMax*=2;
                    } else {
                        sb.append(" ");
                    }
                }
            }
            return "total:"+arr.length+" each line:\n"+sb.toString();
        }

        @Override
        public Iterator<Integer> iterator() {
            return new HeapIt();
        }

        class HeapIt implements Iterator<Integer> {
            private int i = 0;

            @Override
            public boolean hasNext() {
                return i< arr.length;
            }

            @Override
            public Integer next() {
                return arr[i++];
            }
        }
    }

    //双指针版
    private void quickSort2(Integer[] arr, int from, int to) {
        if (from < to) {
            int pivot = from;
            int split = pivot+1;
            for (int i = split; i <= to ; i++) {
                if (arr[i] < arr[pivot]) {
                    swap(arr,i,split);
                    split++;
                }
            }
            swap(arr,pivot,split-1);
            pivot = split-1;

            quickSort(arr,from,pivot-1);
            quickSort(arr,pivot+1,to);
        }
    }

    //单指针版
    private void quickSort(Integer[] arr, int from, int to) {
        if (from < to) {
            int pivot = from;
            for (int i = from+1; i <= to ; i++) {
                if (arr[i] < arr[pivot]) {
                    //三个格子间的交换
                    int oldV = arr[pivot];
                    arr[pivot] = arr[i];
                    arr[i] = arr[pivot+1];
                    arr[pivot+1] = oldV;
                    pivot++;
                }
            }

            quickSort(arr,from,pivot-1);
            quickSort(arr,pivot+1,to);
        }
    }

    private void mergeSort(Integer[] arr, int from, int to) {
        if (from < to ) {
            int mid = (from + to) / 2;
            mergeSort(arr,from,mid);
            mergeSort(arr,mid+1,to);

            //combine
            Integer[] sorted = new Integer[to-from+1];
            int i=from,j=mid+1,n=0;
            while (i<=mid && j<=to) {
                if (arr[i] < arr[j]) {
                    sorted[n++] = arr[i++];
                } else {
                    sorted[n++] = arr[j++];
                }
            }

            while (i<=mid){
                sorted[n++] = arr[i++];
            }
            while (j <= to) {
                sorted[n++] = arr[j++];
            }

            System.arraycopy(sorted,0,arr,from,to-from+1);
        }
    }

    @Test
    public void test_() {
        System.out.println(findMedianSortedArrays(new int[]{1,3},new int[]{2}));
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] ret = new int[nums1.length+nums2.length];
        int i=0,j=0,n=0;
        while(i<nums1.length&&j<nums2.length){
            if(nums1[i] < nums2[j]){
                ret[n++] = nums1[i++];
            }else {
                ret[n++] = nums2[j++];
            }
        }

        while(i<nums1.length){
            ret[n++] = nums1[i++];
        }

        while(j<nums2.length){
            ret[n++] = nums2[j++];
        }

        int length = i+j;
        if(length%2 == 0){
            return (ret[(length-1)/2] +ret[length/2] )/2.0;
        }else {
            return ret[(length-1)/2] ;
        }
    }

    private void swap(Integer[] arr, int j, int i) {
        int tmp = arr[j];
        arr[j]= arr[i];
        arr[i] = tmp;
    }
}
