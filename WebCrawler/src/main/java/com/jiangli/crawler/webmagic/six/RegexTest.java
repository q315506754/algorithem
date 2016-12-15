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
        return matcher.group();
    }

    public static void main(String[] args) {
        String r4 = "<th class=\"lock\"> <label> &nbsp;</label> <em>[<a href=\"http://www.sis001.com/forum/forumdisplay.php?fid=58&amp;filter=type&amp;typeid=1255\">SWMT</a>]</em> <span id=\"thread_9652539\"><a href=\"http://www.sis001.com/forum/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255\">SW-406 通勤バスはギュウギュウの満員で目の前には黒パンストのOLだらけ！</a></span> <img src=\"images/attachicons/common.gif\" alt=\"附件\" class=\"attach\"> <span class=\"threadpages\"> &nbsp; <a href=\"http://www.sis001.com/forum/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255&amp;page=1\">1</a> <a href=\"http://www.sis001.com/forum/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255&amp;page=2\">2</a> </span> </th> ";
        String r5 ="<td class=\"author\"> <cite> <a href=\"http://www.sis001.com/forum/space.php?action=viewpro&amp;uid=11797557\">chriscfleung</a><img src=\"images/thankyou.gif\" border=\"0\" align=\"absmiddle\">37 </cite> <em>2016-5-14</em> </td> ";
        String r7 ="<td class=\"nums\">1.21G / MP4 </td> ";
        String r8 ="<td class=\"lastpost\"> <em><a href=\"http://www.sis001.com/forum/redirect.php?tid=9652539&amp;goto=lastpost#lastpost\">2016-5-18 04:37</a></em> <cite>by <a href=\"http://www.sis001.com/forum/space.php?action=viewpro&amp;username=C-B\">C-B</a></cite> </td> ";

        Pattern ptn_url = Pattern.compile("http://.*typeid=\\d+");
        Pattern ptn_type = Pattern.compile("<em>.*</em>");

        System.out.println("type:"+match(ptn_type,r4));
        System.out.println("url:"+match(ptn_url,r4));


    }

}
