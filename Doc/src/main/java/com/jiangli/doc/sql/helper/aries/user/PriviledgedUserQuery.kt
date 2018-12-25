package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 查账号管理权限的账号
 * @author Jiangli
 * @date 2018/8/27 10:58
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)


    val query = jdbc.query("""
SELECT u.ID,u.NAME,CONCAT('',um.IS_DELETE) as '账号管理已删除'from db_aries_manage.TBL_USER_MENU um LEFT JOIN
  db_aries_user.TBL_USER u ON um.USER_ID=u.ID
WHERE MENU_ID=4000;
    """.trimIndent(), ColumnMapRowMapper())

    query.forEach {
        println(it)
    }
}