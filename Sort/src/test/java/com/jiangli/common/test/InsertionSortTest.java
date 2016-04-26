package com.jiangli.common.test;

import com.jiangli.sort.InsertionSort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class InsertionSortTest {
    private static Logger logger = LoggerFactory.getLogger(InsertionSortTest.class);

    @Autowired
    private InsertionSort insertionSort;

    @Test
    public void func() {
        Integer[] input = new Integer[]{5,2,1,3,4};
        logger.debug("input:" + Arrays.toString(input));
        Integer[] output = insertionSort.sort(input);
        logger.debug("output:" + Arrays.toString(output));

    }

}
