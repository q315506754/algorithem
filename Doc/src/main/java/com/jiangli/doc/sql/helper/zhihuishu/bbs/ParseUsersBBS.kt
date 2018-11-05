package com.jiangli.doc.sql.helper.zhihuishu.bbs

import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.FileOutputStream

/**
 * 查参与数
 *
 * @author Jiangli
 * @date 2018/11/1 11:19
 */
fun main(args: Array<String>) {
    val qajdbc = Zhsutil.getJDBC(Env.WAIWANG_BBS, "ZHS_BBS")
    val onlineshooljdbc = Zhsutil.getJDBC(Env.WAIWANG_ONLINESCHOOL, "db_G2S_OnlineSchool")

    val ouputFile = """C:\Users\Jiangli\Desktop\000000处理助教数据.xlsx"""

    ExcelUtil.processRow(ouputFile,0,1){
        file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx>=1) {
            val userId = ExcelUtil.getCellValueByTitle(sheet,row, "用户id")
//            val email = ExcelUtil.getCellValueByTitle(sheet,row, "专员")
//            println(userId+" "+email)

//            上周
            val gtCreatetime = "2018-10-29"
            val qCount = qajdbc.queryForObject("""
select count(*) as COUNT from ZHS_BBS.QA_QUESTION where CREATE_USER=$userId AND IS_DELETED=0 AND CREATE_TIME >= '$gtCreatetime';
            """.trimIndent(), ColumnMapRowMapper())["COUNT"].toString().toInt()
            val aCount = qajdbc.queryForObject("""
select count(*) as COUNT  from  ZHS_BBS.QA_ANSWER where  A_USER_ID=$userId AND IS_DELETED=0 AND CREATE_TIME >= '$gtCreatetime';
            """.trimIndent(), ColumnMapRowMapper())["COUNT"].toString().toInt()
            val cCount = qajdbc.queryForObject("""
select count(*) as COUNT from ZHS_BBS.QA_COMMENT where  COMMENT_USER_ID=$userId AND IS_DELETED=0 AND CREATE_TIME >= '$gtCreatetime';
            """.trimIndent(), ColumnMapRowMapper())["COUNT"].toString().toInt()

            ExcelUtil.setCellValueByTitle(sheet,row, "问答参与数",qCount+aCount+cCount)

        }

        if (rowIdx == lastRowIdx) {
            workbook.write(FileOutputStream(ouputFile))
        }

    }
}



private fun cloneMap(dataOneProto: MutableMap<String, Any>): MutableMap<String, Any> {
    val dataOne = mutableMapOf<String, Any>()
    dataOne.putAll(dataOneProto)
    return dataOne
}