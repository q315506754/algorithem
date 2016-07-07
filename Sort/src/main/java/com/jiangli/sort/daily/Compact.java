package com.jiangli.sort.daily;

/**
 *
 * aaabccddf->a3bc2d2f
 * @author Jiangli
 *
 *         CreatedTime  2016/7/5 0005 10:11
 */
public class Compact {
    public static void main(String[] args) {
        System.out.println(compress("aaabccddf"));
        System.out.println(compress("aaabccddffff"));
        System.out.println(compress("abcd"));
        System.out.println(compress(""));
        System.out.println(compress("1112334"));
    }

    public static String compress(String str){
        StringBuilder sb = new StringBuilder();
        Character pref= null;
        int contiN = 1;
        for (int i = 0; i < str.length(); i++) {
            char cur = str.charAt(i);
            boolean continuous = pref == null?false:cur==pref;
            if (!continuous) {
                if (contiN >1) {
                    sb.append(contiN);
                    contiN=1;
                }
                sb.append(cur);
            } else {
                contiN++;
            }

            pref = cur;
        }

        if (contiN >1) {
            sb.append(contiN);
        }

        return sb.toString();
    }

}
