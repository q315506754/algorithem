package com.jiangli.crawler.jsoup;

import com.jiangli.common.utils.PathUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;

/**
 * @author Jiangli
 * @date 2016/12/22 16:57
 */
public class JSoupLearnModifyingData {
    public static void main(String[] args) {
        printDoc();
        printSplit();

        addClass();
        printSplit();

        setAttr();
        printSplit();

        Document doc = getDocument();
        doc.select("style").remove();
        Element content = doc.select("#content").first();
        content.html("after html...");
        System.out.println(doc);
        System.out.println(doc.body());
    }

    private static void setAttr() {
        Document doc = getDocument();
        Elements img = doc.select("img");
        img.wrap("<li><a href='picpic'>hhhaaa</a></li>");
        System.out.println(img);
        System.out.println(doc);

        Element first = doc.select("#console").first();
        first.html("<p>lorem ipsum</p>")
        .prepend("<p>First</p>")
        .append("<p>Last</p>");
        System.out.println("append.."+first);
        System.out.println(doc);
    }

    private static void addClass() {
        Document doc = getDocument();
        Elements a = doc.select("a");
        a.addClass("hahaha");
        System.out.println(doc);
    }


    private static void printDoc() {
        try {
            Document doc = getDocument();
            System.out.println("doc:"+doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Document getDocument()   {
        try {
            File input = PathUtil.getClassFile(JSoupLearnInput.class, "Example Domain.html");
        return Jsoup.parse(input, "UTF-8", "http://www.xx.com/");
//            return Jsoup.parse(input, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void printSplit() {
        System.out.println("----------------------------");
    }

}
