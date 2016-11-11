package com.jiangli.code.test;

import com.jiangli.common.utils.ArrayUtil;
import com.jiangli.common.utils.CodeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jiangli
 * @date 2016/11/11 9:57
 */
public class UnicodeTest {
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

    @Test
    public void test_uniconvert() {
        String str = "你好,wo是jiangl"+new Date().toString();
        System.out.println(str);
        String uniStr = CodeUtil.unicodeStr(str);
        System.out.println(uniStr);
        System.out.println(CodeUtil.unicodeToUtf8Str(uniStr));
    }


    @Test
    public void test_chartoint() {
        char c = 'a';
        char c2 = '8';
        System.out.println((int)c);
        System.out.println((int)c2);
        System.out.println(CodeUtil.charToint(c));
        System.out.println(CodeUtil.charToint(c2));
        ArrayUtil.print(CodeUtil.hexStringToByteArray("662f"));
        String uniStr = CodeUtil.unicodeStr("按时打算");
        System.out.println("----"+ uniStr);

        String uniStr2 = "\\u6309\\u65f6\\u6253\\u7b97\\u6309\\u65f6\\u6253\\u7b97";
        System.out.println(uniStr2);

        Map<String,String> cache = new HashMap<>();
        Pattern compile = Pattern.compile("\\\\u.{4}");
        Matcher matcher = compile.matcher(uniStr2);
        while (matcher.find()) {
            String group = matcher.group();

            System.out.println("~"+group);
            String hexStr = group.substring(2);
            try {
                cache.put(group,new String(CodeUtil.hexStringToByteArray(hexStr),"unicode"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//            cache.put(group);
        }
        System.out.println(cache);
        System.out.println(CodeUtil.unicodeToUtf8Str("姑姑asdasd"+uniStr2));

        for (String unic : cache.keySet()) {
            uniStr2 = uniStr2.replaceAll("\\"+unic, cache.get(unic));
        }
        System.out.println(uniStr2);

    }

    @Test
    public void test_fsd() {
        String hexStr = "554a";
        hexStr = hexStr.toLowerCase();
        byte[] ret = new byte[hexStr.length()/2];
        int count=0;
        for (int i = 0; i < hexStr.length(); i+=2) {
            int high = CodeUtil.charToint(hexStr.charAt(i));
            int low = CodeUtil.charToint(hexStr.charAt(i + 1));
            int x = (high<<4) + low;//+ 优先于 <<  所以 << 需要括起来
            ret[count++] = (byte)x;
        }
        ArrayUtil.print(ret);
    }


    @Test
    public void func2() {
        String name="啊啊啊";


        try {
            StringBuilder sb = new StringBuilder();
            byte[] unicodes = name.getBytes("unicode");
            for (int i = 2,l=name.length(); l>0; i+=2,l--) {
                byte[] range = ArrayUtil.getRange(unicodes, i, 2);
                sb.append("\\u"+CodeUtil.toHexString(range));
            }
            System.out.println(sb.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test_asd() {
        String name="是";
        String name2="啊啊啊";
        String name3="a";
        try {
            byte[] unicodes = name.getBytes("unicode");
            byte[] unicodes2 = name2.getBytes("unicode");
            byte[] unicodes3 = name3.getBytes("unicode");
            CodeUtil.printByteArray(unicodes);
            CodeUtil.printByteArray(unicodes2);
            CodeUtil.printByteArray(unicodes3);
            ArrayUtil.print(unicodes,0,8);
            ArrayUtil.print(unicodes2,0,8);
            ArrayUtil.print(unicodes3,0,8);
            //对于unicode 前2个字节为固定 输出为-2,-1  feff
            //每个字符占用2字节 不论中英文

            System.out.println("\u662f");

            System.out.println(new String(unicodes2,"unicode"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
