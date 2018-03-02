package com.jiangli.doc.desktop

import java.io.File

/**
 *
 *
 * @author Jiangli
 * @date 2018/3/2 20:36
 */


fun main(args: Array<String>) {
//    val OUTPUTPATH = "C:\\Users\\DELL-13\\Desktop\\outpath"
    val OUTPUTPATH = "C:\\Users\\Jiangli\\Desktop"

    File(OUTPUTPATH).listFiles().forEach {
        println(it.name)
    }

}