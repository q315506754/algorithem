package com.jiangli.jdk.v1_8;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jiangli
 * @date 2018/1/25 19:11
 */
public class FactoryTest {
    public static FactoryTest create(Supplier<FactoryTest> testSupplier) {
        return testSupplier.get();
    }

    private FactoryTest() {
        System.out.println("FactoryTest");
    }
    private FactoryTest(int a) {
        System.out.println("FactoryTest");
    }

    public static void main(String[] args) {
        FactoryTest test = FactoryTest.create(FactoryTest::new);
        Runnable runnable = String::new;
        Runnable aNew = FactoryTest::new;
        Function<Integer,FactoryTest> fx = FactoryTest::new;
        aNew.run();

        FactoryTest apply = fx.apply(33);
    }

}
