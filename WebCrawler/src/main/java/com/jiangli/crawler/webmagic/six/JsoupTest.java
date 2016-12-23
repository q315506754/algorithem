package com.jiangli.crawler.webmagic.six;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class JsoupTest {

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        String r4 = "<table><th class=\"lock\"> <label> &nbsp;</label> <em>[<a href=\"http://www.baidu.com/forum/forumdisplay.php?fid=58&amp;filter=type&amp;typeid=1255\">教学视频</a>]</em> <span id=\"thread_9652539\"><a href=\"http://www.baidu.com/realpage/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255\">shipin名字阿瑟大 时代阿斯顿阿 斯 顿按时！</a></span> <img src=\"images/attachicons/common.gif\" alt=\"附件\" class=\"attach\"> <span class=\"threadpages\"> &nbsp; <a href=\"http://www.baidu.com/forum/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255&amp;page=1\">1</a> <a href=\"http://www.baidu.com/forum/viewthread.php?tid=9652539&amp;extra=page%3D4%26amp%3Bfilter%3Dtype%26amp%3Btypeid%3D1255&amp;page=2\">2</a> </span> </th> ";
        String r5 ="<td class=\"author\"> <cite> <a href=\"http://www.baidu.com/forum/space.php?action=viewpro&amp;uid=11797557\">chriscfleung</a><img src=\"images/thankyou.gif\" border=\"0\" align=\"absmiddle\">37 </cite> <em>2016-5-14</em> </td> ";
        String r7 ="<td class=\"nums\">1.21G / MP4 </td> ";
        String r8 ="<td class=\"lastpost\"> <em><a href=\"http://www.baidu.com/forum/redirect.php?tid=9652539&amp;goto=lastpost#lastpost\">2016-5-18 04:37</a></em> <cite>by <a href=\"http://www.baidu.com/forum/space.php?action=viewpro&amp;username=C-B\">C-B</a></cite> </td> </table>";
        String title ="SDDE-339「zf xz qqq」sdfsdfsdf aaaaaaaaaaaaa";
        String total = r4+r5+r7+r8;

        Document parse = Jsoup.parse(total);
        System.out.println(parse);

//        long l = System.currentTimeMillis();

        System.out.println("type:"+parse.select("a").first().text());
        System.out.println("url:"+parse.select("span").get(0).select("a").first().attr("abs:href"));
        System.out.println("title:"+parse.select("span").get(0).select("a").first().text());
        System.out.println("size:"+parse.select("td").get(1).text());
        System.out.println("mediaType:"+parse.select("td").get(1).text());
        System.out.println("lastEndorseTime:"+parse.select("td").get(2).select("a").first().text());
        System.out.println("createTime:"+parse.select("td").get(0).select("em").first().text());
//        System.out.println("id:"+match(ptn_id,title));

        l = System.currentTimeMillis() -l;
        System.out.println("cost:"+l);


    }

}
