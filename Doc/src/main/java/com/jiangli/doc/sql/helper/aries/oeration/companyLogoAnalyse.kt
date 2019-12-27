package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.common.utils.PathUtil
import java.io.File

/**
 *
 * @author Jiangli
 * @date 2019/11/18 19:46
 */
fun main(args: Array<String>) {
    val names = getFileNames(PathUtil.desktop("企业logo"))
    val names2 = getFileNames(PathUtil.desktop("企业logo2"))

    println(names)
    println(names2)

    names2.filter { !names.contains(it) }.forEach {
        println(it)
    }
}

private fun getFileNames(inputpath: String?): MutableSet<String> {
    val dir1 = File(inputpath)

    val names = mutableSetOf<String>()
    val listFiles = dir1.listFiles()
    listFiles.forEach {
        names.add(it.name)
    }
    return names
}