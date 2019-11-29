package com.jiangli.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class PinyinUtil {

    public  enum ResolveType {
        /**
         * 全部拼音 李四Tommy->LISITOMMY
         */
        ALL
        ,/**
         * 全部拼音 李四Tommy->L
         */FIRST_LETTER
        ,/**
         * 全部拼音 李四Tommy->LSTOMMY
         **/FIRST_LETTERS
        ;
    }

    static Map<String, String> map = new HashMap<>();
    static {
        map.put("重庆", "CQ");
    }

    /**
     * 将文字转为汉语拼音
     * @param ChineseLanguage 要转成拼音的中文
     */
    public static String toHanyuPinyin(String ChineseLanguage){
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部小写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V) ;
        try {
            for (int i=0; i<cl_chars.length; i++){
                if (String.valueOf(cl_chars[i]).matches("[\u4e00-\u9fa5]+")){// 如果字符是中文,则将中文转为汉语拼音
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                } else {// 如果字符不是中文,则不转换
                    hanyupinyin += cl_chars[i];
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

    public static String getFirstLettersUp(String ChineseLanguage){
        return getFirstLetters(ChineseLanguage ,HanyuPinyinCaseType.UPPERCASE);
    }

    public static String getFirstLettersLo(String ChineseLanguage){
        return getFirstLetters(ChineseLanguage ,HanyuPinyinCaseType.LOWERCASE);
    }

    public static String getFirstLetters(String ChineseLanguage,HanyuPinyinCaseType caseType) {
        String s = map.get(ChineseLanguage);
        if (s!=null) {
            return s;
        }

        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        try {
            for (int i = 0; i < cl_chars.length; i++) {
                String str = String.valueOf(cl_chars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0].substring(0, 1);
                } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
                    hanyupinyin += cl_chars[i];
                } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母
                    hanyupinyin += cl_chars[i];
                } else {// 否则不转换
                    hanyupinyin += cl_chars[i];//如果是标点符号的话，带着
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

    public static String getPinyinString(String ChineseLanguage){
        try {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
            for (int i = 0; i < cl_chars.length; i++) {
                String str = String.valueOf(cl_chars[i]);
                if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                    hanyupinyin += PinyinHelper.toHanyuPinyinStringArray(
                            cl_chars[i], defaultFormat)[0];
                } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
                    hanyupinyin += cl_chars[i];
                } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母

                    hanyupinyin += cl_chars[i];
                } else {// 否则不转换
                }
            }
            return hanyupinyin;
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }  catch (Exception e) {
            //e.printStackTrace();
        }
        return ChineseLanguage;
    }
    /**
     * 取第一个汉字的第一个字符
     * @Title: getFirstLetter
     * @Description: TODO
     * @return String
     * @throws
     */
    public static String getFirstLetter(String ChineseLanguage){
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = "";
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 输出拼音全部大写
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不带声调
        try {
            String str = String.valueOf(cl_chars[0]);
            if (str.matches("[\u4e00-\u9fa5]+")) {// 如果字符是中文,则将中文转为汉语拼音,并取第一个字母
                hanyupinyin = PinyinHelper.toHanyuPinyinStringArray(
                        cl_chars[0], defaultFormat)[0].substring(0, 1);;
            } else if (str.matches("[0-9]+")) {// 如果字符是数字,取数字
                hanyupinyin += cl_chars[0];
            } else if (str.matches("[a-zA-Z]+")) {// 如果字符是字母,取字母

                hanyupinyin += cl_chars[0];
            } else {// 否则不转换

            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            System.out.println("字符不能转成汉语拼音");
        }
        return hanyupinyin;
    }

    public static <T>  void injectPinyin(Iterable<T> list,ResolveType resolveType, Function<T,String> nameGetter, BiConsumer<T,String> pinyinSetter) {
        if (list != null) {
            Iterator<T> iterator = list.iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (next != null) {
                    String apply = nameGetter.apply(next);
                    if (apply != null) {
                        String value  = null;

                        try {
                            if (resolveType == ResolveType.ALL) {
                                value  =toHanyuPinyin(apply).toUpperCase();
                            }else if (resolveType == ResolveType.FIRST_LETTER) {
                                value  =getFirstLetter(apply).toUpperCase();
                            }else if (resolveType == ResolveType.FIRST_LETTERS) {
                                value  = getFirstLettersUp(apply).toUpperCase();
                            }
                        } catch (Exception e) {
                        }

                        if (value != null && pinyinSetter!=null) {
                            pinyinSetter.accept(next,value);
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(toHanyuPinyin("中秋节"));
        System.out.println(getFirstLetter("中秋节"));
        System.out.println(getFirstLettersUp("中秋节"));
        System.out.println(getFirstLettersUp("张三"));
        System.out.println(getFirstLettersUp("李四"));
        System.out.println(getFirstLettersUp("李四 Jim"));
        System.out.println(getFirstLettersUp("李四 Tommy"));
        System.out.println(getFirstLettersUp("李四Tommy"));

        List<TestPinyin> ds = new ArrayList<>();
        ds.add(new TestPinyin("李四Tommy"));
        ds.add(new TestPinyin("吕布"));
        ds.add(new TestPinyin("诸葛亮"));

        injectPinyin(ds,ResolveType.FIRST_LETTERS,TestPinyin::getName,TestPinyin::setPinyin);

        System.out.println(ds);
    }

    static class TestPinyin {
         String name;
         String pinyin;

        public TestPinyin(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "TestPinyin{" +
                    "name='" + name + '\'' +
                    ", pinyin='" + pinyin + '\'' +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }
    }

}