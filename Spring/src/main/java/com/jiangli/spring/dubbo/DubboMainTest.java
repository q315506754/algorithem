package com.jiangli.spring.dubbo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jiangli
 * @date 2020/1/9 16:36
 */
public class DubboMainTest {
    public static void main(String[] args) {
        System.out.println("aaa");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
        //context.start();

        MenuService bean = context.getBean(MenuService.class);
        System.out.println(bean);
        System.out.println(bean.getClass());
        bean.sayHello();

    }
}
