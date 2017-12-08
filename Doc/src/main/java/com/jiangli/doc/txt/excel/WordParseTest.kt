package com.jiangli.doc.txt.excel

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


/**
 *
 *
 * @author Jiangli
 * @date 2017/12/7 19:33
 */

fun main(args: Array<String>) {
    val base = "C:\\Users\\DELL-13\\Desktop\\教师专栏\\于海专栏"
    val file = File(PathUtil.buildPath(base, "第二期试听小节\\02.于海专栏讲什么(编辑底稿）.docx"))
    println(file.exists())

    processWordOfDir(base)
}

private  fun  processWordOfDir(dir:String) {
    FileUtil.getFilesFromDirPath(dir){
        t ->
        t.name.endsWith(".docx") || t.name.endsWith(".doc")
    }.forEach {
        val collector = StringBuilder()
        collector.append("\"\"\n")
        processWord(it, collector){
            t, line ->
            if (line.isNotEmpty()) {
                t.append("+ \"<p class='fz18'>$line</p>\"\n")
            }
        }

        val resultPath = "${it.parent}\\[RESULT]结果-${it.name.substring(0,it.name.lastIndexOf("."))}.txt"
//        val file = File(resultPath)
//        if (file.exists())
//            file.createNewFile()
        IOUtils.write(collector.toString(),FileOutputStream(resultPath))
    }
}

private  fun <T> processWord(file: File, collector: T, consumer: (t:T,line:String) -> Unit) {
    var text =""
    if (file.name.endsWith(".docx")){
        val doc = XWPFDocument(FileInputStream(file))
        val extractor = XWPFWordExtractor(doc)
        text = extractor.getText()
//    println(text)
    }
    else if (file.name.endsWith(".doc")){
        val doc = HWPFDocument(FileInputStream(file))
        val extractor = WordExtractor(doc)
        text = extractor.getText()
//    println(text)
    }
    text = text.replace("\r\n","\n")
    val split = text.split("\n")
    split.forEachIndexed{
        i,it->
//        if (file.name.endsWith(".doc")){
//            println("$i $it")
//        }

        consumer(collector,it)
    }


}