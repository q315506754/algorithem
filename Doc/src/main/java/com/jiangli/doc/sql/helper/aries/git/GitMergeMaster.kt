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
aries-app-server:
aries-live-server：
aries-classTools:
aries-server:
aries:
aries-h5:245b0de0a939d73ebd054521dd9742117a603d87
aries-erp-server:80cb07e6f29a8f0da30896fb02fa6e635006c9d6
aries-teachmanage:373f014278fd32c4d714d671ca5ec2a15de6fb9f
org-server:bd96572147b4a0ab24d4b983a7a0d3481b2d3ade
org-app-server:53b93e716a1e65ba00fbceaf2f391a630867fcb6
org-manage:e66dbbff2fb58cdda52dd5501ff7c52c832a61f9
aries-web-h5:
    """.trimIndent()

     val BAT_NAME = "_temp_merge_master.bat"
//     val BAT_NAME = "_temp_update.bat"

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

//        git commit -m "发版合并${DateUtil.getCurrentDate_YMDHms()}"${"\r\n"}

//        genefile
        generateFile("""
cd "$path"${"\r\n"}
git checkout master${"\r\n"}
git pull origin master${"\r\n"}
git pull origin release-branch${"\r\n"}
git push${"\r\n"}
        """,BAT_FILE)

//        do
//        println(BAT_FILE)
        println(path)

        var oldRemoteVer = getGitRemoteVersion(path,"master")
        var oldLocalVer = getGitLocalVersion(path,"master")

//       do merge
        val exec = Runtime.getRuntime().exec(BAT_FILE)
        val inputStream = exec.inputStream
        IOUtils.copy(inputStream,System.out)

        var newRemoteVersion = getGitRemoteVersion(path,"master")
        var newLocalVer = getGitLocalVersion(path,"master")

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


