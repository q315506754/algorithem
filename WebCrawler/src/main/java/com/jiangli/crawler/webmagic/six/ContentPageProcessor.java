package com.jiangli.crawler.webmagic.six;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class ContentPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private static final Pattern ptn_type = Pattern.compile("\"http://.*\"");

    public String match(Pattern pattern,String src) {
        Matcher matcher = pattern.matcher(src);
        matcher.find();
        return matcher.group();
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        boolean is_table_page = page.getUrl().regex(Conf.listRegex).match();
        boolean is_detail_page = page.getUrl().regex(Conf.detailRegex).match();
        System.out.println(is_table_page);
        System.out.println(is_detail_page);

        if (is_table_page) {
            List<String> mainTable = html.xpath("//div[@class='mainbox threadlist']//tbody[@id]").all();
//            System.out.println(mainTable);
//            System.out.println(mainTable.size());
//        System.out.println(page);

            for (String tbody : mainTable) {
//                System.out.println(tbody+"!!!!!!!!!!");

                TableModel one = new TableModel();

                String[] rows = tbody.split("\n");

                one.setType(match(ptn_type,rows[4]));
                one.setTitle(rows[4]);
                one.setUrl(rows[4]);
                one.setCreateTime(rows[5]);
                one.setLastEndorseTime(rows[7]);
                one.setSize(rows[7]);
                one.setMediaType(rows[8]);

//                System.out.println(one);

//                int n = 0;
//                for (String row : rows) {
//                    System.out.println((n++)+":"+row);
//                }

            }
            String nextBtn = html.xpath("//div[@class='pages_btns']//div[@class='pages']//a[@class='next']").regex("http://.*page=\\d+").get();
            System.out.println("nextBtn:"+nextBtn);
        }


    }

    @Override
    public Site getSite() {
        return site;
    }
}
