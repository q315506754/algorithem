package com.jiangli.security.test.digest;

import com.jiangli.common.utils.CodeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jiangli
 * @date 2016/11/10 15:08
 */
public class Sha1 {

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
        String msg = "123456";

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("sha1");
            byte[] digest = md5.digest(msg.getBytes());
            CodeUtil.printByteArray(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

}
