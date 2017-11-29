package com.jiangli.junit.spring;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jiangli
 * @date 2017/11/17 9:08
 */
public class StatisticsSpringJunitRunner extends SpringJUnit4ClassRunner {
    public StatisticsSpringJunitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new InvokeMethodRecycle(method, test);
    }
}
