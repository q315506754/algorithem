package com.jiangli.cache.memcached.test;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/14 0014 14:09
 */
public class XMemcachedTest {

    @Test
    public void func() throws Exception {
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses("localhost:11211"));

        MemcachedClient memcachedClient = builder.build();

        try {
            memcachedClient.set("hello", 0, "Hello,xmemcached");
            String value = memcachedClient.get("hello");
            System.out.println("hello=" + value);


            memcachedClient.delete("hello");
            value = memcachedClient.get("hello");

            System.out.println("hello=" + value);

            memcachedClient.set("hello2", 10, "Hello2,xmemcached");//senconds

        } catch (MemcachedException e) {
            System.err.println("MemcachedClient operation fail");
            e.printStackTrace();

        } catch (TimeoutException e) {
            System.err.println("MemcachedClient operation timeout");
            e.printStackTrace();

        } catch (InterruptedException e) {
            // ignore

        }

        try {
            memcachedClient.shutdown();
        } catch (IOException e) {
            System.err.println("Shutdown MemcachedClient fail");
            e.printStackTrace();

        }
    }

}
