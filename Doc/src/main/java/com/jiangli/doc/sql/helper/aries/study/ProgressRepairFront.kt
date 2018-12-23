package com.jiangli.doc.sql.helper.aries.study

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 学习进度去重
 * 前端会变
 *
 * @author Jiangli
 * @date 2018/12/10 15:57
 */
fun main(args: Array<String>) {
    //    val env = Env.YANFA
    //    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val sql = """
#修WATCH_STATE对不上的
UPDATE  db_aries_study.`TBL_VIDEO_WATCH_INFO`
SET WATCH_PERCENT = 100
  ,  UPDATE_TIME='2018-12-12 12:12:13'
WHERE ID IN
      (SELECT * FROM (
           SELECT
             wi.ID
           FROM db_aries_study.`TBL_VIDEO_WATCH_INFO` wi
             LEFT JOIN db_aries_user.TBL_USER_COMPANY uc ON wi.USER_ID = uc.USER_ID
             LEFT JOIN  db_aries_company.TBL_COMPANY c ON uc.COMPANY_ID = c.ID
           WHERE
             WATCH_STATE = 1
             AND WATCH_PERCENT <> 100
             AND c.NAME LIKE '%幼儿园%'
         ) T2)
;
""".trimIndent()
    val query = jdbc.query(sql, ColumnMapRowMapper())
//    println(query)
    println(query.size)
}