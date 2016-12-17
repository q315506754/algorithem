package com.jiangli.crawler.webmagic.six;

import com.jiangli.common.utils.DateUtil;
import com.jiangli.crawler.webmagic.six.model.TableModel;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class ContentPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    private static final  Pattern ptn_type = Pattern.compile("(?<=<em>\\[<a href=\"http.{0,200}\">).*?(?=</a>)");
    private static final Pattern ptn_url = Pattern.compile("(?<=<span id=\".{0,50}?\"><a href=\")http://.*?(?=\")");
    private static final Pattern ptn_title = Pattern.compile("(?<=<span id=\".{0,50}?\"><a href=\"http.{0,200}\">).*?(?=</a>)");
    private static final Pattern ptn_size = Pattern.compile("(?<=>).*?(?=\\s*/)");
    private static final Pattern ptn_mediaType = Pattern.compile("(?<=/).*?(?=</)");
    private static final Pattern ptn_createTime = Pattern.compile("\\d{4}-\\d{0,2}-\\d{0,2}");
    private static final Pattern ptn_lastEndorseTime = Pattern.compile("\\d{4}-\\d{0,2}-\\d{0,2}\\s+\\d{0,2}:\\d{0,2}");
    private static final Pattern ptn_id = Pattern.compile("^\\S*(?=\\s{0,10})");

    class FilterRs{
        int code=0;
        String key;
    }

    public String match(Pattern pattern,String src) {
        try {
            Matcher matcher = pattern.matcher(src);
            matcher.find();
            return matcher.group().trim();
        } catch (Exception e) {
            return "NOT_FOUND for:"+src;
        }
    }
    public String url(String src) {
        return src.replaceAll("&amp;", "&");
    }

    public FilterRs exclude(String title) {
        FilterRs rs = new FilterRs();
        for (String line : Conf.keyExcludeList) {
            if (title.contains(line)) {
                rs.key = line;
                rs.code = -1;
                return rs;
            }
        }
        for (String line : Conf.keyList) {
            if (title.contains(line)) {
                rs.key = line;
                rs.code = 1;
                return rs;
            }
        }
        return rs;
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        boolean is_table_page = page.getUrl().regex(Conf.listRegex).match();
        boolean is_detail_page = page.getUrl().regex(Conf.detailRegex).match();
//        System.out.println(is_table_page);
//        System.out.println(is_detail_page);

        if (is_table_page) {
            List<String> mainTable = html.xpath("//div[@class='mainbox threadlist']//tbody[@id]").all();
//            System.out.println(mainTable);
//            System.out.println(mainTable.size());
//        System.out.println(page);

            boolean requestNextPage = true;
            for (String tbody : mainTable) {
//                System.out.println(tbody+"!!!!!!!!!!");

                TableModel one = new TableModel();
                one.setMarked(0);

                String[] rows = tbody.split("\n");

                one.setTitle(match(ptn_title,rows[4]));

                //filter
                FilterRs filterRs = exclude(one.getTitle());
                if (filterRs.code<=0) {
                    continue;
                }
                one.setMatchKey(filterRs.key);

                one.setType(match(ptn_type,rows[4]));
                one.setUrl(match(ptn_url,rows[4]));
                one.setCreateTime(match(ptn_createTime,rows[5]));
                one.setLastEndorseTime(match(ptn_lastEndorseTime,rows[8]));
                one.setSize(match(ptn_size,rows[7]).toUpperCase());
                one.setMediaType(match(ptn_mediaType,rows[7]).toLowerCase());
                one.setId(match(ptn_id,one.getTitle()).toUpperCase());

                System.out.println(one);

                try {
                    if(DateUtil.getDate_yyyy_MM_dd(one.getCreateTime()).getTime() < Conf.createTimeGTE){
                        requestNextPage = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                int n = 0;
//                for (String row : rows) {
//                    System.out.println((n++)+":"+row);
//                }

                try {
                    String dbTitle = Conf.mapper.selectByTitle(one.getTitle());

                    if (dbTitle == null) {
                        Conf.mapper.insert(one);
                    } else {
                        System.out.println("already exists..");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            Selectable xpath = html.xpath("//div[@class='pages_btns']//div[@class='pages']//a[@class='next']");
//            System.out.println(xpath);
            String nextBtn = xpath.regex("http://.*\\.html").get();
            if (nextBtn==null) {
                nextBtn = xpath.regex("http://.*page=\\d+").get();
            }
            System.out.println("org nextBtn:"+nextBtn);
            nextBtn = url(nextBtn);
            System.out.println("nextBtn:"+nextBtn);

            if (requestNextPage) {
                page.addTargetRequest(nextBtn);
            } else {
                System.out.println("interrupt next page...");
            }
        }


    }

    @Override
    public Site getSite() {
        return site;
    }
}
