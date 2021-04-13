package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 10:17
 */
fun main(args: Array<String>) {
//    val env = Env.WAIWANG
    val env = Env.YUFA
    val jdbc = Ariesutil.getJDBC(env)

    var userId = 100007071

//    管理员
    var mutableList = jdbc.query("""
        SELECT
       u.MOBILE,u.NAME,c.ID as COMPANY_ID,c.NAME as COMPANY_NAME,c.IS_SAAS,c.IS_EDU,usc.ID,usc.ROLE_TYPE
FROm db_aries_user.TBL_USER u
LEFT JOIN db_aries_user.TBL_USER_SAAS_COMPANY_ADMIN usc on u.ID = usc.USER_ID AND usc.IS_DELETED = 0
LEFT JOIN db_aries_company.TBL_COMPANY c on c.ID = usc.COMPANY_ID AND c.IS_DELETED = 0
WHERE
      u.ID = ${userId};
    """.trimIndent(), ColumnMapRowMapper())

    println("管理员 登录权限:")
    for (map in mutableList) {
        println("$map")
    }

//    管理员
     mutableList = jdbc.query("""
       SELECT u.MOBILE,u.NAME,c.ID as COMPANY_ID,c.NAME as COMPANY_NAME,c.IS_SAAS,c.IS_EDU
FROM db_aries_user.TBL_USER_COMPANY  uc
         LEFT JOIN  db_aries_company.TBL_COMPANY c on uc.COMPANY_ID = c.ID
         LEFT JOIN  db_aries_user.TBL_USER u on uc.USER_ID = u.ID
         LEFT JOIN  db_aries_user.TBL_USER_SAAS_COMPANY usc on usc.USER_COMPANY_ID = uc.ID
WHERE
        uc.IS_DELETED = 0
  AND c.IS_DELETED = 0
  AND c.IS_SAAS > 0
          AND usc.IS_DELETED = 0
          AND uc.USER_ID = ${userId}
        ORDER BY c.IS_SAAS DESC,FIELD(COMPANY_ID,47) DESC,uc.ID DESC;
    """.trimIndent(), ColumnMapRowMapper())

    println("企业成员 登录权限:")
    for (map in mutableList) {
        println("$map")
    }

}