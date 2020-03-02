package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
    //    val env = Env.YANFA
    //    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val inputpath = PathUtil.desktop("""用户企业.xlsx""")

    val list = mutableListOf<String>()

    ExcelUtil.processRow(inputpath, 0, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
            val userMobile = ExcelUtil.getCellValueByTitle(sheet, row, "用户手机")
            val companyName = ExcelUtil.getCellValueByTitle(sheet, row, "展示企业")

            val sql = """
SELECT * FROM db_aries_user.TBL_USER WHERE MOBILE='${userMobile}'
AND IS_DELETED=0
;
            """.trimIndent()

            //query
            val qIdList = jdbc.query(sql, ColumnMapRowMapper())
            if (qIdList.isEmpty()) {

            }else {
                println("${qIdList[0]}")
            }
        }

    }


}

