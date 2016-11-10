package com.jiangli.security.test.symmetry;

import com.jiangli.common.utils.CodeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author Jiangli
 * @date 2016/11/10 15:50
 */
public class TDES {
    private long startTs;

    @Before
    public void before() {
        startTs = System.currentTimeMillis();
        System.out.println("----------before-----------");
        System.out.println();
    }

    @After
    public void after() {
        long cost = System.currentTimeMillis() - startTs;
        System.out.println("----------after-----------:cost:"+cost+" ms");
        System.out.println();
    }

    // 算法名称
    public static final String KEY_ALGORITHM = "desede";
    // 算法名称/加密模式/填充方式
    public static final String CIPHER_ALGORITHM = "desede/CBC/NoPadding";

    private static Key keyGenerator(String keyStr) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
        byte input[] = keyStr.getBytes();

        DESedeKeySpec KeySpec = new DESedeKeySpec(input);

        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return ((Key) (KeyFactory.generateSecret(((java.security.spec.KeySpec) (KeySpec)))));
    }

    @Test
    public void func() {
//        String data = "123456";
        String data = "123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456";
//        String key = "JIANGLI";
//        String key = "A1B2C3D4E5F60708";
        String key = "A1B2C3D4E5F60708A1B2C3D4E5F60708";
        byte[] keyiv = { 1, 2, 3, 4, 5, 6, 7, 8};

        try {
            Key secretKey = keyGenerator(key);

            Cipher instance = Cipher.getInstance("desede/CBC/NoPadding");
//            Cipher instance = Cipher.getInstance("desede/CBC/ZerosPadding");
//            Cipher instance = Cipher.getInstance("desede/CBC/ZerosPadding");

            SecureRandom random = new SecureRandom();
            IvParameterSpec ips = new IvParameterSpec(keyiv);

//            instance.init(Cipher.ENCRYPT_MODE,secretKey);
            instance.init(Cipher.ENCRYPT_MODE,secretKey,ips);
//            instance.init(Cipher.ENCRYPT_MODE,secretKey,random);

            byte[] bytes = instance.doFinal(data.getBytes());

            CodeUtil.printByteArray(bytes);



            instance.init(Cipher.DECRYPT_MODE,secretKey,ips);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
