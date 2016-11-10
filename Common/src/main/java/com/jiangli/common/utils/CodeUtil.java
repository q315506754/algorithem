package com.jiangli.common.utils;

import java.io.IOException;

/**
 * @author Jiangli
 * @date 2016/11/10 15:02
 */
public class CodeUtil {
     public static String toHexString(byte[] digest){
         StringBuilder sb = new StringBuilder();
         for (byte b : digest) {
//                System.out.println(b);
//                System.out.println(b&0xff);
             String str = Integer.toHexString(b & 0xff);
             if (str.length()==1) {
                 str = "0"+str;
             }
             sb.append(str);
//                sb.append(Integer.toHexString(b));
         }
         String s = sb.toString();
         return s;
     }

    /**
     * 编码
     * @param bstr
     * @return String
     */
    public static String base64Encode(byte[] bstr){
        //or use commons-codec Base64
        return new sun.misc.BASE64Encoder().encode(bstr);
    }

    /**
     * 解码
     * @param str
     * @return string
     */
    public static byte[] base64Decode(String str) {
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bt;
    }

    public static void printByteArray(byte[] digest) {
        System.out.println("byte[].length:"+digest.length);//16 byte 128bit

        String s = toHexString(digest);
        System.out.println("byte[] after toHexString:"+s);
        System.out.println("string.length:"+s.length());

        String x = base64Encode(digest);
        System.out.println("byte[] after base64Encode:"+x);
        System.out.println("string.length:"+x.length());
    }
}
