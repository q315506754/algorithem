package com.jiangli.cipher.des;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES的加密工具类
 *
 * @author cairuirui
 * @create 2018-01-09
 */
public class DESUtils {

    private static final String DES_DEFAULT_KEY = "f630cbce-ce31-4fb7-a6a2-6b4cfc0ffdfa";
    private static final String DES = "DES";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    /**
     * 默认构造方法，使用默认的密匙
     * @throws Exception
     */
    public DESUtils() throws Exception {
        this(DES_DEFAULT_KEY);
    }

    /**
     *指定密匙的构造方法
     * @param desKey 指定的密匙
     * @throws Exception
     */
    public DESUtils(String desKey) throws Exception {
        //生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // Security.addProvider(new com.sun.crypto.provider.SunJCE());
        //重原始密匙字符串创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(desKey.getBytes());
        //创建密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey key = keyFactory.generateSecret(dks);
        //加密
        //Key key = getKey(desKey.getBytes());
        encryptCipher = Cipher.getInstance(DES);
        encryptCipher.init(Cipher.ENCRYPT_MODE,key,sr);
        //解密
        decryptCipher = Cipher.getInstance(DES);
        decryptCipher.init(Cipher.DECRYPT_MODE,key,sr);
    }

    /**
     * 加密字符串
     * @param sourceStr 需要加密字符串
     * @return
     * @throws Exception
     */
    public String encrypt(String sourceStr) throws Exception {
        return byteArr2HexStr(encrypt(sourceStr.getBytes()));
    }

    /**
     * 加密字节数组
     * @param bytes 需要加密字节数组
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] bytes) throws Exception {
        return encryptCipher.doFinal(bytes);
    }

    /**
     * 解密字符串
     * @param secertStr 需要解密字符串
     * @return
     * @throws Exception
     */
    public String decrypt(String secertStr) throws Exception {
        return new String(decrypt(hexStr2ByteArr(secertStr)));
    }

    /**
     * 解密字节数组
     * @param bytes 需要加密字节数组
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] bytes) throws Exception {
        return decryptCipher.doFinal(bytes);
    }

    /**
     * 从指定字符串生成密钥，密钥所需的字节数组长度为8位
     * 不足8位时后面补0，超出8位只取前8位
     * @param arrBTmp 构成该字符串的字节数组
     * @return 生成的密钥
     * @throws Exception
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }

    /**
     * 将byte数组转换为表示16进制值的字符串，
     * 如：byte[]{8,18}转换为：0813，
     * 和public static byte[] hexStr2ByteArr(String strIn)
     * 互为可逆的转换过程
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16));
        }
        return sb.toString();
    }



    /**
     * 将表示16进制值的字符串转换为byte数组，
     * 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static byte[] hexStr2ByteArr(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
}
