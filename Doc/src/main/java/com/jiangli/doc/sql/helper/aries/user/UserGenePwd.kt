package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.EncryptHelper
import com.jiangli.doc.sql.helper.aries.Env
import java.io.FileOutputStream

/**
 *
 *  批量修改初始密码并写回excel
 *
 * @author Jiangli
 * @date 2019/4/4 10:07
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val inOutputFile = PathUtil.desktop("陈春花教授网课学习账号分配-裕同账户.xlsx")

    val queryUserInDb = true
//    val queryUserInDb = false

    ExcelUtil.processRow(inOutputFile) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
            val email = ExcelUtil.getCellValueByTitle(sheet,row, "账号")
            val pwd = ExcelUtil.getCellValueByTitle(sheet,row, "备注")
//            println(email + )

            if (queryUserInDb) {
                val user = Ariesutil.getUser(jdbc, "", "", email)
                ExcelUtil.setCellValueByTitle(sheet,row, "dbpwd",user!![0]["PASSWORD"].toString())
            }

            val encryptPassword = EncryptHelper.encryptPassword(pwd ?: "123456")

            ExcelUtil.setCellValueByTitle(sheet,row, "密文",encryptPassword)
            ExcelUtil.setCellValueByTitle(sheet,row, "sql","UPDATE db_aries_user.TBL_USER set PASSWORD ='$encryptPassword' where EMAIL='$email' AND IS_DELETED = 0;")
        }

        if (rowIdx == lastRowIdx) {
            workbook.write(FileOutputStream(inOutputFile))
        }
    }

}