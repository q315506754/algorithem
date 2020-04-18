package com.jiangli.doc.sql.helper.aries;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 知室密码加密/解密器
 *
 */
public class AriesPwd {
    private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";
    //private static final String ALGORITHM = "RSA";
    public static String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDSD6HMZAVjT1Ctp5mIhe5O1UNX9vabLcTdd4o9+iLPE3yXGvqDsR3KhUfoWwwV0PyeRo9vGWk0Q60VYy4/GlmebJW+7wYBXoebn/g/kzmOvvbbTZbJi+M1WIpDDizaDl1zHFoyahMa590RW63llDzVd6ZffubhaVKbUruiSs/xMwIDAQAB";
    public static String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANIPocxkBWNPUK2nmYiF7k7VQ1f29pstxN13ij36Is8TfJca+oOxHcqFR+hbDBXQ/J5Gj28ZaTRDrRVjLj8aWZ5slb7vBgFeh5uf+D+TOY6+9ttNlsmL4zVYikMOLNoOXXMcWjJqExrn3RFbreWUPNV3pl9+5uFpUptSu6JKz/EzAgMBAAECgYEAorT/A305+yl1eX0HXIs5ApEH/7vIWnyCL3UnfEgi7DjQ2Y5JGvZ29+Zyw1S6sK9W7RYEhe6t4ZDGIXnYWYKEhN1c0UkwMOv1r0olNSbYJjk+vEgfXG2R3R6DLCMmKT1tWjxygfYrj77UiQOD5kmzeR3IRP6rMFp/be5SOhXpkEECQQDxFCeKe4GvEJ4h0IG+0BpKQoSZYTFKs14P31neOZ+w9TvH8rXarggKNp+q+N/HhNWrYsIONeCdDdHBMWnXjL8TAkEA3xACOXspjfStPRj0Lag6+SkBv+LD2NhHuL4kS5HBC+S13FSnXfr+Y5truV3QdiA4O7qCywbKS1bf5n6GDUipYQJBAJCDmmhjPQwqlACFMyGlMf1N/prLLncvKrWtF/wk4tt7RkjH78eVB3DH6dLduZeqw0MnwlguZ+T9wb1j16TE1E0CQHdilg41wPPdNKsyo92b4JRiQjF1KOTyPNC/06UuiWfYLQ2TsVA3edfN40X+1AelDvPIyx/mrnbMNGGk7hJGBYECQG0K0ujFuTxO4g4AmXfzskkx2ENjn7ByPVDu5WOfUE5j7wLjW5ICqeQbiMJ1hq+BPdjzjBcSgG3NVHWe9a9SUdM=";
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
            e.printStackTrace();
        }
    }

    //生成密钥对
    public static KeyPair genKeyPair(int keyLength) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        return keyPairGenerator.generateKeyPair();
    }

    //key- > String
    public static String keyToBase64(Key key) throws Exception {
        return bytesToBase64(key.getEncoded());
    }

    public static String keyToHex(Key key) throws Exception {
        return bytesToHexString(key.getEncoded());
    }


    //将base64编码后的公钥字符串转成PublicKey实例
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] keyBytes = base64ToBytes(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    //将base64编码后的私钥字符串转成PrivateKey实例
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = base64ToBytes(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    //公钥加密
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(content);
    }

    //私钥解密
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(content);
    }

    //公钥加密，并转换成十六进制字符串打印出来
    public static String encryptToHexLarge(String content, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);//java默认"RSA"="RSA/ECB/PKCS1Padding"
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        int splitLength = ((RSAPublicKey) publicKey).getModulus().bitLength() / 8 - 11;
        byte[][] arrays = splitBytes(content.getBytes(), splitLength);
        StringBuffer sb = new StringBuffer();
        for (byte[] array : arrays) {
            sb.append(bytesToHexString(cipher.doFinal(array)));
        }
        return sb.toString();
    }

    //私钥解密，并转换成十六进制字符串打印出来
    public static String decryptFromHexLarge(String content, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //
        int splitLength = ((RSAPrivateKey) privateKey).getModulus().bitLength() / 8 - 11;
        byte[] contentBytes = hexString2Bytes(content);
        byte[][] arrays = splitBytes(contentBytes, splitLength);
        StringBuffer sb = new StringBuffer();
        for (byte[] array : arrays) {
            sb.append(new String(cipher.doFinal(array)));
        }
        return sb.toString();
    }

    //拆分byte数组
    public static byte[][] splitBytes(byte[] bytes, int splitLength) {
        int x; //商，数据拆分的组数，余数不为0时+1
        int y; //余数
        y = bytes.length % splitLength;
        if (y != 0) {
            x = bytes.length / splitLength + 1;
        } else {
            x = bytes.length / splitLength;
        }
        byte[][] arrays = new byte[x][];
        byte[] array;
        for (int i = 0; i < x; i++) {

            if (i == x - 1 && bytes.length % splitLength != 0) {
                array = new byte[bytes.length % splitLength];
                System.arraycopy(bytes, i * splitLength, array, 0, bytes.length % splitLength);
            } else {
                array = new byte[splitLength];
                System.arraycopy(bytes, i * splitLength, array, 0, splitLength);
            }
            arrays[i] = array;
        }
        return arrays;
    }

    public static byte[] combineBytes(byte[][] bytes) {
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
        hex = hex.toUpperCase();
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


    //私钥加密
    //可以加密无限长的base64串
    public static String encrypt(String str) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);//java默认"RSA"="RSA/ECB/PKCS1Padding"
        //cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        //return bytesToBase64(cipher.doFinal(str.getBytes()));

        int splitLength = ((RSAPrivateKey) privateKey).getModulus().bitLength() / 8 - 11;

        byte[][] arrays = splitBytes(str.getBytes(), splitLength);

        byte[][] encrypt = new byte[arrays.length][];
        int n = 0;
        for (byte[] array : arrays) {
            byte[] bytes = cipher.doFinal(array);
            encrypt[n++] = bytes;
        }
        return bytesToBase64(combineBytes(encrypt));
    }

    //公钥解密
    //可以解密无限长的base64串
    public static String decrypt(String str) throws Exception {
        byte[] decode = base64ToBytes(str);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        //cipher.init(Cipher.DECRYPT_MODE, privateKey);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        //return new String(cipher.doFinal(decode));

        int splitLength = ((RSAPublicKey) publicKey).getModulus().bitLength() / 8;

        byte[][] arrays = splitBytes(decode, splitLength);

        byte[][] decrypt = new byte[arrays.length][];
        int n = 0;
        for (byte[] array : arrays) {
            byte[] bytes = cipher.doFinal(array);
            decrypt[n++] = bytes;
        }

        return new String(combineBytes(decrypt));
    }

    public static String bytesToBase64(byte[] bytes) throws Exception {
        return new String(Base64.getEncoder().encode(bytes));
        ////TODO
        //return new String(Base64Custom.encode(bytes));
    }

    private static byte[] base64ToBytes(String str) {
        return Base64.getDecoder().decode(str.getBytes());
        //TODO
        //return Base64Custom.decodeGetBytes(str);
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = genKeyPair(1024);

        //获取公钥，并以base64格式打印出来
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("公钥 base64：" + keyToBase64(publicKey));
        System.out.println("公钥 hex：" + keyToHex(publicKey));

        //获取私钥，并以base64格式打印出来
        PrivateKey privateKey = keyPair.getPrivate();
        System.out.println("私钥 base64：" + keyToBase64(privateKey));
        System.out.println("私钥 hex：" + keyToHex(privateKey));

        System.out.println(decrypt(encrypt("")));
        System.out.println(decrypt(encrypt("1")));
        System.out.println(decrypt(encrypt("1阿萨达")));
        System.out.println(decrypt(encrypt("324324电饭锅%￥#%##￥%#3）*%￥）&*￥）#")));
        System.out.println(decrypt(encrypt("发的萨菲水电费")));
        System.out.println(encrypt(publicKeyString));
        System.out.println(decrypt(encrypt(publicKeyString)));

        System.out.println(encrypt("javaqwe"));
        //System.out.println(decrypt(""));
        System.out.println(decrypt("l0RN7DKxaLUtsjjmZ3l69QDWdhcqHA6uonBvdiJIecjbQ4MCL7vXR/UBRkeZnXmSIk97oke98T+CYcrwKyd1dRIwapx+PnYdU2Qo/Qhlo7HpvYQopIjYaM3/vhP7u4MwVXJwo3hGeHMJ+h05PQvL/RSKGLLx73GG/T/MCDX/tGk="));
    }
}
