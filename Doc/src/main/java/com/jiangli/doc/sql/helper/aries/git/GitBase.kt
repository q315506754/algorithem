package com.jiangli.doc.sql.helper.aries.git

import com.jiangli.common.utils.PathUtil
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileInputStream

fun splitProject(projects: String): List<String> {
    var ret = mutableListOf<String>()
    projects.split("\n").forEach {
        val line = it.trim()
        val split = line.split("""[:：,，\s]""".toRegex())
        val project = split[0]

        if (!ret.contains(project)) {
            ret.add(project)
        }
    }
    return ret
}
fun splitProjectPair(projects: String): List<Pair<String,String>> {
    var ret = mutableListOf<Pair<String,String>>()
    var keys = mutableSetOf<String>()

    projects.split("\n").forEach {
        val line = it.trim()

        if (line.isNotBlank()) {
            val split = line.split("""[:：,，\s]""".toRegex())
            val project = split[0].trim()
            if (keys.contains(project)){
                throw Error("重复的project:${project}")
            }
            keys.add(project)

            ret.add(project to split[1].trim())
        }
    }
    return ret
}
fun executeBat(BAT_PUSH_FILE: String) {
    val exec2 = Runtime.getRuntime().exec(BAT_PUSH_FILE)
    val inputStream2 = exec2.inputStream
    IOUtils.copy(inputStream2, System.out)
}

fun getGitRemoteVersion(basePath: String, branch: String): String? {
    return getGitContent(basePath,"refs/remotes/origin/${branch}")
}
fun getGitLocalVersion(basePath: String, branch: String): String? {
    return getGitContent(basePath,"refs/heads/${branch}")
}
fun getGitContent(basePath: String, relapath:String): String? {
    var gitPath = PathUtil.buildPath(basePath, false, ".git")
    var remotePath = PathUtil.buildPath(gitPath, false, relapath)
    var file = File(remotePath)
    val fileInputStream = FileInputStream(file)
    val toString = IOUtils.toString(fileInputStream).trim()
    fileInputStream.close()
    return toString
}