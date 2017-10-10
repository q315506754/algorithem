package com.jiangli.crawler.webmagic.gamesky

import com.jiangli.common.utils.PathUtil
import org.jsoup.Jsoup
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader
import us.codecraft.webmagic.processor.PageProcessor
import us.codecraft.webmagic.selector.Html
import us.codecraft.webmagic.selector.Selectable
import java.io.FileReader
import java.net.URLEncoder
import java.util.*


enum class BDYunProcessRs{
    NOT_HANDLE,NEED_PWD,NOT_AVAILABLE,AVAILABLE
}

data class BDYunResult(val entryText:String,val entryUrl:String,val yunUrl:String,var password:String="",var rs:BDYunProcessRs=BDYunProcessRs.NOT_HANDLE){

}


object PhantomRequester {
    val sConfig = Properties()
    lateinit var webDriver: WebDriver

    init {
        var configFile =PathUtil.getClassFile(com.jiangli.crawler.utils.ImageUtil.javaClass, "selecnium_config.ini").absolutePath
        sConfig.load(FileReader(configFile))
        val sCaps = DesiredCapabilities()
        sCaps.setCapability(
                PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                sConfig.getProperty("phantomjs_exec_path"))

        webDriver = PhantomJSDriver(sCaps)

    }

    fun request(url: String): Html {
        synchronized(this){
            webDriver.get(url)

//        val manage = webDriver.manage()
//        val cookie = Cookie(cookieEntry.key,
//                cookieEntry.value)
//        manage.addCookie(cookie)

            val webElement = webDriver.findElement(By.xpath("/html"))
            val content = webElement.getAttribute("outerHTML")
            val html = Html(content, url)
            return html
        }
    }
}


/**
 *
 *
 * @author Jiangli
 * @date 2017/9/22 8:55
 */
fun main(args: Array<String>) {
    val keyWords = "战狼2 百度云"
    val maxPage = 2
//    val maxPage = 20
    val urlFeature = "https://pan.baidu.com/s/[a-zA-Z0-9]*"


    var curIndex = 0
    val pageSize = 10
    val totalRecords = maxPage * pageSize
    val regex = urlFeature.toRegex()
    fun getUrl() = "https://www.baidu.com/s?wd=${ URLEncoder.encode(keyWords, "utf8")}&&pn=$curIndex"
    val url = getUrl()
    val dUrl = url
    val rs = mutableListOf<BDYunResult>()
    println(dUrl)

//    val document = Jsoup.connect(url).get()
//    println(document)

    System.setProperty("selenuim_config", PathUtil.getClassFile(com.jiangli.crawler.utils.ImageUtil.javaClass, "selecnium_config.ini").absolutePath)

    Spider.create(object : PageProcessor {
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
//            println(html)
            val container = html.`$`("#content_left")
//            container.`$`("h3 a").all().forEach {
//                println(it)
//            }

            fun isNotEmpty(d:Selectable): Boolean { return d.nodes().size>0 }

            container.`$`("h3 a").nodes().forEachIndexed { index, it ->
                //http://webmagic.io/docs/zh/posts/ch4-basic-page-processor/xsoup.html
                val entryUrl = it.links().get()
                val entryText = it.xpath("//a/html()").replace("<em>", "").replace("</em>", "").get()

//                println(entryText)
//                println(entryUrl)

                println("${curIndex+index+1} / $totalRecords")

                var eachHtml: String?=null
                try {
                    val document = Jsoup.connect(entryUrl).get()
//                println()
                    eachHtml = document.html()
                } catch (e: Exception) {
                }


                eachHtml?.let {
                    val findAll = regex.findAll(it)

                    findAll.forEachIndexed { index2, matchResult ->
                        val yunUrl = matchResult.value
//                    println(yunUrl)

                        println("   ${curIndex+index+1}-${index2+1}")

                        val one = BDYunResult(entryText,entryUrl, yunUrl)
                        rs.add(one)

                        val request = PhantomRequester.request(yunUrl)
                        val buttons = request.`$`(".bar .g-button-right")
                        val no_found = request.`$`("#share_nofound_des")
                        val no_found2 = request.`$`(".error-main")
                        val ac = request.`$`("#accessCode")

                        if (isNotEmpty(buttons)) {
                            one.rs=BDYunProcessRs.AVAILABLE
                        }

                        if (isNotEmpty(no_found)) {
                            one.rs=BDYunProcessRs.NOT_AVAILABLE
                        }
                        if (isNotEmpty(no_found2)) {
                            one.rs=BDYunProcessRs.NOT_AVAILABLE
                        }
                        if (isNotEmpty(ac)) {
                            one.rs=BDYunProcessRs.NEED_PWD
                        }

                        if (one.rs==BDYunProcessRs.NOT_HANDLE) {
                            println("$yunUrl "+ request)
                        }

                        println("$yunUrl "+ buttons)
                        println("$yunUrl "+ no_found)
                    }
                }


            }


            curIndex+=pageSize

            if(curIndex<totalRecords){
                //next page
                page.addTargetRequest(getUrl())
            } else {
                //output
    //            println(rs)
                rs.forEachIndexed { index, bdYunResult ->
                    println("${index+1} $bdYunResult")
                }

                PhantomRequester.webDriver.quit()
            }
        }
    })
            .addUrl(dUrl.trimMargin())
            .setDownloader(SeleniumDownloader())
            .run()


}
