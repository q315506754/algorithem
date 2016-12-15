package com.jiangli.crawler.webmagic.six;

import com.jiangli.common.utils.PathUtil;
import com.jiangli.crawler.webmagic.TaoBaoCrawler;
import org.apache.log4j.PropertyConfigurator;
import us.codecraft.webmagic.Spider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class TestCrawler {
    public static void main(String[] args) throws MalformedURLException {
        URL configURL = PathUtil.getClassFileURI(TaoBaoCrawler.class, "log4j.properties").toURL();
        PropertyConfigurator.configure(configURL);


        Spider.create(new ContentPageProcessor())
                .test(Conf.startTableUrl);
//                .run();

        Spider.create(new ContentPageProcessor())
                .test(Conf.startDetailUrl);
//                .run();
    }
}
