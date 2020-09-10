package com.jiangli.doc.json


/**
 *
 *
 * @author Jiangli
 * @date 2020/8/26 17:10
 */
fun main(args: Array<String>) {
    var res = """
    token:KpYzMjXWWADxb
cid:8MyPW69eN7JGw
pageSize:100
pageNum:0
    """.trimIndent()

    val split = res.split("\n".toRegex())
    split.forEach {
        val line = it.trim()

        if (line.isBlank()) {
            return@forEach
        }

        val arr = line.split(":")
        var key = arr[0]
        var v = arr[1]
        var tp = getValType(v)
        var remark = ""

        println("${key} ${tp} ${geRemark(key,remark)}")
    }

}