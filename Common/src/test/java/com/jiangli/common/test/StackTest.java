package com.jiangli.common.test;

/**
 * @author Jiangli
 * @date 2017/5/5 10:12
 */
public class StackTest {

    public static void main(String[] args)
    {
        int[] row = {6 , -2 , 1 ,2 ,-8, 1};
        int result = arraySum(0, row.length-1, row);
        System.out.println(result);

    }

    public static int arraySum(int start, int end, int[] row)
    {
        System.out.println(start + " " + end);
        System.out.println(start + " " + end);
        int len = end-start+1;
        if (len == 1)
        {
            return row[start];
        }
        int aux1 = arraySum(start, start+(len/2)-1, row);
        int aux2 = arraySum(start+len/2, end, row);
        return aux1 + aux2;
    }
}
