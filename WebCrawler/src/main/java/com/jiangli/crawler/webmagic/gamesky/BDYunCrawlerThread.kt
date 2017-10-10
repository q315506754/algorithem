package com.jiangli.crawler.webmagic.gamesky

import com.jiangli.common.utils.PathUtil
import org.jsoup.Jsoup
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader
import us.codecraft.webmagic.processor.PageProcessor
import us.codecraft.webmagic.selector.Selectable
import java.net.URLEncoder
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors


/**
 *
 *
 * @author Jiangli
 * @date 2017/9/22 8:55
 */
fun main(args: Array<String>) {
    val keyWords = "战狼2 百度云"
//    val maxPage = 2
    val maxPage = 20
    val urlFeature = "https://pan.baidu.com/s/[a-zA-Z0-9]*"


    var curIndex = 0
    val pageSize = 10
    val totalRecords = maxPage * pageSize
    val regex = urlFeature.toRegex()
    fun getUrl() = "https://www.baidu.com/s?wd=${URLEncoder.encode(keyWords, "utf8")}&&pn=$curIndex"
    val rs = mutableListOf<BDYunResult>()
    val threadPool = Executors.newFixedThreadPool(10)

//    val document = Jsoup.connect(url).get()
//    println(document)

    System.setProperty("selenuim_config", PathUtil.getClassFile(com.jiangli.crawler.utils.ImageUtil.javaClass, "selecnium_config.ini").absolutePath)

    val spider = Spider.create(object : PageProcessor {
        internal var site = Site.me()
                .setRetryTimes(3)
                .setSleepTime(1000)
                .addHeader("Host", "www.baidu.com")
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6")
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Connection", "keep-alive")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")

        override fun getSite(): Site {
            return site
        }

        override fun process(page: Page?) {
            val html = page?.html!!
            val container = html.`$`("#content_left")
            val cUrl = page.url.get()
//            println(cUrl)
            val cIdx = cUrl.substring(cUrl.lastIndexOf("pn=")+3).toInt()

            fun isNotEmpty(d: Selectable): Boolean {
                return d.nodes().size > 0
            }

            val nodes = container.`$`("h3 a").nodes()
            val countDownLatch = CountDownLatch(nodes.size)

            nodes.forEachIndexed { index, it ->
                //http://webmagic.io/docs/zh/posts/ch4-basic-page-processor/xsoup.html
                val entryUrl = it.links().get()
                val entryText = it.xpath("//a/html()").replace("<em>", "").replace("</em>", "").get()

                println("${cIdx + index + 1} / $totalRecords")

                threadPool.execute({
                    var eachHtml: String? = null
                    try {
                        val document = Jsoup.connect(entryUrl).get()
                        eachHtml = document.html()
                    } catch (e: Exception) {
                    }


                    eachHtml?.let {
                        val findAll = regex.findAll(it)

                        findAll.forEachIndexed { index2, matchResult ->
                            val yunUrl = matchResult.value
//                    println(yunUrl)

                            println("   ${cIdx + index + 1}-${index2 + 1}")

                            val one = BDYunResult(entryText, entryUrl, yunUrl)
                            rs.add(one)

                            val request = PhantomRequester.request(yunUrl)
                            val buttons = request.`$`(".bar .g-button-right")
                            val no_found = request.`$`("#share_nofound_des")
                            val no_found2 = request.`$`(".error-main")
                            val ac = request.`$`("#accessCode")

                            if (isNotEmpty(buttons)) {
                                one.rs = BDYunProcessRs.AVAILABLE
                            }

                            if (isNotEmpty(no_found)) {
                                one.rs = BDYunProcessRs.NOT_AVAILABLE
                            }
                            if (isNotEmpty(no_found2)) {
                                one.rs = BDYunProcessRs.NOT_AVAILABLE
                            }
                            if (isNotEmpty(ac)) {
                                one.rs = BDYunProcessRs.NEED_PWD
                            }

                            if (one.rs == BDYunProcessRs.NOT_HANDLE) {
                                println("$yunUrl " + request)
                            }

                            println("$yunUrl " + buttons)
                            println("$yunUrl " + no_found)
                        }
                    }

                    countDownLatch.countDown()
                })
            }

            countDownLatch.await()
        }
    })
    .setDownloader(SeleniumDownloader())
    .thread(10)

    while (curIndex < totalRecords) {
        spider.addUrl(getUrl())

        curIndex+=pageSize
    }
    spider.run()


    rs.forEachIndexed { index, bdYunResult ->
        println("${index + 1} $bdYunResult")
    }

    PhantomRequester.webDriver.quit()
}