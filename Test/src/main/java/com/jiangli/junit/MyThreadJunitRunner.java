package com.jiangli.junit;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jiangli
 * @date 2017/3/14 15:34
 */
public class MyThreadJunitRunner extends BlockJUnit4ClassRunner {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public MyThreadJunitRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    protected Statement methodInvoker(FrameworkMethod method, Object test) {
        return new ThreadsInvokeMethod(method, test);
    }

    public class ThreadsInvokeMethod extends Statement {
        private final FrameworkMethod fTestMethod;
        private Object fTarget;

        public ThreadsInvokeMethod(FrameworkMethod testMethod, Object target) {
            fTestMethod= testMethod;
            fTarget= target;
        }

        @Override
        public void evaluate() throws Throwable {
            MyThread annotation = fTestMethod.getAnnotation(MyThread.class);
            if (annotation != null) {
                int threadNum = annotation.threadNum();
                System.out.println("threadNum:"+threadNum);
                threadNum = threadNum < 1?1:threadNum;

                CountDownLatch latch = new CountDownLatch(threadNum);

                while (threadNum-->0) {
                    executorService.execute(()->{
                        System.out.println(""+Thread.currentThread());
                        try {
                            fTestMethod.invokeExplosively(fTarget);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();

                        } finally {
                            latch.countDown();
                        }
                    });
                }

                latch.await();
            } else {
                fTestMethod.invokeExplosively(fTarget);
            }

        }
    }
}
