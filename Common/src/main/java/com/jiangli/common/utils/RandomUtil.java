package com.jiangli.common.utils;

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
}
