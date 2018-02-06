package com.jiangli.common.lib;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * Random
 * @author Jiangli
 * @date 2018/2/6 10:08
 */
public class Rnd {
    public static Random random = new Random();

    // from <= ret <= to
    public  static int getRandomNum(int from,int to){
        return random.nextInt(to-from+1)+from;
    }

    public  static long getRandomNum(int length){
        StringBuilder sb = new StringBuilder();
        if (length>0) {
            length--;
            sb.append(getRandomNum(1,9));

            while (length-->0) {
                sb.append(getRandomNum(0,9));
            }
        }else {
            sb.append("0");
        }
        return Long.parseLong(sb.toString());
    }
    public  static int getRandomNum(){
        return getRandomNum(0,9);
    }


    public  static String getRandomString(int length){
        StringBuilder sb = new StringBuilder();
        if (length>0) {
            length--;
            sb.append(getRandomNum(1,9));

            while (length-->0) {
                sb.append(getRandomNum(0,9));
            }
        }else {
            sb.append("0");
        }
        return sb.toString();
    }



    public  static <T> T getRandomOne(Collection<T> list){
        int maxN = list.size();
        int randomNum = getRandomNum(0, maxN-1);

        int count = 0;
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (count == randomNum) {
                return next;
            }
            count++;
        }
        return null;
    }

    public boolean lottery(double chance) {
        return random.nextDouble() <= chance;
    }
}
