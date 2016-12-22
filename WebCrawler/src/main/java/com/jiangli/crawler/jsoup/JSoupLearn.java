package com.jiangli.crawler.jsoup;

import com.jiangli.common.utils.PathUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;

/**
 * @author Jiangli
 * @date 2016/12/22 16:57
 */
public class JSoupLearn {
    public static void main(String[] args) {
        fromHtml();
        printSplit();

        fromFragment();
        printSplit();

        fromUrl();
        printSplit();

        fromUrlChain();
        printSplit();

        fromFile();
        printSplit();
    }

    private static void fromFile() {
        try {

            File input = PathUtil.getClassFile(JSoupLearn.class, "Example Domain.html");
            Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
            System.out.println("doc:"+doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fromUrlChain()   {
        try {
            Document doc = Jsoup.connect("http://example.com")
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .post();
            System.out.println("doc:"+doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fromUrl()   {
        try {
            Document doc = Jsoup.connect("http://www.baidu.com/").get();
            String title = doc.title();
            System.out.println("title:"+title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void fromHtml() {
        String html = "<html><head><title>First parse</title></head>"
                + "<body><p>Parsed HTML into a doc.</p></body></html>";
        Document doc = Jsoup.parse(html);
        System.out.println("doc:"+doc);
    }

    private static void fromFragment() {
        String html = "<div><p>Lorem ipsum.</p>";
        Document doc = Jsoup.parseBodyFragment(html);
        Element body = doc.body();
        System.out.println("doc:"+doc);
        System.out.println("body:"+body);
    }

    private static void printSplit() {
        System.out.println("----------------------------");
    }

}
