package com.jiangli.springmvc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.WebApplicationObjectSupport;

/**
 * @author Jiangli
 * @date 2017/5/2 13:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext.xml"})
public class WebApplicationObjectSupportTest extends WebApplicationObjectSupport {

    @Test
    public void test_() {
        System.out.println(getApplicationContext());
        System.out.println(getMessageSourceAccessor());

        //java.lang.IllegalStateException: WebApplicationObjectSupport instance
        // [com.jiangli.springmvc.test.WebApplicationObjectSupportTest@6973bf95]
        // does not run in a WebApplicationContext but in:
        // org.springframework.context.support.GenericApplicationContext@26f67b76:
        // startup date [Tue May 02 13:58:48 CST 2017]; root of context hierarchy
        System.out.println(getServletContext());

        //同上
        System.out.println(getWebApplicationContext());
    }


}
