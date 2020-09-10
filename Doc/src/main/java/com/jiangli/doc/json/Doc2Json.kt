package com.jiangli.doc.json

import net.sf.json.JSONObject


/**
 *
 *
 * @author Jiangli
 * @date 2020/8/26 17:10
 */
fun main(args: Array<String>) {
    var res = """
appId:ar2P1qJlWoPj9
userId:abcde
pkg:com.aa.bb
version:1.0.0
    """.trimIndent()

    val split = res.split("\n".toRegex())

    val gson = JSONObject()
    split.forEach {
        val line = it.trim()

        if (line.isBlank()) {
            return@forEach
        }

        val arr = line.split(":")
        var key = arr[0]
        var v = arr[1]
        gson.put(key,v)
    }
    println("${gson}")

}