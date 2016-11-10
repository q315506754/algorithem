package com.jiangli.security.test.digest;

import com.jiangli.common.utils.CodeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jiangli
 * @date 2016/11/10 14:47
 */
public class Md5 {
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
        try {
            String csn = Charset.defaultCharset().name();
            System.out.println(csn);

            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] digest = md5.digest(msg.getBytes());



            CodeUtil.printByteArray(digest);

//            System.out.println(Integer.toHexString(-31));
//            System.out.println(Integer.toHexString(225));
            System.out.println(Integer.toHexString(17));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
