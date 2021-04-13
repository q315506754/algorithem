package com.jiangli.doc.sql.helper.aries.study

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * 教学企业数量 分布数据
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val outputpath = PathUtil.desktop("""小平数据.xlsx""")


    val questions = jdbc.query("""
SELECT c.LIVE_NAME,b.`NAME`,b.MOBILE,ROUND(a.TOTAL_WATCH_TIME/60,2) from 
db_aries_study.tbl_video_watch_info a,
db_aries_user.tbl_user b,
db_aries_live.live_task c
WHERE
a.COURSE_ID IN (SELECT s.LIVE_ID from db_aries_operation.tbl_column_content s WHERE s.COLUMN_ID = 2 AND s.IS_DELETE = 0)
and 
a.COURSE_ID = c.ID
and 
a.USER_ID = b.ID
and 
a.USER_ID in (SELECT g.USER_ID from db_aries_user.tbl_user_company g where g.COMPANY_ID = 218 AND g.IS_DELETED = 0);
    """.trimIndent(), ColumnMapRowMapper())

    println(questions)

    writeMapToExcel(outputpath, questions)
}