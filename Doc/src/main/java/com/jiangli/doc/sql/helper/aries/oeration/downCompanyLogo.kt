package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.apache.commons.io.IOUtils
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.File
import java.io.FileOutputStream
import java.net.URL

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

    val inputpath = PathUtil.desktop("企业logo")
    println("$inputpath")

//    FileUtil.acceptDragFile()
    PathUtil.ensurePath(inputpath)
    FileUtil.deleteUnderDir(inputpath)

    FileUtil.openDirectory(inputpath)

    val list = jdbc.query("""
SELECT * FROM db_aries_run.TBL_COMPANY_SHARE_STU WHERE IS_SHOW=1 AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper())

    list.forEach {
        val COMPANY_ID = it["COMPANY_ID"]
        val COMPANY_LOGO = it["COMPANY_LOGO"].toString()


        val openConnection = URL(COMPANY_LOGO).openConnection()
        val inputStream = openConnection.getInputStream()
        val fileOutputStream = FileOutputStream(File(inputpath, "$COMPANY_ID.png"))
        IOUtils.copy(inputStream,fileOutputStream)

    }
}