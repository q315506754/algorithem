package com.jiangli.doc.sql.helper.aries.git

import com.jiangli.common.utils.FileUtil

/**
 *
 * @author Jiangli
 * @date 2019/5/29 13:12
 */
fun main(args: Array<String>) {
    var url = """
http://192.168.9.138:8080/jenkins/view/zs-%E7%9F%A5%E5%AE%A4/job/{projectName}/build?v={v}&delay=0sec
    """.trimIndent()

    var projects = """
aries-live-server:dd963e41553fab4a5791c5d1ce92bac5c3048973
aries-server:4884ef18f55536a45ef829a66987f5ad2b32cb7b
    """.trimIndent()

    val splitProject = splitProjectPair(projects)

    var c = 0;
    splitProject.forEach {
        val project = it.first.trim()
        if (project.isBlank()) {
            return@forEach
        }
        var urlOfThis = url
        urlOfThis = urlOfThis.replace("{projectName}",it.first)
        urlOfThis = urlOfThis.replace("{v}",it.second)
        println(urlOfThis)

        FileUtil.execute("/c \"start $urlOfThis\"")
        c++

//        return
    }

    println("execute times:$c")
}




