package com.jiangli.crawler.webmagic.gamesky

import com.jiangli.common.utils.PathUtil
import com.jiangli.crawler.utils.ImageUtil
import org.springframework.core.io.ClassPathResource
import us.codecraft.webmagic.Page
import us.codecraft.webmagic.Site
import us.codecraft.webmagic.Spider
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader
import us.codecraft.webmagic.processor.PageProcessor
import java.net.URLDecoder

/**
 *
 * https://my.oschina.net/flashsword/blog/147334
 *
 * @author Jiangli
 * @date 2017/9/22 8:55
 */
fun main(args: Array<String>) {
    val url = "https://item.mi.com/product/10000070.html"
//    val url = "http://www.gamersky.com/news/201709/958928.shtml"
    val dUrl = URLDecoder.decode(url, "utf8")
    println(dUrl)

    val classPathResource = ClassPathResource("selecnium_config.ini")
    println(classPathResource.path)
    println(classPathResource.uri)
    println(classPathResource.url)
    println(PathUtil.getClassFileURI(ImageUtil.javaClass,"selecnium_config.ini"))
    println(PathUtil.getClassFile(ImageUtil.javaClass,"selecnium_config.ini"))


    System.setProperty("selenuim_config", PathUtil.getClassFile(ImageUtil.javaClass,"selecnium_config.ini").absolutePath)
//    System.getProperties().setProperty("webdriver.chrome.driver", "C:\\Users\\DELL-13\\Desktop\\chromedriver_win32\\chromedriver.exe")

    var i = 10
    var ts = System.currentTimeMillis()

    while (i-- > 0) {
        Spider.create(object : PageProcessor {
            internal var site = Site.me().setRetryTimes(3).setSleepTime(1000)

            override fun getSite(): Site {
                return site
            }

            override fun process(page: Page?) {
    //            println(page?.rawText)

    //            val html = page?.html!!
    //            println(html.`$`(".Comment"))


                val html = page?.html!!
                println(html.`$`("#J_list"))

            }
        })
    //            .addUrl("""http://cm.gamersky.com/commentapi/count?callback=cb&topic_source_id=958921%2C959038%2C957850%2C959035%2C959031%2C958949%2C958983%2C958996%2C958844%2C958935%2C958903%2C958943%2C958953%2C958933%2C957853%2C958863%2C958979%2C958337%2C958939%2C958879%2C958852%2C958737%2C958825%2C958829%2C958492%2C958809%2C958736%2C958836%2C958706%2C958552%2C958797%2C958550%2C958799%2C958233%2C958798%2C955830%2C958790%2C955850%2C958792%2C958766%2C958315%2C958660%2C958518%2C958785%2C958782%2C957234%2C958426%2C956517%2C958775%2C958452%2C959377%2C959375%2C959362%2C959373%2C959363%2C959357%2C959360%2C959359%2C959354%2C959206%2C959352%2C959351%2C959218%2C959346%2C959341%2C959151%2C959276%2C959210%2C959272%2C959278%2C959312%2C959301%2C959268%2C959267%2C958866%2C959259%2C959246%2C959242%2C959235%2C959229%2C959200%2C959154%2C959204%2C959217%2C959247%2C958834%2C958787%2C958763%2C958762%2C958750%2C959062%2C959310%2C959244%2C959179%2C959270%2C959161%2C959124%2C959066%2C959055%2C959063%2C958993%2C958971%2C958966%2C958959%2C958912%2C958932%2C958811%2C958828%2C958736%2C958808%2C959084%2C959079%2C959089%2C958858%2C959052%2C959017%2C959019%2C958790%2C958518%2C958502%2C958576%2C958264%2C958458%2C958128%2C958403%2C958324%2C958192%2C958186%2C957707%2C957787%2C959356%2C959159%2C958909%2C958766%2C958291%2C958606%2C958586%2C958570%2C958491%2C958187%2C958131%2C957917%2C957888%2C957661%2C957658%2C957659%2C957486%2C957577%2C957468%2C957346%2C958915%2C959335%2C959262%2C959048%2C959049%2C958979%2C958939%2C958888%2C958249%2C958864%2C958792%2C958791%2C942954%2C958298%2C958436%2C958288%2C958278%2C958282%2C958279%2C958274%2C958829%2C958492%2C958836%2C958622%2C958516%2C958467%2C958378%2C958247%2C958139%2C958105%2C957830%2C957706%2C957629%2C957378%2C957214%2C957143%2C957130%2C956890%2C956696%2C956663%2C959374%2C959314%2C959287%2C959122%2C958800%2C958315%2C958785%2C958722%2C958365%2C958190%2C957764%2C958006%2C957988%2C957846%2C957851%2C957678%2C957643%2C957650%2C957289%2C956919%2C958233%2C957287%2C949900&_=1506041766611""".trimMargin())
    //            .addUrl(url.trimMargin())
                .setDownloader(SeleniumDownloader())
    //            .setDownloader(SeleniumDownloader("C:\\Users\\DELL-13\\Desktop\\chromedriver_win32\\chromedriver.exe"))
    //            .run()
                .test(url.trimMargin())


        println("cur:$i ts:${System.currentTimeMillis() - ts}")
        ts = System.currentTimeMillis()
    }

}