package com.jiangli.common.test;

import com.jiangli.common.core.Sort;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.ServiceLoader;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ServiceLoaderTest {
    private static Logger logger = LoggerFactory.getLogger(ServiceLoaderTest.class);

    @Test
    public void func() {
        ServiceLoader<Sort> loder= ServiceLoader.load(Sort.class);
        for (Sort hello : loder) {
            System.out.println(hello.getClass());
            Object[] sort = hello.sort(new Integer[]{5, 2, 1, 3, 4});
            System.out.println(Arrays.toString(sort));
        }
    }

}
