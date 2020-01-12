package com.jiangli.http.helper

import com.jiangli.common.utils.FileUtil

/**
 *
 *
 * @author Jiangli
 * @date 2020/1/9 21:56
 */
fun main(args: Array<String>) {
    val outdir = "E:\\videos\\list5"

//
    val str = """

    """.trimIndent()

    val split = str.split("\n")
    split.forEachIndexed { index, it ->
        var line = it.trim()
        if (line.isBlank()) {
            return@forEachIndexed
        }
        println("start download ${index+1} / ${split.size}")

        FileUtil.downloadM3U8(line, outdir,true)
//        FileUtil.downloadM3U8(line, outdir)
    }
}