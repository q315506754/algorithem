package com.jiangli.cipher;

import com.jiangli.common.utils.CodeUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jiangli
 * @date 2018/6/12 15:40
 */
public class MD5 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String msg = "123456";

        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] digest = md5.digest(msg.getBytes());

        System.out.println(CodeUtil.toHexString(digest));
    }
}
