package com.jiangli.commons.test;

import com.jiangli.common.utils.NullAwareBeanUtilsBean;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Jiangli
 * @date 2018/11/12 16:51
 */
public class CopyTest {

    class ABC{
        private Integer age;
        private String name;

        public Integer getAge() {
            return age;
        }

        public ABC setAge(Integer age) {
            this.age = age;
            return this;
        }

        public String getName() {
            return name;
        }

        public ABC setName(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "ABC{" +
                    "age=" + age +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    @Test
    public void test_copy() {
        ABC abc = new ABC();
        abc.setAge(1);
        abc.setName("aaa");

        //使用builder构建的setter&getter
        ABC abc2 = new ABC();
        abc2.setName("2");

        try {
            PropertyDescriptor age = new PropertyDescriptor("age", ABC.class);
            System.out.println(age);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }

        BeanUtilsBean bean = new NullAwareBeanUtilsBean();
        try {
            System.out.println(abc);
            bean.copyProperties(abc,abc2);
            System.out.println(abc);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
