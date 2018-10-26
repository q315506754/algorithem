package com.jiangli.datastructure.test;

import java.util.Arrays;
import java.util.BitSet;

/**
 * @author Jiangli
 * @date 2018/10/22 13:15
 */
public class BitSetTest {
    public static void main(String[] args) {
        BitSet bitSet = new BitSet();
        System.out.println(bitSet);
        System.out.println(new BitSet(23));
        bitSet.set(3,10,true);
        System.out.println(bitSet);
        System.out.println(bitSet.get(3));
        System.out.println(bitSet.get(2));
        System.out.println(bitSet.get(0));
        System.out.println(bitSet.toString());
        System.out.println(bitSet.size());//64
        System.out.println(bitSet.length());//10 最高位idx+1
        System.out.println(bitSet.cardinality());//7 true位数count  n. 基数；集的势


        //从0开始 0~7 第一个字节 8~15第二个
        //[-8, 3]
        //11 11111000  = 3  -1 十进制
        System.out.println(Arrays.toString(bitSet.toByteArray()));


        //[1016]
        //00000000 00000000 00000011 11111000  = 1016 十进制
        System.out.println(Arrays.toString(bitSet.toLongArray()));


        BitSet b1 = new BitSet();
        b1.set(1,7);
        BitSet b2 = new BitSet();
        b2.set(2,4);
        b1.andNot(b2);//{1, 4, 5, 6} bits not in
        //System.out.println(b1.intersects(b2));

        //{1, 4, 5, 6}
        System.out.println(b1.nextSetBit(2));//4
        System.out.println(b1.nextSetBit(4));//4
        System.out.println(b1.nextSetBit(5));//5
        System.out.println(b1.nextSetBit(7));//-1

        System.out.println(b1.nextClearBit(2));//2
        System.out.println(b1.nextClearBit(4));//7
        System.out.println(b1.nextClearBit(8));//8

        //{1, 4, 5, 6}
        b1.flip(3,6);
        //{1, 3, 6}
        System.out.println(b1);
    }
}
