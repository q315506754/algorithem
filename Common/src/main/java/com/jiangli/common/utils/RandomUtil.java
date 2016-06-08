package com.jiangli.common.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Jiangli on 2016/6/7.
 */
public class RandomUtil {
    public static Random random = new Random();

    // from <= ret <= to
    public  static int getRandomNum(int from,int to){
        return random.nextInt(to-from+1)+from;
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
}
