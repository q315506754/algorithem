package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
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

    var str = """
458 160
225 328	
229 238	
430 100	
    """.trimIndent()

    str.split("\n").forEach {
        var line = it.trim()
        if (line.isBlank()) {
            return@forEach
        }

        val arr = line.split(" ")
        val FROM = arr[0].trim()
        val TO = arr[1].trim()

        val TO_VALIDATE = jdbc.query("""
            SELECT * FROm db_aries_company.TBL_COMPANY_CRM WHERE  COMPANY_ID = $TO AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper())

        val c = jdbc.queryForObject("""
SELECT COUNT(*) as c FROm db_aries_user.TBL_USER_COMPANY WHERE  IS_DELETED=0 AND COMPANY_ID = $FROM;
    """.trimIndent(), ColumnMapRowMapper())["c"]

        println("$line: ${TO_VALIDATE.size}")
        println("$c")
    }

}