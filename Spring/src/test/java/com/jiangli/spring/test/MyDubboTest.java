package com.jiangli.spring.test;

import com.jiangli.spring.dubbo.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class MyDubboTest {

    private static Logger logger = LoggerFactory.getLogger(MyDubboTest.class);

    @Resource
    private MenuService menuService;


    @Test
    public void func() {
        logger.debug(menuService.toString());

        menuService.sayHello();
    }


}
