package com.jiangli.aop.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jiangli on 2016/7/9.
 */
public class LogAspect {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public Object aroundFunc(JoinPoint joinPoint) throws Throwable {
        logger.debug("Log around start");
        logger.debug("joinPoint:"+joinPoint.getClass());
        Object proceed = ((ProceedingJoinPoint) joinPoint).proceed();
        logger.debug("Log around over");
        return proceed;
    }
}
