package com.jiangli.doc.sql.helper.aries.git

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.mybatis.concatPath
import com.jiangli.doc.mybatis.generateFile
import java.io.File
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 *
 *
 * @author Jiangli
 * @date 2019/5/29 13:12
 */
fun main(args: Array<String>) {
    var projects = """
org-app-server:b1d33b2fe55801ec2b84bf05f531ac7d820a660c
aries-app-server:ddc0dcaa43754dbc35e9cb49a5d4bec63c36e071
aries-server:e1b3c25450177588aff671e2eb7c6b06c9fb56bf
org-server:1ed60021511aacb2b2c957c47a604796f3a52db0
aries-classtools：f5d849cc9989ae8f45c7a7046b4368860722e631
aries:e64bef765ff6aefb9b96f5cb536b424629ccc07b
aries-erp-server:812244e5637930e2d2573a633549fc77d1a7a3c5

    """.trimIndent()

     val BAT_UPDATE_NAME = "_temp_update_rb.bat"

    val results = mutableListOf<String>()
    val details = mutableListOf<String>()
    val localUpdated = mutableListOf<String>()
    val remotePushed = mutableListOf<String>()

    var baseDirPath = "C:\\projects"

    val splitProject = splitProject(projects)
    val threadPool = Executors.newFixedThreadPool(splitProject.size)
    val latch = CountDownLatch(splitProject.size)

    splitProject.forEach {
        val project = it
        var path = PathUtil.buildPath(baseDirPath, false, project)
        var BAT_UPDATE_FILE = concatPath(path, BAT_UPDATE_NAME)

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

                //        do
                //        println(BAT_FILE)
//                println(path)

                var oldLocalVer = getGitLocalVersion(path,"release-branch")
                var oldRemoteVer = getGitRemoteVersion(path,"release-branch")
                //       do 更新脚本
                executeBat(BAT_UPDATE_FILE)

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

    println("=======---------remoteUpdated----------------------")
    remotePushed.forEach {
        println(it)
    }


    threadPool.shutdown()
}




