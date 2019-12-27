package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.http.okhttp.uploadFileAndGetPath
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.File

/**
 *
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

    FileUtil.openDirectory(inputpath)

    val dir = File(inputpath)
    val listFiles = dir.listFiles()
    listFiles.forEach {
        val newLogo = uploadFileAndGetPath(it)
        val id = it.name.substring(0, it.name.lastIndexOf("."))

//        println("${it.name} $newLogo")
        val queryForObject = jdbc.queryForObject("SELECT * FROM db_aries_run.TBL_COMPANY_SHARE_STU WHERE COMPANY_ID = ${id} AND IS_SHOW=1 AND IS_DELETED=0;", ColumnMapRowMapper())
        println("#cur ${queryForObject["COMPANY_NAME"]}-img ${queryForObject["COMPANY_LOGO"]}")
        println("UPDATE db_aries_run.TBL_COMPANY_SHARE_STU SET COMPANY_LOGO = '$newLogo' WHERE COMPANY_ID = ${id} AND IS_SHOW=1 AND IS_DELETED=0;")
    }

}