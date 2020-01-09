package com.jiangli.spring.dubbo;

import org.springframework.beans.factory.FactoryBean;

public class ReferenceBean<T> extends ReferenceConfig<T> implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return get();
    }
    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }
    @Override
    public boolean isSingleton() {
        return true;
    }
}