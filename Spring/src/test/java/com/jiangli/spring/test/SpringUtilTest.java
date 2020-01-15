package com.jiangli.spring.test;

import org.springframework.context.ApplicationListener;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import static org.springframework.core.io.support.SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION;

/**
 * @author Jiangli
 * @date 2020/1/15 13:29
 */
public class SpringUtilTest {
    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<String> strings = SpringFactoriesLoader.loadFactoryNames(ApplicationListener.class, classLoader);
        System.out.println(strings);

        Enumeration<URL> urls = null;
        try {
            urls = (classLoader != null ? classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
                    ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (urls.hasMoreElements()) {
            System.out.println(urls.nextElement());
        }
    }

}
