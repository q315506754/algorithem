package com.jiangli.crawler.webmagic.gamesky

import org.jsoup.Jsoup

fun main(args: Array<String>) {
    val keyword = "java"
    val URL = "https://mp.weixin.qq.com/s?__biz=MzAxOTM3OTg2Mg==&mid=2653549746&idx=1&sn=a59d115f012daea7785681da1be69ad9&chksm=801ab74bb76d3e5dd94497bc26500e5ef1450ac631768f5ee9b08f0d150efbd9cbdf299af126&scene=0#rd"
    val document = Jsoup.connect(URL).get()
//    println(document)
    val select = document.select("#js_article")
    printEle(select," ")
}

