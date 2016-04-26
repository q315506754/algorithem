/**
 * 
 */
package com.jiangli.common.core;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * @author JiangLi
 *
 * CreateTime 2014-7-23 下午1:51:54 
 */
@Aspect
@Service
public class RecordAspect {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public RecordAspect() {
        logger.debug("construct..AOP");
    }

    /**
     * 必须为final String类型的,注解里要使用的变量只能是静态常量类型的 
     */  
    public static final String PointCutStr = "execution(* com.jiangli.*.*(..))";
    
    @Before(PointCutStr)    //spring中Before通知
    public void logBefore() {  
        System.out.println("logBefore:现在时间是:"+new Date());  
    }  
      
    @After(PointCutStr)    //spring中After通知
    public void logAfter() {  
        System.out.println("logAfter:现在时间是:"+new Date());  
    }  
    
    @AfterThrowing(pointcut=PointCutStr,throwing="ex")    //spring中After通知  
    public void logAfterThrowing(JoinPoint point,Exception ex) {  
    	logger.error(ex.getMessage(), ex);
    	Signature signature = point.getSignature();
    	
    	String methodname = signature.getName();
    	String propName=  "exception." + methodname + ".emailTo";

    	
    }  
}
