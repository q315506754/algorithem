package com.jiangli.crawler.webmagic.six;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class RegexTest {
    public static String match(Pattern pattern,String src) {
        Matcher matcher = pattern.matcher(src);
        matcher.find();
        return matcher.group().trim();
    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String r4 = "<th class=\"lock\"> <label> &nbsp;</label> <em>[<a href=\"http://www.baidu.com/forum/forumdisplay.php?fid=58&amp;filter=type&amp;typeid=1255\">教学视频</a>]</em> <span id=\"thread_9652539\"><a href=\"http://www.baidu.com/realpage/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255\">shipin名字阿瑟大 时代阿斯顿阿 斯 顿按时！</a></span> <img src=\"images/attachicons/common.gif\" alt=\"附件\" class=\"attach\"> <span class=\"threadpages\"> &nbsp; <a href=\"http://www.baidu.com/forum/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255&amp;page=1\">1</a> <a href=\"http://www.baidu.com/forum/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255&amp;page=2\">2</a> </span> </th> ";
        String r5 ="<td class=\"author\"> <cite> <a href=\"http://www.baidu.com/forum/space.php?action=viewpro&amp;uid=11797557\">chriscfleung</a><img src=\"images/thankyou.gif\" border=\"0\" align=\"absmiddle\">37 </cite> <em>2016-5-14</em> </td> ";
        String r7 ="<td class=\"nums\">1.21G / MP4 </td> ";
        String r8 ="<td class=\"lastpost\"> <em><a href=\"http://www.baidu.com/forum/redirect.php?tid=9652539&amp;goto=lastpost#lastpost\">2016-5-18 04:37</a></em> <cite>by <a href=\"http://www.baidu.com/forum/space.php?action=viewpro&amp;username=C-B\">C-B</a></cite> </td> ";

        Pattern ptn_type = Pattern.compile("(?<=<em>\\[<a href=\"http.{0,200}\">).*?(?=</a>)");
        Pattern ptn_url = Pattern.compile("(?<=<span id=\".{0,50}?\"><a href=\")http://.*?(?=\")");
        Pattern ptn_title = Pattern.compile("(?<=<span id=\".{0,50}?\"><a href=\"http.{0,200}\">).*?(?=</a>)");
        Pattern ptn_size = Pattern.compile("(?<=>).*?(?=\\s*/)");
        Pattern ptn_mediaType = Pattern.compile("(?<=/).*?(?=</)");
        Pattern ptn_createTime = Pattern.compile("\\d{4}-\\d{0,2}-\\d{0,2}");
        Pattern ptn_lastEndorseTime = Pattern.compile("\\d{4}-\\d{0,2}-\\d{0,2}\\s+\\d{0,2}:\\d{0,2}");

        System.out.println("type:"+match(ptn_type,r4));
        System.out.println("url:"+match(ptn_url,r4));
        System.out.println("title:"+match(ptn_title,r4));
        System.out.println("size:"+match(ptn_size,r7).toUpperCase());
        System.out.println("mediaType:"+match(ptn_mediaType,r7).toLowerCase());
        System.out.println("createTime:"+match(ptn_createTime,r5));
        System.out.println("lastEndorseTime:"+match(ptn_lastEndorseTime,r8));

        l = System.currentTimeMillis() -l;
        System.out.println("cost:"+l);
    }

}
