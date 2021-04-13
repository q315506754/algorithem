package com.jiangli.doc.json

/**
 *
 *
 * @author Jiangli
 * @date 2020/9/27 11:31
 */
fun main(args: Array<String>) {
//    var rootPath = "/usr/local"
    var rootPath = "/opt"

    println("""
vim /root/shell/log-clear.sh

    """.trimIndent())

    """
        
        """.split("\n").forEach {
        var itX = it.trim()
        if (itX.isBlank()) {
            return@forEach
        }

        val split = itX.split(" ")
        var project = split[0]
        var t = "s"
        if (split.size > 1) {
            t = split[1]
        }

        if (t == "s") {
            println("""
echo '清理 $project 日志'
echo '' > $rootPath/logs/$project.log
echo '' > $rootPath/logs/$project-1.0.0.log
        """.trimIndent())
        }else if (t == "w") {
            println("""
echo '清理 $project 日志'
echo '' > $rootPath/$project/logs/catalina.out
        """.trimIndent())
        }

    }
}