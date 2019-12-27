package com.jiangli.doc.sql.helper.aries;

import org.apache.commons.codec.digest.DigestUtils;

import static org.springframework.util.Assert.notNull;

/**
 * 加密工具类
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018-01-19 15:54
 */
public class EncryptHelper {

    /**
     * SHA256 加密工具方法
     * @param message
     * @param salt
     * @return
     */
    public static final String sha256Hex(String message, String salt) {
        notNull(message, "加密字符串参数不能为空");
        // 如果盐不为空，拼装盐
        if (salt != null) {
            message = salt + message + salt;
        }
        return DigestUtils.sha256Hex(message);
    }

    /**
     * 密码加密
     * @param passwordText 密码明文，不能为空
     * @return
     */
    public static final String encryptPassword(String passwordText) {
        return sha256Hex(passwordText, "Aries#2018");
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("123456"));
    }
}
