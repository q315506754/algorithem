package com.jiangli.springmvc.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class AntPathMatcherTest {
    private long startTs;
    private Logger log = LoggerFactory.getLogger(AntPathMatcherTest.class);


    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        log.debug("----------before-----------");
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        log.debug("----------after-----------:cost:" + cost + " ms");
    }

    @Test
    public void testCommit() {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher);
        System.out.println(antPathMatcher.match("/*","asdsd"));//false
        System.out.println(antPathMatcher.match("/*","/asdsd"));//true
        System.out.println(antPathMatcher.match("/*","/asdsd/aasd"));//false

        System.out.println(antPathMatcher.match("/**","asdsd"));//false
        System.out.println(antPathMatcher.match("/**","/asdsd"));//true
        System.out.println(antPathMatcher.match("/**","/asdsd/aasd"));//true

        System.out.println(antPathMatcher.match("classpath*:*","D:\\algorithem\\SpringMVC\\src\\main\\resources\\applicationContext.xml"));//false
        System.out.println(antPathMatcher.match("classpath*:applicationContext.xml","D:\\algorithem\\SpringMVC\\src\\main\\resources\\applicationContext.xml"));//false
        System.out.println(antPathMatcher.match("classpath*:applicationContext.xml","D:\\algorithem\\SpringMVC\\target\\classes\\applicationContext.xml"));//false

    }



}
