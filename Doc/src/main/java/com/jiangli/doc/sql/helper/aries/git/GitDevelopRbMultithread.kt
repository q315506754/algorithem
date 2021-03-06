package com.jiangli.doc.sql.helper.aries.git

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.mybatis.concatPath
import com.jiangli.doc.mybatis.generateFile
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 *
 * @author Jiangli
 * @date 2019/5/29 13:12
 */
fun main(args: Array<String>) {
//    var projects = """
//aries-server
//aries-app-server
//aries
//
//org-server
//org-app-server
//aries-forum-server
//org-manage
//aries-classtools

//    aries-pay-core
//    aries-pay-gateway
//    aries-pay-app-server
//    """.trimIndent()

    var projects = """

org-app-server：429bb8d97630247c3fa97b88160c025775611624
aries-server：853e9d935c9fbd69a04df0f574205943d606e2df

    """.trimIndent()

     val BAT_UPDATE_NAME = "_temp_update_rb.bat"
     val BAT_PUSH_NAME = "_temp_update_push_rb.bat"

    val results = mutableListOf<String>()
    val details = mutableListOf<String>()
    val localUpdated = mutableListOf<String>()
    val remotePushed = mutableListOf<String>()

    var baseDirPath = "C:\\projects"

    val splitProject = splitProject(projects)
    val threadPool = Executors.newFixedThreadPool(splitProject.size)
    val latch = CountDownLatch(splitProject.size)

    splitProject.forEach {
        val project = it.trim()
        if (it.isBlank()) {
            latch.countDown()
            return@forEach
        }

        var path = PathUtil.buildPath(baseDirPath, false, project)
        var BAT_UPDATE_FILE = concatPath(path, BAT_UPDATE_NAME)
        var BAT_PUSH_FILE = concatPath(path, BAT_PUSH_NAME)

        threadPool.execute {
            try {
                val project_dir = File(path)
                if (project_dir.exists()) {
                    println("$project ok")
                }else {
                    println("$project no！！")

                    results.add("$project:no such project")
                    return@execute
                }

                //        更新脚本
                generateFile("""
    cd "$path"${"\r\n"}
    git checkout release-branch${"\r\n"}
    git pull origin release-branch${"\r\n"}
            """,BAT_UPDATE_FILE)

                //        推送脚本
                generateFile("""
    cd "$path"${"\r\n"}
    git push${"\r\n"}
            """,BAT_PUSH_FILE)

                //        do
                //        println(BAT_FILE)
//                println(path)

                var oldLocalVer = getGitLocalVersion(path,"release-branch")
                //       do 更新脚本
                executeBat(BAT_UPDATE_FILE)

                var oldRemoteVer = getGitRemoteVersion(path,"release-branch")
                //       do 推送脚本
                executeBat(BAT_PUSH_FILE)

                var newRemoteVersion = getGitRemoteVersion(path,"release-branch")
                var newLocalVer = getGitLocalVersion(path,"release-branch")

                val localChanged = !oldLocalVer.equals(newLocalVer)
                val remoteChanged = !oldRemoteVer.equals(newRemoteVersion)

                println("""
   $project local version ${oldLocalVer} -> ${newLocalVer}
   $project local result ${if (localChanged) "changed!!!" else "up to date"}
   $project remote version ${oldRemoteVer} -> ${newRemoteVersion}
   $project remote result ${if (remoteChanged) "changed!!!" else "up to date"}
            """.trimIndent())

                results.add("$project:${newRemoteVersion}")

                if (localChanged) {
                    localUpdated.add("$project:${newLocalVer}")
                }

                if (remoteChanged) {
                    remotePushed.add("$project:${newRemoteVersion}")
                }

                details.add("$project: ${if (localChanged) "local=changed" else ""} ${if (remoteChanged) "remote=changed" else ""}")


                //        删除掉临时文件
                File(BAT_UPDATE_FILE).delete()
                File(BAT_PUSH_FILE).delete()
                println("-----------------------------------------")

            } finally {
                latch.countDown()
            }
        }
    }

    latch.await()

    println("=======---------details----------------------")
    details.forEach {
        println(it)
    }

    println("=======---------results----------------------")
    results.forEach {
        println(it)
    }

    println("=======---------localUpdated----------------------")
    localUpdated.forEach {
        println(it)
    }

    println("=======---------remotePushed----------------------")
    remotePushed.forEach {
        println(it)
    }


    threadPool.shutdown()
}




