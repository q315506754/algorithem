package com.jiangli.junit.spring;

import com.jiangli.junit.spring.group.InvokerGroup;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.util.List;

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

    @Override
    protected void validatePublicVoidNoArgMethods(Class<? extends Annotation> annotation, boolean isStatic, List<Throwable> errors) {
        List<FrameworkMethod> methods= getTestClass().getAnnotatedMethods(annotation);

        for (FrameworkMethod eachTestMethod : methods) {
            if(eachTestMethod.getAnnotation(InvokerGroup.class) == null){
                eachTestMethod.validatePublicVoidNoArg(isStatic, errors);
            }
        }
    }
}
