package com.jiangli.test;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/9/1 0001 16:38
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:NONE.xml"})
public class JunitLifeCycle {
    public static String database= null;

    @BeforeClass
    public static void beforeCls1() {
        System.out.println("BeforeClass1");
        database = ":mysql:3306";
    }
    @AfterClass
    public static void afterClass1() {
        System.out.println("AfterClass1");
        database = null;
    }
    @BeforeClass
    public static void beforeCls2() {
        System.out.println("BeforeClass2");
    }
    @AfterClass
    public static void afterClass2() {
        System.out.println("AfterClass2");
    }

    @Before
    public void before1() {
        System.out.println("before1"+database);
    }
    @After
    public void after1() {
        System.out.println("after1"+database);
    }
    @Before
    public void before2() {
        System.out.println("before2"+database);
    }
    @After
    public void after2() {
        System.out.println("after2"+database);
    }

    @Test
    public void func1() {
        System.out.println("func1");
    }

    @Test
    public void func2() {
        System.out.println("func1");
    }

}
