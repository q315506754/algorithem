package com.jiangli.practice.test;

import com.jiangli.practice.eleme.core.Calculator;
import com.jiangli.practice.eleme.core.CalcContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext*.xml"})
public class CalcTest {
    private long startTs;

    @Autowired
    @Qualifier("basicDataSource")
    private DataSource dataSource;

    @Autowired
    private Calculator calculator;

    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        System.out.println("----------before-----------");
        System.out.println();
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        System.out.println("----------after-----------:cost:" + cost + " ms");
        System.out.println();
    }

    @Test
    public void testCommit() {
        System.out.println(calculator);
        CalcContext context = new CalcContext();
        context.setMerchantId(1);
        context.setReduceForEachOrder(4d);
        calculator.calc(context);
    }
}
