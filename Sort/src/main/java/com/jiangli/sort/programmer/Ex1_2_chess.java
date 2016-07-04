package com.jiangli.sort.programmer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/7/4 0004 10:14
 */
public class Ex1_2_chess {
    public static final byte LEFT_MASK = (byte) 0b11110000;
    public static final byte RIGHT_MASK = 0b00001111;

    public static void main(String[] args) {
        System.out.println(0b11011011 & 0b1101);
        System.out.println(LEFT_MASK);
        System.out.println(RIGHT_MASK);
        System.out.println('d');
        System.out.println((int)'d');
        System.out.println(7/3);
        System.out.println("::"+(0b11011011 & 0b11010000>>>4));
        System.out.println(0b11111111);
        byte x = -128;
        System.out.println((int)x);
        System.out.println("~"+(x & 0b11111111));
        System.out.println("~~"+(x & 0xff));

        int count = 0;
//        for (int i = 0; i < 256; i++) {
//        for (byte i = 0; i < 256; i++) {
        for (int i = 0; (i&0xff) <= 0b11111111; i++) {
//            System.out.println("byte:"+i);
            if (((i& LEFT_MASK)>>>4)>=9 ) {
                break;
            }
            if ((i& RIGHT_MASK)>=9) {
                continue;
            }
            if (((i& LEFT_MASK) >>>4)%3 != (i& RIGHT_MASK)%3) {
                System.out.println("pos:down-"+(char)(((i& LEFT_MASK) >>>4)%3+100) +(((i& LEFT_MASK) >>>4)/3+1) + " up-"+(char)((i&RIGHT_MASK)%3+100)+((i&RIGHT_MASK)/3+8));
                count++;
            }
        }
        System.out.println("total:"+count);
    }

}
