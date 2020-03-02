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
aries-app-server:38509509dc04339440256151bc1f561f0acfbadf
aries-classtools:dc005a0df5551e6a93d6f1589f543100ada37f0e
org-manage:333030599d13cb2d51ae525118772f3d5b21330e
aries:cd7dfcd636d6366a419e51c184e16eb122442ca4
aries-server:31a52f6cc8c6b8f23fac198aad1ba69351a203b2
org-app-server:bec5fd8a89396f7b1ef8a16d4e7dc87d1bfa0c9f

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




