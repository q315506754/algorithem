package com.jiangli.db.spring;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author Jiangli
 * @date 2018/7/11 14:16
 */
@Component
public class MyFactoryClsFBean implements FactoryBean<MyFactoryCls> {
    public MyFactoryClsFBean() {
        System.out.println("MyFactoryClsFBean construct!!");
    }

    @Override
    public MyFactoryCls getObject() throws Exception {
        System.out.println("MyFactoryCls getObject");
        return new MyFactoryCls();
    }

    @Override
    public Class<?> getObjectType() {
        return MyFactoryCls.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
