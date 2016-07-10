package com.jiangli.aop.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by Jiangli on 2016/7/9.
 */
public class FaxAdvice  implements MethodBeforeAdvice {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.debug("method before...");
        logger.debug("target:"+target.getClass());

    }
}
