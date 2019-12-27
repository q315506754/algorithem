package com.jiangli.doc

import com.jiangli.common.utils.PathUtil
import org.aspectj.util.FileUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 *
 * 修复 皮皮  excel
 * @author Jiangli
 * @date 2019/7/9 9:04
 */

fun main(args: Array<String>) {

    val src = File(PathUtil.desktop("06原始表.XLS"))

    val s = "gb2312"
//    val s = "iso-8859-1"
//    val s = "gbk"
//    val s = "utf8"

//    val t = "gb2312"
//    val t = "gbk"
    val t = "utf8"
    val br = BufferedReader(InputStreamReader(FileInputStream(src), s))
    var line: String? = br.readLine()

    val out = StringBuilder()

    while (line != null) {
//        println(line)
        var message = String(line.toByteArray(Charset.forName(t)))

        if (message.contains("gb2312")) {
            message = message.replace("gb2312", "utf8")
        }

        if (message.contains("姓名")) {
            message = message.substring(0,message.lastIndexOf("部门"))
            message+="</Data></Cell>"

            println(message)
        }

//        println(message)
        out.append(message)
        out.append("\r\n")

        line= br.readLine()
    }

    br.close()


    FileUtil.writeAsString(File(PathUtil.desktop("06原始表_rs.XLS")),out.toString())

}