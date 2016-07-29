package com.jiangli.spring.test;

import com.jiangli.spring.CmBean;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/7/27 0027 14:27
 */
public class ClassPathTest {
    @Test
    public void func() {
        ClassPathResource cps = new ClassPathResource("text/mytext", CmBean.class);
        print(cps);

        cps = new ClassPathResource("../mytextxxx", CmBean.class);
        print(cps);

        cps = new ClassPathResource("com.jiangli.spring.mytext222");
        print(cps);
    }

    public void print(ClassPathResource cps)   {
        try {
            System.out.println(cps);
            System.out.println(cps.getDescription());
            System.out.println(cps.getPath());
            System.out.println(cps.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
