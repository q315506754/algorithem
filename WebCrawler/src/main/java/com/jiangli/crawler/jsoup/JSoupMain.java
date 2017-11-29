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
//            doc = Jsoup.connect("http://192.168.9.252:8080/jenkins/job/%E5%BC%80%E5%8F%91-%E9%A2%84%E5%8F%91codis%20%E6%93%8D%E4%BD%9C%EF%BC%88%E5%8D%95%E6%9D%A1%E5%91%BD%E4%BB%A4%EF%BC%89/3443/consoleText").get();
            System.out.println(doc);
            Elements newsHeadlines = doc.select(".section-header li");
            System.out.println(newsHeadlines);
            System.out.println(newsHeadlines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
