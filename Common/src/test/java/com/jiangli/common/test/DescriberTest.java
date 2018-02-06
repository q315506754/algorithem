package com.jiangli.common.test;

import com.jiangli.common.lib.Describer;
import com.jiangli.common.model.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2017/7/12 19:16
 */
public class DescriberTest {

        private long startTs;

        @Before
        public void before() {
            startTs = System.currentTimeMillis();
            System.out.println("----------before-----------");
            System.out.println();
        }

        @After
        public void after() {
            long cost = System.currentTimeMillis() - startTs;
            System.out.println("----------after-----------:cost:"+cost+" ms");
            System.out.println();
        }

        @Test
        public void test_() {
            Person department = new Person();
            System.out.println(Describer.describeObject(department));
        }

}
