package com.jiangli.cipher;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author Jiangli
 * @date 2018/3/8 13:25
 */
public class RSAUtil {
    public static String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLFj+jygyQ9m9PSMdQS/MM7ZQYtKD7RgkxxB5MJTJUbSzDQjYxIqciFviuuqBtFB60I0Jx2orizqeVnzNg9UR0zYIk9CulJiS4dsB3mSlEMEeURiIQ69aXwPL3MIc6dMtXwpi/OPzVQ/k6aHawihsUO5KcmgnFvdnLD7tIT9JT8QIDAQAB";
    public static String privateKeyString="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIsWP6PKDJD2b09Ix1BL8wztlBi0oPtGCTHEHkwlMlRtLMNCNjEipyIW+K66oG0UHrQjQnHaiuLOp5WfM2D1RHTNgiT0K6UmJLh2wHeZKUQwR5RGIhDr1pfA8vcwhzp0y1fCmL84/NVD+TpodrCKGxQ7kpyaCcW92csPu0hP0lPxAgMBAAECgYBeQZSdLeb4MllalIqmCuJKh5KOb5KM1niSpkKV/bsu5dGo/Wz2PXKgMosS9lyTr0fjceWsfJsUe1GsnvfVpEFgGSsiYePa6hY2vI05/eF300B/lB2dIxr5k5N5UeM6TvS+sEdp24KDiqyAP8ymhLjRiYe7MeDvX2NOIeutDiQhsQJBANMcmMO5DCOlK5aRcu/aVUeLwZGOU7c96/VN5WqK96rvyYne28LPCYTedh3M67Qk3j47e+gnQf809oU5UqXuE00CQQCoqSPIy++vPbywqjGETOCNatPEqGPr56nxKbOTIaylDb7UdZtBZp4ss9aLdS5uejH8wbIRMW9YATm0gARrtSk1AkA0h3IPiVTW7btJzMkvm0EK+2Bfym1UNAkpUYebGhcEJoQcVSKTd5ajxALZ6Wpguae/DmgHXPVT/ia1AY1qEjIlAkBB4yFpldGigdmuoi4J4wZt+GeWEbpHNA79eS7/sU4ChKYh1Xe7St1L1U1g2Xw0CGRyuzBW6xr2JDRowQH5GaJpAkAJE4BL2HAx9ozsr2rt7Zysxp1LJrNlJPGOLXFrBBu2Xr1qxHUNQqwyVT63dr/EJ23Xb/gZkmNLtBt5CxL5Mzx0";
    private static PublicKey publicKey;
    private static PrivateKey privateKey;
    static {
        initKey();
    }

