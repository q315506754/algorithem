package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 海外用户手机、区号更改
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
//    val env = Env.YANFA
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    某个企业下
    val companyId = 110

    var areaCodes = setOf(
            "+65"
    )

    val sql = """
SELECT u.ID,u.AREA_CODE,u.MOBILE FROM  db_aries_study.`tbl_user_course`  uc
  LEFT JOIN db_aries_user.tbl_user u on uc.USER_ID = u.ID
WHERE
1 = 1
AND uc.COMPANY_ID=$companyId
AND uc.IS_DELETE=0
AND MOBILE like '+%'
;
""".trimIndent()

    val query = jdbc.query(sql, ColumnMapRowMapper())
    println(query)

    query.forEach {
        val userId = it["ID"].toString()
        val AREA_CODE = it["AREA_CODE"].toString()
        val MOBILE = it["MOBILE"].toString()

        val first = areaCodes.first { MOBILE.startsWith(it) }
        val real_mobile = MOBILE.substring(first.length)

        val updateSql = """
update db_aries_user.TBL_USER set AREA_CODE = '$first',MOBILE='$real_mobile' WHERE ID = $userId;
""".trimIndent()

        println(updateSql)
    }
}