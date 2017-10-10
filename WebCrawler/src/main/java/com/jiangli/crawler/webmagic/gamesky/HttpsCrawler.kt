package com.jiangli.crawler.webmagic.gamesky

import com.jiangli.crawler.webmagic.HttpsDisableUtil
import org.jsoup.Jsoup


/**
 *
 *
 * @author Jiangli
 * @date 2017/9/22 8:55
 */
fun main(args: Array<String>) {
    HttpsDisableUtil.disableSSLCertCheck()

    val keyWords = "战狼2 百度云"
    val maxPage = 2
    val urlFeature = "https://pan.baidu.com/s/[a-zA-Z0-9]*"


    var curIndex = 0
    val pageSize = 10
    val totalRecords = maxPage * pageSize
    val regex = urlFeature.toRegex()
//    fun getUrl() = "https://www.baidu.com/s?wd=${URLEncoder.encode(keyWords, "utf8")}&&pn=$curIndex"
    fun getUrl() = "https://pan.baidu.com/s/1kVDqjkF"
    val url = getUrl()
    val dUrl = url
    val rs = mutableListOf<BDYunResult>()
    println(dUrl)

    val document = Jsoup.connect(dUrl).get()
    println(document.select("#content_left"))
    println(document)


}