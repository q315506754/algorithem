package com.jiangli.doc.sql.helper.aries

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 注销用户
 * redis
 */


fun main(args: Array<String>) {
//    val env = Env.YANFA
    val env = Env.YUFA
//    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    val DOMAIN = "api.g2s.cn"
//    val REDIS_DOMAIN = "api-pay.g2s.cn/aries-pay-app-server"
    val REDIS_DOMAIN = "yf-api-pay.g2s.cn/aries-pay-app-server"
//        val REDIS_DOMAIN = "127.0.0.1:84/aries-pay-app-server"


    val inputpath = """code_map\code_map.xlsx"""

    println("#redis")
    var cmd:String
    var rs:String

//    清空用户企业列表
//    val USER_ID = "1230302"
//    cmd = "del aries:ser:USER_SAAS_CHOOSE_COMPANY_LIST:d:$USER_ID"
//    rs = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",cmd))
//    println("#$cmd:${rs}")

    val fudaoclasslist = jdbc.query("""
SELECT
    c.*
FROm db_aries_company.TBL_COMPANY c
WHERE c.ID > 0  LIMIT  100;
            """.trimIndent(), ColumnMapRowMapper())

    ExcelUtil.processRow(PathUtil.desktop(inputpath), 0, 1) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 1) {
            val id = ExcelUtil.getCellValueByTitle(sheet, row, "二维码编号")!!.toInt()
            val courseName = ExcelUtil.getCellValueByTitle(sheet, row, "课程名称")
            val chapterName = ExcelUtil.getCellValueByTitle(sheet, row, "章节名称")

            var COURSE_ID: String? = courseName
            var CHAPTER_ID: String? = chapterName

            println("$COURSE_ID $CHAPTER_ID")
        }
    }

//    清空企业缓存
    fudaoclasslist.forEach {
        val COMPANY_ID = it["ID"]

//        val COMPANY_ID = "330"
        cmd = "del aries:ser:COMPANY:d:$COMPANY_ID"
        rs = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",cmd))
        println("#$cmd:${rs}")
        cmd = "del aries:ser:TBL_COMPANY:d:$COMPANY_ID"
        rs = HttpPostUtil.postUrl("http://$REDIS_DOMAIN/admin/execute",  HttpPostUtil.build("cmd",cmd))
        println("#$cmd:${rs}")
    }


}