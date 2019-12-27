package com.jiangli.crawler.webmagic.gamesky.core

import org.jsoup.Jsoup

private val URL = "https://m.jlszyy.cc/play/5326-2-38.html"
private fun CONTENT_URL(id: String, page: Int = 0) = """https://m.jlszyy.cc/play/$id${if (page > 1) "-${page-1}" else "0"}.html"""

private fun getContentOfURL(url: String): Content {
    val content = Jsoup.connect(url).get()
    val article1 = content.select("article").first()

    val pagenation = content.select("article .yu-btnwrap")?.first()?.select("span")?.first()
    val pagetxt = pagenation?.text()
    val maxPage: Int = pagetxt?.substring(pagetxt?.lastIndexOf("/") + 1)?.toInt() ?: 1

    return Content(url,"""${article1.select("p").text()}""",maxPage)
}

private fun getContent(id: String): String {
    val content = getContentOfURL(CONTENT_URL(id))


    var ret = StringBuilder()
    ret.append("""1/${content.maxPage}.${content.content}""")

    for(i in 2..content.maxPage){
        val (url,cd_content, _) = getContentOfURL(CONTENT_URL(id, i))
        ret.append("\r\n$i/${content.maxPage}.$cd_content $url")
    }

    return ret.toString()
}


fun main(args: Array<String>) {
    (46..46).forEach {
        val url = CONTENT_URL("5326-2", it)
        println(url)

        val document = Jsoup.connect(URL).get()
        println(document.body())
        document.select("iframe").forEach {
            println(it.attr("src"))
        }
    }

//    val document = Jsoup.connect(URL).get()
////    println(document)
//
////    println(CONTENT_URL("11",2))
////    println(CONTENT_URL("22",1))
//
//    val ul = document.body().select("ul.ymwNews")
////    println(ul)
//
//    ul.select("li").forEach {
//        val alt = it.select("img").first().attr("alt")
//        var imgsrc = it.select("img").first().attr("src")
//        val href = it.select("a").first().attr("href")
//        val time = it.select("time").first().text()
//        val id = it.attr("data-id")
//        println("time:$time tile:$alt img:$imgsrc url:$href")
////        println("$maxPage.${article1.text()}")
//        println(getContent(id))
//        println()
////        println(id)
//    }
}