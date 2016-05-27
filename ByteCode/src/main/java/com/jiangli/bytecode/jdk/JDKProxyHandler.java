package com.jiangli.bytecode.jdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 9:52
 */
public class JDKProxyHandler implements InvocationHandler {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private Object o;

    public JDKProxyHandler(Object o) {
        this.o = o;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.debug("invoked...");
        logger.debug("method..." + method);
        logger.debug("proxy..." + proxy.getClass());
        logger.debug("args..." + Arrays.toString(args));
//        logger.debug("proxy:"+proxy);

        Object orgResult = method.invoke(o, args);
        logger.debug("orgResult..." + orgResult);
        Object decoratedResult = orgResult;

        logger.debug("decoratedResult..." + decoratedResult);
        return decoratedResult;
    }
}
