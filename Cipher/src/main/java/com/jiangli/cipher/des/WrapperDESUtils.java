package com.jiangli.cipher.des;

/**
 * DES加密解密工具封装
 *
 * @author cairuirui
 * @create 2018-01-09
 */
public class WrapperDESUtils {

    /**
     * 使用默认密钥进行DES加密
     */
    public static String encrypt(String plainText) {
        try {
            return new DESUtils().encrypt(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    public static String encrypt(String plainText, String key) {
        try {
            return new DESUtils(key).encrypt(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用默认密钥进行DES解密
     */
    public static String decrypt(String plainText) {
        try {
            return new DESUtils().decrypt(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 使用指定密钥进行DES解密
     */
    public static String decrypt(String plainText, String key) {
        try {
            return new DESUtils(key).decrypt(plainText);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String str = "71fb34193121b8904d560e44690c915bbd5e5f471c55993a0450284f31017be9e29b488eef13bdf1f426df920761364d194764d29097884e13a7428f44dce6f5ab289df1cad1a2df89d0a4b92ede1610f5c05952f0a9f66bb1da53034c749446fb70deec897cbb62ad1a2a4c535029c8a7bfe182f930351e7e3f416ca06c4428c7b5402c8c23e54f226e3e2741f7e93242572f4894734404388af4981ced71381b27ce22fea180aa00b186f7899a25125db1a1fe0d66a6c9050b3a141504cacb58236e45dcf775daef0ce97c1e39d3e56a5c91c8c15d78a7ad8ee84878cce5bf052c3e48e619e3974366606be5c20efc1f259bd8fd2847a40e899326acc8a3053fae4873b6f744a74eeb136ee3c05025d18d9e94a9069b34579ba09bb887588c7e90801e9804c26f675ddc17b94a190f8d440b7bb02254210c27633dc4d3eb5c59d712fd642250c42f6fec43a785c450637799984d05cc4d1a0eef74d631e0523910c0b881e48da141f3a2d6a6badcf27532abffe0810f50003b903be93025e8063f7eb3c57ab1174fa2c8540c73b24339659c7b067da9bc";
//        String t = "";
//        t = WrapperDESUtils.encrypt(str);
//        System.out.println("加密后：" + t);
        System.out.println("解密后：" + WrapperDESUtils.decrypt(str));
    }
}
