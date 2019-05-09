package com.jiangli.doc.word

import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
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
//    val base = "C:\\Users\\DELL-13\\Desktop\\aires cr 文档\\aa"
    val file = File(PathUtil.desktop("知到知识学习中心系统-代码.docx"))
//    println(file.exists())

    var text = ""
    val doc = XWPFDocument(FileInputStream(file))
    val extractor = XWPFWordExtractor(doc)
    text = extractor.getText()

    text = text.replace("\n{2,}".toRegex(),"\n")
    println(text)

    IOUtils.write(text, FileOutputStream(PathUtil.desktop("问答软件（Web）V1.0_代码_2.txt")))
}
