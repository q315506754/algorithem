/**
 *
 */
package com.jiangli.common.core;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author JiangLi
 *
 *         CreateTime 2014-7-23 下午1:51:54
 */
@Aspect
public class RecordAspect {
    /**
     * 必须为final String类型的,注解里要使用的变量只能是静态常量类型的
     */
    public static final String PointCutStr = "execution(* com.jiangli..*.*(..))";
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public RecordAspect() {
        logger.debug("construct..AOP");
    }
//    public static final String PointCutStr = "execution(* com.jiangli.*.*(..))";

    @Before(PointCutStr)    //spring中Before通知
    public void logBefore() {
//        logger.debug("AOP before");
    }

    @After(PointCutStr)    //spring中After通知
    public void logAfter() {
//        logger.debug("AOP after");
    }


    @Around(PointCutStr)    //spring中After通知
    public Object logAround(JoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = ((ProceedingJoinPoint) joinPoint).proceed();
        long end = System.currentTimeMillis();
        logger.info("AOP around:" + joinPoint + "\tUse time : " + (end - start) + " ms!");
        return proceed;
    }


    @AfterReturning(PointCutStr)
    public void afterReturn(JoinPoint joinPoint) {
//        logger.info("AOP afterReturn:");
    }

    @AfterThrowing(pointcut = PointCutStr, throwing = "ex")    //spring中After通知
    public void logAfterThrowing(JoinPoint point, Exception ex) {
        logger.error(ex.getMessage(), ex);
        Signature signature = point.getSignature();

        String methodname = signature.getName();
        String propName = "exception." + methodname + ".emailTo";
        logger.info("AOP afterThrowing:");

    }
}
