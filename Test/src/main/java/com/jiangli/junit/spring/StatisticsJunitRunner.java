package com.jiangli.junit.spring;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * @author Jiangli
 * @date 2017/11/17 9:08
 */
public class StatisticsJunitRunner extends BlockJUnit4ClassRunner {
    public StatisticsJunitRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new InvokeMethodRecycle(method, test);
    }
}