    private static void initKey() {
        try {
            publicKey = getPublicKey(publicKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            privateKey = getPrivateKey(privateKeyString);
        } catch (Exception e) {
        }
    }

    //生成密钥对
    public static KeyPair genKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator= KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    //key- > String
    public static String keyToBase64(Key key) throws Exception {
        return bytesToBase64(key.getEncoded());
    }
    public static String keyToHex(Key key) throws Exception {
        return bytesToHexString(key.getEncoded());
    }

    public static String bytesToBase64(byte[] bytes) throws Exception {
        return new String(Base64.getEncoder().encode(bytes));
    }

    //将base64编码后的公钥字符串转成PublicKey实例
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[ ] keyBytes= base64ToBytes(publicKey);
        X509EncodedKeySpec keySpec=new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
    //将base64编码后的私钥字符串转成PrivateKey实例
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = base64ToBytes(privateKey);
        PKCS8EncodedKeySpec keySpec=new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    //公钥加密
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher= Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //私钥解密
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher= Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    //公钥加密，并转换成十六进制字符串打印出来
    public static String encryptToHexLarge(String content, PublicKey publicKey) throws Exception {
        Cipher cipher= Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        int splitLength=((RSAPublicKey)publicKey).getModulus().bitLength()/8-11;
        byte[][] arrays=splitBytes(content.getBytes(), splitLength);
        StringBuffer sb=new StringBuffer();
        for(byte[] array : arrays){
            sb.append(bytesToHexString(cipher.doFinal(array)));
        }
        return sb.toString();
    }

    //私钥解密，并转换成十六进制字符串打印出来
    public static String decryptFromHexLarge(String content, PrivateKey privateKey) throws Exception {
        Cipher cipher= Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //
        int splitLength=((RSAPrivateKey)privateKey).getModulus().bitLength()/8-11;
        byte[] contentBytes=hexString2Bytes(content);
        byte[][] arrays=splitBytes(contentBytes, splitLength);
        StringBuffer sb=new StringBuffer();
        for(byte[] array : arrays){
            sb.append(new String(cipher.doFinal(array)));
        }
        return sb.toString();
    }

    //拆分byte数组
    public static byte[][] splitBytes(byte[] bytes, int splitLength){
        int x; //商，数据拆分的组数，余数不为0时+1
        int y; //余数
        y=bytes.length%splitLength;
        if(y!=0){
            x=bytes.length/splitLength+1;
        }else{
            x=bytes.length/splitLength;
        }
        byte[][] arrays=new byte[x][];
        byte[] array;
        for(int i=0; i<x; i++){

            if(i==x-1 && bytes.length%splitLength!=0){
                array=new byte[bytes.length%splitLength];
                System.arraycopy(bytes, i*splitLength, array, 0, bytes.length%splitLength);
            }else{
                array=new byte[splitLength];
                System.arraycopy(bytes, i*splitLength, array, 0, splitLength);
            }
            arrays[i]=array;
        }
        return arrays;
    }

    public static byte[] combineBytes(byte[][] bytes){
        int total = 0;
        for (byte[] aByte : bytes) {
            total += aByte.length;
        }
        byte[] ret = new byte[total];
        int idx = 0;
        for (int i = 0; i < bytes.length; i++) {
            System.arraycopy(bytes[i], 0, ret, idx, bytes[i].length);

            idx += bytes[i].length;
        }
        return ret;
    }

    //byte数组转十六进制字符串
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (int i = 0; i < bytes.length; i++) {
            sTemp = Integer.toHexString(0xFF & bytes[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    //十六进制字符串转byte数组
    public static byte[] hexString2Bytes(String hex) {
        int len = (hex.length() / 2);
        hex=hex.toUpperCase();
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }


    //可以加密无限长的base64串
    public static String encrypt(String str) throws Exception {
        Cipher cipher= Cipher.getInstance("RSA");//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        //return bytesToBase64(cipher.doFinal(str.getBytes()));

        int splitLength=((RSAPublicKey)publicKey).getModulus().bitLength()/8-11;
        byte[][] arrays=splitBytes(str.getBytes(), splitLength);

        byte[][] encrypt = new byte[arrays.length][];
        int n = 0;
        for(byte[] array : arrays){
            byte[] bytes = cipher.doFinal(array);
            encrypt[n++] = bytes;
        }
        return bytesToBase64(combineBytes(encrypt));
    }

    //可以解密无限长的base64串
    public static String decrypt(String str) throws Exception {
        byte[] decode = base64ToBytes(str);
        Cipher cipher= Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //return new String(cipher.doFinal(decode));

        int splitLength=((RSAPrivateKey)privateKey).getModulus().bitLength()/8;
        byte[][] arrays=splitBytes(decode, splitLength);

        byte[][] decrypt = new byte[arrays.length][];
        int n = 0;
        for(byte[] array : arrays){
            byte[] bytes = cipher.doFinal(array);
            decrypt[n++] = bytes;
        }

        return new String(combineBytes(decrypt));
    }

    private static byte[] base64ToBytes(String str) {
        return Base64.getDecoder().decode(str.getBytes());
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair=genKeyPair(1024);

        //获取公钥，并以base64格式打印出来
        PublicKey publicKey=keyPair.getPublic();
        System.out.println("公钥 base64：" + keyToBase64(publicKey));
        System.out.println("公钥 hex："+ keyToHex(publicKey));



        //获取私钥，并以base64格式打印出来
        PrivateKey privateKey=keyPair.getPrivate();
        System.out.println("私钥 base64：" + keyToBase64(privateKey));
        System.out.println("私钥 hex："+ keyToHex(privateKey));

        String str = encrypt("http://wsvideo.g2s.cn/zhs_yanfa_150820/ablecommons/demo/201802/a63cd562043c46e0805ad8a95c809e1d_64.mp3");
        System.out.println(str);
        System.out.println(decrypt(str));

        System.out.println(decrypt(encrypt("1")));
        System.out.println(decrypt(encrypt("1阿萨达")));
        System.out.println(decrypt(encrypt("324324电饭锅%￥#%##￥%#3）*%￥）&*￥）#")));
        System.out.println(decrypt(encrypt("发的萨菲水电费")));
        System.out.println(encrypt(publicKeyString));
        System.out.println(decrypt(encrypt(publicKeyString)));

    }
}
