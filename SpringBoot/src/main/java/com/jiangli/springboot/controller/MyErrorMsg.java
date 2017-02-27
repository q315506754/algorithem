package com.jiangli.springboot.controller;

import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * To replace the default behaviour completely you can implement ErrorController and register a bean definition of that type,
 *
 * or simply add a bean of type ErrorAttributes to use the existing mechanism but replace the contents.
 * @author Jiangli
 * @date 2017/2/27 11:39
 */
@Component
public class MyErrorMsg implements ErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("111","222");

        //must be provided
        ret.put("timestamp","123123");
        ret.put("error","sdfgsdgf");
        ret.put("status","fghf");
        ret.put("message","message");
        return ret;
    }

    @Override
    public Throwable getError(RequestAttributes requestAttributes) {

        return new NullPointerException("gggg");
//        return null;
    }
}
