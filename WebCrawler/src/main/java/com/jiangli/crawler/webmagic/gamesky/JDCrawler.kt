package com.jiangli.crawler.webmagic.gamesky

import com.jiangli.common.utils.PathUtil
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.processor.PageProcessor
import java.net.URLDecoder

/**
 *
 *
 * @author Jiangli
 * @date 2017/9/22 8:55
 */
fun main(args: Array<String>) {
    val url = "https://search.jd.com/Search?keyword=gtx1080&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=gtx1080&page=3&s=55&click=0"
    val dUrl = URLDecoder.decode(url, "utf8")
    println(dUrl)

    System.setProperty("selenuim_config", PathUtil.getClassFile(com.jiangli.crawler.utils.ImageUtil.javaClass, "selecnium_config.ini").absolutePath)

    Spider.create(object : PageProcessor {
        internal var site = Site
                .me()
                .setRetryTimes(3)
                .setSleepTime(1000)
                .addHeader("Host","search.jd.com")
                .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Accept-Language","zh-CN,zh;q=0.8,en;q=0.6")
                .addHeader("Accept-Encoding","gzip, deflate, br")
                .addHeader("Cache-Control","max-age=0")
                .addHeader("Connection","keep-alive")
                .addHeader("Upgrade-Insecure-Requests","1")
                .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36")

        override fun getSite(): Site {
            return site
        }

        override fun process(page: Page?) {
            val html = page?.html!!
            println(html.`$`("#J_goodsList"))
        }
    })
            .addUrl(url.trimMargin())
//            .setDownloader(SeleniumDownloader())
            .run()
}