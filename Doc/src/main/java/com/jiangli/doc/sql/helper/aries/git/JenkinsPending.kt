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
aries-forum-server：3f7f00576dccf8b6d11ff85f37fafd4d4862dce4
aries-app-server：747f20afc29b39d9127bda8367dd8ae77e06b6a0

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




