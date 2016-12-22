package com.jiangli.crawler.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author Jiangli
 * @date 2016/12/22 16:57
 */
public class JSoupMain {
    public static void main(String[] args) {
        Document doc = null;
        try {
            doc = Jsoup.connect("http://www.mi.com/").get();
            System.out.println(doc);
            Elements newsHeadlines = doc.select(".section-header li");
            System.out.println(newsHeadlines);
            System.out.println(newsHeadlines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
