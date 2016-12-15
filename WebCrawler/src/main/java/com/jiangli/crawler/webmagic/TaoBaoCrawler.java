package com.jiangli.crawler.webmagic;

import com.jiangli.common.utils.PathUtil;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Jiangli
 * @date 2016/12/15 9:23
 */
public class TaoBaoCrawler {
    private static Logger logger = LoggerFactory.getLogger(TaoBaoCrawler.class);

    public static void main(String[] args) throws MalformedURLException {
        URL configURL = PathUtil.getClassFileURI(TaoBaoCrawler.class, "log4j.properties").toURL();
        PropertyConfigurator.configure(configURL);
        System.out.println(configURL);

        logger.debug("!!!!!!"+configURL.toString());

        Spider.create(new TaobaoPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://github.com/code4craft")
//                .addUrl("https://s.taobao.com/search?q=%E5%BC%80%E5%BF%83%E6%9E%9C&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index&spm=a21bo.50862.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20161215")
                //开启5个线程抓取
//                .addPipeline(new PrintPipeline())
                .addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
                .thread(5)
                //启动爬虫
                .run();
    }

}
