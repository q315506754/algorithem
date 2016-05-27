package com.jiangli.bytecode.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

public class MyProxyFilter implements CallbackFilter {
    @Override
    public int accept(Method arg0) {
        if (!"query".equalsIgnoreCase(arg0.getName()))
            return 0;
        return 1;
    }
}  