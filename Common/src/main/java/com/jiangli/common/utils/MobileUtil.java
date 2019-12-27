package com.jiangli.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * @author Jiangli
 * @date 2019/5/23 14:46
 */
public class MobileUtil {

    /**
     * 手机号加密
     * @author JinXing
     * @date 2018/12/18 16:47
     */
    public static String encryptMobile(String mobile){
        if(StringUtils.isEmpty(mobile)){
            return null;
        }

        int length = mobile.length();
        int minIndex=3;
        int maxIndex=7;
        if(length >=minIndex && length <maxIndex){
            StringBuilder tempBuilder = new StringBuilder();
            for (int i = 0; i< length -minIndex; i++){
                tempBuilder.append("*");
            }
            return mobile.substring(0,3)+tempBuilder;
        }else if(length >=maxIndex){
            return mobile.substring(0,3)+"****"+mobile.substring(7);
        }
        return mobile;
    }

    /**
     * 邮箱加密
     * @author JinXing
     * @date 2018/12/18 16:47
     */

    private static String encryptEmail(String email){
        if(StringUtils.isEmpty(email)){
            return null;
        }

        /**
         * 邮箱掩码规则：
         * @字符前4位掩码，其余正常显示（如果邮箱前缀小于4位，则前缀全部显示星号
         */
        int index = email.indexOf("@");
        int prefixIndex=3;
        if(index > prefixIndex){
            return email.substring(0,index-4)+"****"+email.substring(index);
        }

        StringBuilder prefixBuffer=new StringBuilder("");
        for (int i=0;i <index;i++){
            prefixBuffer.append("*");
        }
        email = prefixBuffer.toString()+email.substring(index);

        return email;
    }


    public static <T>  void batchEncryptMobile(Iterable<T> list,  Function<T,String> getter, BiConsumer<T,String> setter) {
        if (list != null) {
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (next != null) {
                    String apply = getter.apply(next);
                    if (apply != null) {
                        String value  = null;

                        try {
                            value = encryptMobile(value);
                        } catch (Exception e) {
                        }

                        if (value != null && setter!=null) {
                            setter.accept(next,value);
                        }
                    }
                }
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(encryptEmail("123456789@qq.com"));
        System.out.println(encryptMobile("13712345678"));
    }
}
