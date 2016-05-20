package com.jiangli.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ExceptionTest {
    private static Logger logger = LoggerFactory.getLogger(ExceptionTest.class);

    @Test
    public void funcD() {
        logger.debug("logger ok:");
        try {
            try {
                funcC();
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void funcX() {
        throw new NullPointerException("A Exception!");
    }
    public void funcA() {
        funcX();
    }
    public void funcB() {
        try {
            funcA();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    public void funcC() {
        try {
            funcB();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
