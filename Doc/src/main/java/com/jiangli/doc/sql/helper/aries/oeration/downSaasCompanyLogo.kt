package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
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

    val inputpath = PathUtil.desktop("会员企业logo")
    println("$inputpath")

//    FileUtil.acceptDragFile()
    PathUtil.ensurePath(inputpath)
    FileUtil.deleteUnderDir(inputpath)

    FileUtil.openDirectory(inputpath)

    val list = jdbc.query("""
SELECT * FROM db_aries_company.TBL_COMPANY WHERE IS_SAAS>0 AND IS_DELETED=0 AND `TYPE` <> 2;
    """.trimIndent(), ColumnMapRowMapper())

    writeMapToExcel(File(inputpath, "会员企业.xlsx").absolutePath,list)

    return

    list.forEach {
        val COMPANY_ID = it["ID"]
        val NAME = it["NAME"]
        val COMPANY_LOGO = it["LOGO"].toString()


        if (COMPANY_LOGO.isNotBlank()) {
            val openConnection = URL(COMPANY_LOGO).openConnection()
            val inputStream = openConnection.getInputStream()
            val fileOutputStream = FileOutputStream(File(inputpath, "$NAME.png"))
            IOUtils.copy(inputStream,fileOutputStream)
        }

    }

}