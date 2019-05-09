package com.jiangli.springMVCflow.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 * @date 2018/12/19 9:34
 */
@Component
public class TestBean {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public TestBean(ApplicationContext applicationContext) {
        logger.debug("applicationContext:{}",applicationContext);
    }
}
