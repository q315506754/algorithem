package com.jiangli.junit;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author Jiangli
 * @date 2017/3/14 15:27
 */
public class MyCountTimeRule implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {

        return new Statement(){
            @Override
            public void evaluate() throws Throwable {
                long l = System.currentTimeMillis();
                System.out.println("----------before-----------");
                base.evaluate();
                long cost = System.currentTimeMillis()-l;
                System.out.println("----------after-----------:cost:"+cost+" ms");
            }
        };
    }

}
