package com.jiangli.doc.txt

import com.jiangli.common.utils.PathUtil
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Created by Jiangli on 2017/8/3.
 */

class Config{
    var spilit="："
}

 class TeacherDto{
     var name:String? = null
}

fun main(args: Array<String>) {
    val file = File(PathUtil.buildPath(PathUtil.getProjectPath(Config().javaClass),"src\\main\\java\\com\\jiangli\\doc\\txt\\14.txt"))
    println(file.absolutePath)
    println(file.exists())

    val bf = BufferedReader(InputStreamReader(FileInputStream(file)))

    var readLine = bf.readLine()
    while (readLine!=null) {
//        println(readLine)


        readLine = bf.readLine()
    }

    val arrayList = ArrayList<Map<String, Any>>()

}

