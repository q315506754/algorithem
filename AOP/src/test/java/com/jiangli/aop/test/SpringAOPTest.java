package com.jiangli.aop.test;

import com.jiangli.common.core.IHello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Jiangli on 2016/7/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SpringAOPTest {
    @Autowired
    private IHello iHello;

    @Test
    public void func() {
//        System.out.println(Reflection.getCallerClass());
        System.out.println(iHello.getClass());
        iHello.sayHello("aaa");
//        String a = "fff"+"xxx";
        String a = "fff";
        a+="xxx";
    }
}
