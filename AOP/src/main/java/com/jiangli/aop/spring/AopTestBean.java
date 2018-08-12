package com.jiangli.aop.spring;

import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 * @date 2018/7/11 15:50
 */
@Component
public class AopTestBean {
    public String func() {
        System.out.println("func");
        return "proxy func";
    }
}
