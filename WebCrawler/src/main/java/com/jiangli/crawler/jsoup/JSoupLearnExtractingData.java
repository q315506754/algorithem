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
public class JSoupLearnExtractingData {
    public static void main(String[] args) {
        printDoc();
        printSplit();

        operDom();
        printSplit();

        operDomBySelector();
        printSplit();

        operAttr();
        printSplit();
    }

    private static void operAttr() {
        Document doc = getDocument();
        Element link = doc.select("a").first();

        String text = doc.body().text(); // "An example link"
        System.out.println("text:"+text);

        String linkHref = link.attr("href"); // "http://example.com/"
        System.out.println("linkHref:"+linkHref);
        String linkText = link.text(); // "example""
        System.out.println("linkText:"+linkText);

        String linkOuterH = link.outerHtml();
        System.out.println("linkOuterH:"+linkOuterH);
        // "<a href="http://example.com"><b>example</b></a>"
        String linkInnerH = link.html(); // "<b>example</b>"
        System.out.println("linkInnerH:"+linkInnerH);
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

    private static void operDom() {
        try {
            Document doc = getDocument();
//            System.out.println("doc:"+doc);

            Element content = doc.getElementById("content");
            System.out.println("content:"+content);
//            Elements links = content.getElementsByTag("a");
            Elements links = doc.getElementsByTag("a");
            for (Element link : links) {
                String linkHref = link.attr("href");
                String linkText = link.text();
                System.out.println("linkHref:"+linkHref);
                System.out.println("linkText:"+linkText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void operDomBySelector() {
        try {
            Document doc = getDocument();
//            System.out.println("doc:"+doc);

            Elements links = doc.select("a[href]"); // a with href
            Elements pngs = doc.select("img[src$=.png]");
            // img with src ending .png
            System.out.println("links:"+links);
            System.out.println("pngs:"+pngs);
            System.out.println("pngs src:"+pngs.first().attr("abs:src"));
            System.out.println("pngs src:"+pngs.first().attr("src"));

            Element masthead = doc.select("div.masthead").first();
            // div with class=masthead
            System.out.println(masthead);

            Elements resultLinks = doc.select("h3.r > a"); // direct a after h3
            System.out.println(resultLinks);

            Elements p = doc.select("p"); //
            Element a = p.get(1).select("a").get(0);
            System.out.println(a);
            System.out.println(a.parent());
//            System.out.println("parents:"+a.parents());
            System.out.println("parents:"+a.parents().size());
            System.out.println(a.html());
            System.out.println(a.text());
            System.out.println(a.outerHtml());
            System.out.println(a.tagName());
            System.out.println(a.attr("href"));
//            Make sure you specify a base URI when parsing the document (which is implicit when loading from a URL), and
//            Use the abs: attribute prefix to resolve an absolute URL from an attribute:
//            there is also a method Node.absUrl(String key) which does the same thing
            System.out.println(a.attr("abs:href"));
            System.out.println(a.absUrl("href"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printSplit() {
        System.out.println("----------------------------");
    }

}
