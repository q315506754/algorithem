package com.jiangli.crawler.webmagic.gamesky

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

fun main(args: Array<String>) {
    val keyword = "java"
    val URL = "https://baike.baidu.com/item/$keyword"
    val document = Jsoup.connect(URL).get()
//    println(document)
    val select = document.select(".content")
//    println(select)

//    println(select.html())
//    println(select.text())
//    select.eachText().forEach {
//        println(it)
//    }

    printEle(select," ")
}


fun printEle(list: Elements,prefix:String) {
    list.forEach {
        printEle(it, prefix)
    }
}
fun printEle(one: Element, prefix:String) {
    val trim = one.ownText().trim()
    if (trim.isNotEmpty()) {
        println("$trim")
//    println("${prefix}${trim}")
    }

    printEle(one.children(),prefix+prefix)
}