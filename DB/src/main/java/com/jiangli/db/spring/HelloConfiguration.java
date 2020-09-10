package com.jiangli.db.spring;

import org.springframework.context.annotation.Configuration;

/**
 * @author Jiangli
 * @date 2018/7/11 14:16
 */
@Configuration
public class HelloConfiguration {
    public HelloConfiguration() {
        System.out.println("HelloConfiguration construct!!");
    }

    //@Bean
    //public TestBean getPerson() {
    //    return new TestBean();
    //}

}
