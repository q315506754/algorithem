package com.jiangli.doc.statistics

import com.jiangli.common.utils.FileUtil
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.FileInputStream

fun main(args: Array<String>) {
    val dir="""D:\baidu cloud download\17年中级\17年注会"""
    var sum=0
    var priceSingle=0.08
    var priceDouble=0.05
    FileUtil.getFilesFromDirPath(dir){
        t ->
        t.name.endsWith(".docx") || t.name.endsWith(".doc")
    }.forEach {
        var page = 0
        if (it.name.endsWith(".docx")){
            val doc = XWPFDocument(FileInputStream(it))
            val extractor = XWPFWordExtractor(doc)
            page = doc.properties.extendedProperties.underlyingProperties.pages
            //    println(text)
        }
        else if (it.name.endsWith(".doc")){
            val doc = HWPFDocument(FileInputStream(it))
            val extractor = WordExtractor(doc)
            page = doc.summaryInformation.pageCount
            //    println(text)
//            page = doc.properties.extendedProperties.underlyingProperties.pages
        }
        sum+=page

        println("$page "+it)
    }

    println("sum: $sum")
    println("单页打: ${sum*priceSingle} 张数:$sum")
    println("双面打: ${sum*priceDouble} 张数:${(sum+1)/2}")

}