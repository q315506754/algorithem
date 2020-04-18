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
 aries-server：91d83c22ff828fdfd5331664c64decc7847fc112
     aries-app-server：ea236ad57ada8425f741f9db3ba84ea781ac5126
	 org-app-server：525fe9c6e51e7fa20004c1e2aa28a1040db49e89
aries-forum-server:4d5b3657649f093a5a5e83b7aa10dd6462d63cc0
aries-base-video:3f5b8b1e560825767322aaa54623200951f5439c
aries-base-message:1380f2a80fdb5a336fdeed26a4b6c3c23f5dc750
aries:4681a38f2f5c806d23ae5c06f1a82db8431cd7fb
     aries-classTools：d068901320d94bd1f52f0d1b98ef7a862f01d183
aries-live-server：3caba6075f11c0a8ac96752553e6036c9596e28a
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




