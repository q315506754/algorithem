package com.jiangli.junit.spring.data.handler;

import com.jiangli.junit.spring.InvokeContext;
import com.jiangli.junit.spring.data.DataCollector;

/**
 * @author Jiangli
 * @date 2017/12/28 11:18
 */
public interface DataHandler {
    void handler(InvokeContext context, DataCollector model);
}
