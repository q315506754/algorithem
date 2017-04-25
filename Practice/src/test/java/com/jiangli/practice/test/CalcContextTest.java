package com.jiangli.practice.test;

import com.jiangli.common.utils.ClassDescribeUtil;
import com.jiangli.common.utils.CollectionUtil;
import com.jiangli.practice.eleme.core.CalcContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 * @date 2017/4/25 9:45
 */
public class CalcContextTest {
        private long startTs;

        @Before
        public void before() {
            startTs = System.currentTimeMillis();
            System.out.println("----------before-----------");
            System.out.println();
        }

        @After
        public void after() {
            long cost = System.currentTimeMillis() - startTs;
            System.out.println("----------after-----------:cost:"+cost+" ms");
            System.out.println();
        }

        @Test
        public void test_ref() throws IllegalAccessException {
            CalcContext calcContext = new CalcContext();
            Class<? extends CalcContext> aClass = calcContext.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            Map<String, Object> map = new HashMap<>();
            for (Field declaredField : declaredFields) {
                System.out.println(declaredField);
                Class<?> declaringClass = declaredField.getDeclaringClass();
                Class<?> type = declaredField.getType();
                System.out.println(type);

                declaredField.setAccessible(true);

                    Object o = declaredField.get(calcContext);
                    System.out.println(o);
                    System.out.println(type.isPrimitive());
                    System.out.println(o instanceof Number);
                    System.out.println(o instanceof Integer);
                    if (o != null ) {
                        boolean valid=true;

                        if (o instanceof Collection) {
                            valid=!CollectionUtil.isEmpty((Collection)o);
                        }

                        if (valid) {
                            map.put(declaredField.getName(),o);
                        }
                    }
                System.out.println("-----------------------");
            }
            System.out.println(map);
        }

    @Test
    public void test_re2() throws IllegalAccessException {
        System.out.println(ClassDescribeUtil.describeFields(CalcContext.class));
    }
}
