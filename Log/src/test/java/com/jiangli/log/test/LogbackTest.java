package com.jiangli.log.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jiangli
 * @date 2016/11/10 16:33
 */
public class LogbackTest {
    private static Logger logger = LoggerFactory.getLogger(LogbackTest.class);
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
        System.out.println("----------after-----------:cost:" + cost + " ms");
        System.out.println();
    }

    @Test
    public void func() {
        logger.debug("ok");

        System.out.println("  ...".startsWith("..."));
    }

    @Test
    public void testMDC() {
        Map<String, String> map = new HashMap<>();
        map.put("aaaa", "aaaa");
        map.put("bbb", "bbb");
        map.put("ttt", new Date().toString());

        MDC.setContextMap(map);
        MDC.put("ccc","12312312");
        logger.debug("ok阿萨德");

        MDC.put("ddd","4444");
        logger.debug("ok2222阿萨德");

        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            MDC.remove(key);
        }
    }

}
