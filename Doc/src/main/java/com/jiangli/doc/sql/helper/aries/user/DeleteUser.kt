package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 注销用户
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
//    val env = Env.YANFA
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val mobiles = """
15585705767
13751838804
    """.trimIndent()

    mobiles.split("\n").forEach {
        val mobile = it.trim()
//        println(mobile)

        val query = jdbc.queryForObject("""
    SELECT * FROM   db_aries_user.TBL_USER  WHERE MOBILE='$mobile' AND IS_DELETED = 0;
""".trimIndent(), ColumnMapRowMapper())

        val USER_ID = query["ID"].toString()

        val sql = """
UPDATE  db_aries_user.TBL_USER set IS_DELETED = 1 WHERE ID='$USER_ID' AND IS_DELETED = 0;
""".trimIndent()
        println(sql)
    }


//


}