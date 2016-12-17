package com.jiangli.crawler.webmagic.six;

import com.jiangli.common.utils.PathUtil;
import com.jiangli.crawler.webmagic.TaoBaoCrawler;
import com.jiangli.crawler.webmagic.six.mapper.TableMapper;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import us.codecraft.webmagic.Spider;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Jiangli on 2016/12/15.
 */
public class TableCrawler {
    public static void main(String[] args) throws MalformedURLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:applicationContext-mybatis.xml");
        TableMapper inf = context.getBean(TableMapper.class);
        Conf.mapper = inf;
//        System.out.println(inf);

        URL configURL = PathUtil.getClassFileURI(TaoBaoCrawler.class, "log4j.properties").toURL();
        PropertyConfigurator.configure(configURL);

        Spider.create(new ContentPageProcessor())
                .addUrl(Conf.startTableUrl)
//                .test(Conf.startTableUrl);
                .run();

    }
}
