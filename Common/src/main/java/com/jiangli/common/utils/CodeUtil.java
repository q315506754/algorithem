package com.jiangli.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static byte[] hexStringToByteArray(String hexStr){
        byte[] ret = new byte[hexStr.length()/2];
        int count=0;
        for (int i = 0; i < hexStr.length(); i+=2) {
            int high = charToint(hexStr.charAt(i));
            int low = charToint(hexStr.charAt(i + 1));
            int x = (high<<4) + low;//+ 优先于 <<  所以 << 需要括起来
            ret[count++] = (byte)x;
        }
        return ret;
    }

    public static List<String> unicodeList(String str){
        List<String> ret = new LinkedList<>();
        try {
            byte[] unicodes = str.getBytes("unicode");
            for (int i = 2,l=str.length(); l>0; i+=2,l--) {
                byte[] range = ArrayUtil.getRange(unicodes, i, 2);
                ret.add(toHexString(range));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ret;
    }
    public static String unicodeStr(String str){
        List<String> strings = unicodeList(str);
        StringBuilder sb = new StringBuilder();
        for (String one : strings) {
            sb.append("\\u" + one);
        }
        return sb.toString();
    }

    public static String unicodeToUtf8Str(String str){
        String uniStr = str;

        Map<String,String> cache = new HashMap<>();
        Pattern compile = Pattern.compile("\\\\u.{4}");
        Matcher matcher = compile.matcher(uniStr);
        while (matcher.find()) {
            String group = matcher.group();

            String hexStr = group.substring(2);
            try {
                cache.put(group,new String(hexStringToByteArray(hexStr),"unicode"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        for (String unic : cache.keySet()) {
            uniStr = uniStr.replaceAll("\\"+unic, cache.get(unic));
        }

        return uniStr;
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

    public static int charToint(char c){
        int x = (int) c;

        //0-9
        if(x>=48 && x<=57){
            return x - 48;
        }

        //A-Z
        if(x>=65 && x<=90){
            return x - 55;
        }

        //a-z
        if(x>=97 && x<=122){
            return x - 87;
        }

        return -1;
    }
}
