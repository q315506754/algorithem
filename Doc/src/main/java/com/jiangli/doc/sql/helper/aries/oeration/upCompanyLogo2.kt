package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.http.okhttp.uploadFileAndGetPath
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.File

/**
 * 批量上传企业logo
 *
 * @author Jiangli
 * @date 2019/11/18 19:46
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val inputpath = PathUtil.desktop("企业logo2")
    println("$inputpath")

//    FileUtil.acceptDragFile()

    var sb = StringBuilder()
    FileUtil.openDirectory(inputpath)

    val dir = File(inputpath)
    val listFiles = dir.listFiles()
    listFiles.forEach {
        val newLogo = uploadFileAndGetPath(it)
        val id = it.name.substring(0, it.name.lastIndexOf("."))

//        println("${it.name} $newLogo")
        val queryForObject = jdbc.queryForObject("SELECT * FROM db_aries_company.TBL_COMPANY WHERE ID = ${id} AND IS_DELETED=0;", ColumnMapRowMapper())

        val s1 = "#cur ${queryForObject["NAME"]}-img ${queryForObject["LOGO"]}"
        println(s1)
        sb.append(s1)
        sb.append("\r\n")
        val s2 = "UPDATE db_aries_company.TBL_COMPANY SET LOGO = '$newLogo' WHERE ID = ${id} AND  IS_DELETED=0;"
        println(s2)
        sb.append(s2)
        sb.append("\r\n")
        sb.append("\r\n")
    }

    FileUtil.writeStr(sb.toString(),PathUtil.desktop("企业logo_bak_${System.currentTimeMillis()}.txt"))

}