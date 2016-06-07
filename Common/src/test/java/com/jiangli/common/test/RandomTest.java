package com.jiangli.common.test;

import com.jiangli.common.utils.RandomUtil;
import org.junit.Test;

/**
 * Created by Jiangli on 2016/6/7.
 */
public class RandomTest {
    @Test
    public void func() {
        int n = 10000;
        while (n-- > 0) {
            int randomNum = RandomUtil.getRandomNum(30, 100);
//            System.out.println(randomNum);

            if (randomNum <= 30) {
                System.out.println(randomNum);
            }
            if (randomNum >= 100) {
                System.out.println(randomNum);
            }
        }

        System.out.println(RandomUtil.random.nextInt());
    }
}
