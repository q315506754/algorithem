package com.jiangli.cipher;

import com.jiangli.common.utils.CodeUtil;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Jiangli
 * @date 2018/6/12 15:40
 */
public class DES {
    //算法名称
    public static final String KEY_ALGORITHM = "DES";

    //DES共有四种工作模式-->>
    // ECB：电子密码本模式、
    // CBC：加密分组链接模式、
    // CFB：加密反馈模式、
    // OFB：输出反馈模式

    //算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

    //PKCS5Padding 填充至符合块大小的整数倍，填充值为填充数量数。例如：
    //ISO10126Padding 填充至符合块大小的整数倍，填充值最后一个字节为填充的数量数，其他字节随机处理。
    //NoPadding PKCS5Padding 和 ISO10126Padding

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String data = "123456sdfsdf";
        //String data = "123456sdfsdfsdfsdfsdfds爱的发的是ddsfdsfsd水电费第三方第三方水电费水电费f";
        String key = "JIANGLI0";
//        String key = "A1B2C3D4E5F60708";

        Cipher instance = null;
        try {
            instance = Cipher.getInstance(CIPHER_ALGORITHM);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }

        try {
            instance.init(Cipher.ENCRYPT_MODE, keyGenerator(key));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] bytes = null;
        try {
            bytes = instance.doFinal(data.getBytes());
            System.out.println(CodeUtil.toHexString(bytes));
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }


        try {
            instance.init(Cipher.DECRYPT_MODE,keyGenerator(key));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        byte[] decrypt = new byte[0];
        try {
            decrypt = instance.doFinal(bytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        String deStr = new String(decrypt);
        System.out.println(deStr);
    }

    private static SecretKey keyGenerator(String keyStr)  {
        byte input[] = keyStr.getBytes();

//        Provider sunJce = new com.sun.crypto.provider.SunJCE();
//        Security.addProvider(sunJce);

        DESKeySpec desKey = null;
        try {
            desKey = new DESKeySpec(input);

            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
            SecretKey securekey = keyFactory.generateSecret(desKey);

//            SecretKey skspec = new SecretKeySpec(input, "Blowfish");
            return securekey;
//            return skspec;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
