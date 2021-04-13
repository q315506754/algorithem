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
aries-app-server:8a84e0b4712d325f53de088c252c1dffad7b2319

aries-live-server:32e49ea3ac1a203f1c1becdc6abc15147c845266

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




