package com.jiangli.springboot.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import static org.springframework.core.io.support.SpringFactoriesLoader.FACTORIES_RESOURCE_LOCATION;

/**
 * @author Jiangli
 * @date 2020/1/15 13:29
 */
public class SpringUtilTest {
    public static void main(String[] args) {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread, StackTraceElement[]> threadEntry : allStackTraces.entrySet()) {
            System.out.println(threadEntry.getKey());
            System.out.println(Arrays.toString(threadEntry.getValue()));
        }
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        System.out.println(Arrays.toString(stackTrace));
        System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));

        System.out.println("---------------");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        List<String> strings = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, classLoader);
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
