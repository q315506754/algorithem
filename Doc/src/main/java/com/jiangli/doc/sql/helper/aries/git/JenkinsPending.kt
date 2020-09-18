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
aries-app-server：5e209f66471ed77f4d8ec2b980073b5cbe3aefb1
aries：64610b4a102ede5be12d5864e4da3c0be601a00f
aries-server：1c0befd5bdc8f61d41b19ff7cebe95be92650672

aries-base-video:cb668ebcfdeea29a7df035219438a83b1c12b36e
aries-base-message:ecc2c8a872358d8a11504959e9212adcb7b074e1
org-app-server:8a3d9a26d1aaacbe23359d2ce4431e0b3a2d3e46

aries-live-server:ad35c6b9acf3fa223c755645a07338551e240355
aries-live-api-server:ef4dd13a9134d34e7686ba8085c50b68df5b1dd3
aries-survey:5a40a29a8c74b285bcb38563d4f04a2be65dbf1a


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




