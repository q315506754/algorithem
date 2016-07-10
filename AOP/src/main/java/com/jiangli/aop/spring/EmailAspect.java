package com.jiangli.aop.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jiangli on 2016/7/9.
 */
@Aspect
public class EmailAspect {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final String PointCutStr = "execution(* com.jiangli..*.*(..))";

    @Around(PointCutStr)
    public Object aroundFunc(JoinPoint joinPoint) throws Throwable {
        logger.debug("Email around start");
        logger.debug("joinPoint:"+joinPoint.getClass());
        Object proceed = ((ProceedingJoinPoint) joinPoint).proceed();
        logger.debug("Email around over");
        return proceed;
    }
}
