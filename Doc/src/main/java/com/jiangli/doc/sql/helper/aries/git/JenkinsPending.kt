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
aries-app-server:e49bbca75637aac149bad5d36f16f9c790ce0bcc
aries-survey:df05b564d166c9d9000d44ba7d553c0e0a68aa12
    """.trimIndent()

    val splitProject = splitProjectPair(projects)

    var c = 0;
    splitProject.forEach {
        val project = it.first.trim()
        if (project.isBlank()) {
            return@forEach
        }
        var urlOfThis = url
        urlOfThis = urlOfThis.replace("{projectName}",it.first.trim())
        urlOfThis = urlOfThis.replace("{v}",it.second.trim())
        println(urlOfThis)

        FileUtil.execute("/c \"start $urlOfThis\"")
        c++

//        return
    }

    println("execute times:$c")
}




