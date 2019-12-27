package com.jiangli.doc.sql.helper.aries.git

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.mybatis.concatPath
import com.jiangli.doc.mybatis.generateFile
import org.apache.commons.io.IOUtils
import java.io.File

/**
 *
 *
 * @author Jiangli
 * @date 2019/5/29 13:12
 */
fun main(args: Array<String>) {
    var projects = """
aries
aries-app-server
aries-classTools
aries-server
    """.trimIndent()

     val BAT_NAME = "_temp_update_rb.bat"

    val results = mutableListOf<String>()
    val details = mutableListOf<String>()

    var baseDirPath = "C:\\projects"
    splitProject(projects).forEach {
        val project = it
        var path = PathUtil.buildPath(baseDirPath, false, project)
        var BAT_FILE = concatPath(path, BAT_NAME)


        val project_dir = File(path)
        if (project_dir.exists()) {
            println("$project ok")
        }else {
            println("$project no！！")

            results.add("$project:no such project")
            return@forEach
        }

//        genefile
        generateFile("""
cd "$path"${"\r\n"}
git checkout release-branch${"\r\n"}
git pull origin release-branch${"\r\n"}
        """,BAT_FILE)

//        do
//        println(BAT_FILE)
        println(path)

        var oldRemoteVer = getGitRemoteVersion(path,"release-branch")
        var oldLocalVer = getGitLocalVersion(path,"release-branch")

//       do merge
        val exec = Runtime.getRuntime().exec(BAT_FILE)
        val inputStream = exec.inputStream
        IOUtils.copy(inputStream,System.out)

        var newRemoteVersion = getGitRemoteVersion(path,"release-branch")
        var newLocalVer = getGitLocalVersion(path,"release-branch")

        val localChanged = !oldLocalVer.equals(newLocalVer)
        val remoteChanged = !oldRemoteVer.equals(newRemoteVersion)

        println("""
local version ${oldLocalVer} -> ${newLocalVer}
local result ${if (localChanged) "changed!!!" else "up to date"}
remote version ${oldRemoteVer} -> ${newRemoteVersion}
remote result ${if (remoteChanged) "changed!!!" else "up to date"}
        """.trimIndent())

        results.add("$project:${newRemoteVersion}")
        details.add("$project: ${if (localChanged) "local=changed" else ""} ${if (remoteChanged) "remote=changed" else ""}")


//        删除掉临时文件
        File(BAT_FILE).delete()

        println("-----------------------------------------")

//        test
//        return@forEach
//        return
    }

    println("=======---------details----------------------")
    details.forEach {
        println(it)
    }

    println("=======---------results----------------------")
    results.forEach {
        println(it)
    }

}


