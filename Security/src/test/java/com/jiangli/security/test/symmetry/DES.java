package com.jiangli.security.test.symmetry;

import com.jiangli.common.utils.CodeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Jiangli
 * @date 2016/11/10 15:28
 */
public class DES {
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

    //算法名称
    public static final String KEY_ALGORITHM = "DES";
    //算法名称/加密模式/填充方式
    //DES共有四种工作模式-->>
    // ECB：电子密码本模式、
    // CBC：加密分组链接模式、
    // CFB：加密反馈模式、
    // OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/NoPadding";

    /**
     *
     * 生成密钥key对象
     */
    private static SecretKey keyGenerator(String keyStr)  {
        byte input[] = keyStr.getBytes();

//        Provider sunJce = new com.sun.crypto.provider.SunJCE();
//        Security.addProvider(sunJce);

        DESKeySpec desKey = null;
        try {
            desKey = new DESKeySpec(input);

            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
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

    @Test
    public void func() {
//        String data = "123456";
        String data = "123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456";
        String key = "JIANGLI0";
//        String key = "A1B2C3D4E5F60708";

        SecretKey secretKey = keyGenerator(key);

        try {
            Cipher instance = Cipher.getInstance("DES/ECB/NoPadding");
//            Cipher instance = Cipher.getInstance("DES/ECB/PKCS5Padding");
//            Cipher instance = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");

            SecureRandom random = new SecureRandom();
            instance.init(Cipher.ENCRYPT_MODE,secretKey);
//            instance.init(Cipher.ENCRYPT_MODE,secretKey,random);

            byte[] bytes = instance.doFinal(data.getBytes());

            CodeUtil.printByteArray(bytes);

            instance.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] decrypt = instance.doFinal(bytes);

            String deStr = new String(decrypt);
            System.out.println(deStr);
            System.out.println(deStr.equals(data));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }
}
