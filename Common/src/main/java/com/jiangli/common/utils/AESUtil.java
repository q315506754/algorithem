package com.jiangli.common.utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";

    /**
     * 16 算法/模式/填充                16字节加密后数据长度        不满16字节加密后长度
     * AES/CBC/NoPadding             16                          不支持
     * AES/CBC/PKCS5Padding          32                          16
     * AES/CBC/ISO10126Padding       32                          16
     * AES/CFB/NoPadding             16                          原始数据长度
     * AES/CFB/PKCS5Padding          32                          16
     * AES/CFB/ISO10126Padding       32                          16
     * AES/ECB/NoPadding             16                          不支持
     * AES/ECB/PKCS5Padding          32                          16
     * AES/ECB/ISO10126Padding       32                          16
     * AES/OFB/NoPadding             16                          原始数据长度
     * AES/OFB/PKCS5Padding          32                          16
     * AES/OFB/ISO10126Padding       32                          16
     * AES/PCBC/NoPadding            16                          不支持
     * AES/PCBC/PKCS5Padding         32                          16
     * AES/PCBC/ISO10126Padding      32                          16
     */
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";//默认的加密算法


    /**
     * AES加密字符串
     * 
     * @param content
     *            需要被加密的字符串
     * @param password
     *            加密需要的密码
     * @return 密文
     */
    public static byte[] encrypt(String content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            kgen.init(128, new SecureRandom(password.getBytes()));// 利用用户密码作为随机数初始化出
            //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(byteContent);// 加密
            return result;
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密AES加密过的字符串
     * 
     * @param content
     *            AES加密过过的内容
     * @param password
     *            加密时的密码
     * @return 明文
     */
    public static byte[] decrypt(byte[] content, String password) {
        return decrypt(content,password.getBytes(),null);
    }
    public static byte[] decrypt(byte[] content, byte[] password, AlgorithmParameterSpec para) {
        try {

            //SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            //random.setSeed(password);
            //
            //KeyGenerator kgen = KeyGenerator.getInstance("AES");// 创建AES的Key生产者
            ////kgen.init(128, new SecureRandom(password));
            //kgen.init(128, random);
            //
            //SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
            //byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥
            //
            //SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");// 转换为AES专用密钥

            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            if (para != null) {
                cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password),para);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password));
            }

            //cipher.init(Cipher.DECRYPT_MODE, getSecretKey(password),ips);

            //执行操作
            //byte[] result = cipher.doFinal(Base64Utils.decode(content));

            byte[] result = cipher.doFinal(content);
            return result; // 明文
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static SecretKeySpec getSecretKey(byte seed[]) {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg = null;

        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);

            //1
            //SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            //random.setSeed(seed);

            //2
            SecureRandom random = new SecureRandom(seed);

            //AES 要求密钥长度为 128
            kg.init(128, random);

            //生成一个密钥
            SecretKey secretKey = kg.generateKey();

            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws Exception {
		String content = "密码1993";
		String password = "加密密码";
		System.out.println("需要加密的内容：" + content);
		byte[] encrypt = encrypt(content, password);
		System.out.println("加密后的2进制密文：" + new String(encrypt));
		String hexStr = ParseSystemUtil.parseByte2HexStr(encrypt);
		System.out.println("加密后的16进制密文:" + hexStr);
		byte[] byte2 = ParseSystemUtil.parseHexStr2Byte(hexStr);
		System.out.println("加密后的2进制密文：" + new String(byte2));
		byte[] decrypt = decrypt(byte2, password);
		System.out.println("解密后的内容：" + new String(decrypt,"utf-8"));
	}
}