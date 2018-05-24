package com.jiangli.doc.word

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
import org.apache.poi.hwpf.HWPFDocument
import org.apache.poi.hwpf.extractor.WordExtractor
import org.apache.poi.xwpf.extractor.XWPFWordExtractor
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*





/**
 *
 *
 * @author Jiangli
 * @date 2017/12/7 19:33
 */

fun main(args: Array<String>) {
//    val base = "C:\\Users\\DELL-13\\Desktop\\aires cr 文档\\aa"
    val base = "C:\\Users\\Jiangli\\Desktop\\新建文件夹\\aa"
    val file = File(PathUtil.buildPath(base, "2.4.4-2.4.6（朱伊枫）.docx"))
    println(file.exists())

    processWordOfDir(base)
}

private  fun  processWordOfDir(dir:String) {
    FileUtil.getFilesFromDirPath(dir){
        t ->
        !t.name.startsWith("~") && (t.name.endsWith(".docx") || t.name.endsWith(".doc"))
    }.forEach {
        val collector = StringBuilder()
        collector.append("\"\"\n")
        processWord(it, collector) { t, line ->
            t.append("$line\n")
        }

        val resultPath = "${it.parent}\\[RESULT]结果-${it.name.substring(0,it.name.lastIndexOf("."))}.txt"
//        val file = File(resultPath)
//        if (file.exists())
//            file.createNewFile()
        IOUtils.write(collector.toString(), FileOutputStream(resultPath))
    }
}

private  fun <T> processWord(file: File, collector: T, consumer: (t:T, line:String) -> Unit) {
    var text =""

    if (file.name.endsWith(".docx")){
        println("${file.name}")
        val doc = XWPFDocument(FileInputStream(file))
        val extractor = XWPFWordExtractor(doc)
        text = extractor.getText()

        val map = HashMap<String, String>()
        doc.allPictures.forEach {
            val relationId = it.parent.getRelationId(it)

            println(relationId+""+it.getPictureType().toString() + File.separator + it.suggestFileExtension()
                    +File.separator+it.getFileName())


            val outFile = File(PathUtil.buildPath(file.parent, "pictures"), it.getFileName())
//            outFile.createNewFile()
            map.put(relationId,outFile.absolutePath)

            IOUtils.copy(ByteArrayInputStream(it.data), FileOutputStream(outFile))
        }

        doc.allPackagePictures.forEach {
            println("pkg.."+it.getPictureType().toString() + File.separator + it.suggestFileExtension()
                    +File.separator+it.getFileName())
        }


        var text = ""
        doc.paragraphs.forEach {
            paragraph->
            val runs = paragraph.runs
            for (run in runs) {
//                System.out.println("xmlText:"+run.getCTR().xmlText())
//                System.out.println("run:"+run)

                if (run.ctr.xmlText().indexOf("<w:drawing>") != -1) {
                    val runXmlText = run.ctr.xmlText()
                    val rIdIndex = runXmlText.indexOf("r:embed")
                    val rIdEndIndex = runXmlText.indexOf("/>", rIdIndex)
                    val rIdText = runXmlText.substring(rIdIndex, rIdEndIndex)
                    println(rIdText.split("\"".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1].substring("rId".length))
                    val id = rIdText.split("\"".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
                    System.out.println(map.get(id))
                    text = text + "<img src = '" + map.get(id) + "'/>"
                    System.out.println("img id=${id} src=${map.get(id)}")
                } else {
                    text = text + run

//                    System.out.println("xmlText:"+run.getCTR().xmlText())
                    if (run.ctr.xmlText().indexOf("o:gfxdata") == -1) {
                        System.out.println("xmlText:"+run.getCTR().xmlText())
                    }

                    System.out.println("run:"+run)
                }
            }

            println("-----paragraph-----")
        }
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