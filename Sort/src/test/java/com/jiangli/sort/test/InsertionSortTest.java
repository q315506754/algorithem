package com.jiangli.sort.test;

import com.jiangli.sort.InsertionSort;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class InsertionSortTest implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(InsertionSortTest.class);
    private ApplicationContext applicationContext;
    @Autowired
//    private Sort<Integer> insertionSort;
    private InsertionSort insertionSort;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void func22() {

    }

    @Test
    @Repeat(20)
    public void func() {
        Integer[] input = new Integer[]{5, 2, 1, 3, 4};
        Integer[] output = insertionSort.sort(input);
        logger.debug(Arrays.toString(input) + "=>" + Arrays.toString(output));
    }

}
