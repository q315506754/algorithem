package com.jiangli.common.test;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Jiangli
 * @date 2016/11/10 13:42
 */
public class IpTest {
    @Before
    public void before() {
        System.out.println("before");
        System.out.println();
    }

    @After
    public void after() {
        System.out.println("after");
        System.out.println();
    }

    @Test
    public void func() {
        String host = null ;
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            host = addr.getHostAddress();
            System.out.println(host);
            if(StringUtils.isBlank(host)) {
                host = addr.getHostName() ;
            }
            System.out.println(host);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
