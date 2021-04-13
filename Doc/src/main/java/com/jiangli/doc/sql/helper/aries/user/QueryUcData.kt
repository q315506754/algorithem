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
83	187
434	416
490	449
252	168
250	248
63	219
140	219
458	160	#1
182	254
225	328	#2
86	239
485	478
229	238	#3
163	221
430	100	#4
275	247
464	139
186	235
193	235
129	228
204	241
    """.trimIndent()

    str.split("\n").forEach {
        var line = it.trim()
        if (line.isBlank()) {
            return@forEach
        }

        val arr = line.split("\t")
        val FROM = arr[0].trim()
        val TO = arr[1].trim()

        val TO_VALIDATE = jdbc.query("""
            SELECT * FROm db_aries_company.TBL_COMPANY_CRM WHERE  COMPANY_ID = $TO AND IS_DELETED=0;
    """.trimIndent(), ColumnMapRowMapper())

//        被删企业人数
//        val c = jdbc.queryForObject("""
//SELECT COUNT(*) as c FROm db_aries_user.TBL_USER_COMPANY WHERE  IS_DELETED=0 AND COMPANY_ID = $FROM;
//    """.trimIndent(), ColumnMapRowMapper())["c"]

//        目标企业人数
//        val c = jdbc.queryForObject("""
//SELECT COUNT(*) as c FROm db_aries_user.TBL_USER_COMPANY WHERE  IS_DELETED=0 AND COMPANY_ID = $TO;
//    """.trimIndent(), ColumnMapRowMapper())["c"]

//        目标企业人数
        val c = jdbc.queryForObject("""
            SELECT * FROm db_aries_company.TBL_COMPANY WHERE ID = $TO;
    """.trimIndent(), ColumnMapRowMapper())["IS_EDU"]

//        查重合
//        val c = jdbc.queryForObject("""
//SELECT COUNT(*) as c FROm
//                          db_aries_user.TBL_USER_COMPANY uc1
//WHERE
//        uc1.IS_DELETED=0
//  AND uc1.COMPANY_ID =$FROM
//AND EXISTS(SELECT * FROM db_aries_user.TBL_USER_COMPANY uc2 WHERE uc2.USER_ID=uc1.USER_ID AND uc2.COMPANY_ID=$TO AND uc2.IS_DELETED=0);
//
//    """.trimIndent(), ColumnMapRowMapper())["c"]

//        查待导入人数
//        val c = jdbc.queryForObject("""
//SELECT COUNT(*) as c FROm
//                          db_aries_user.TBL_USER_COMPANY uc1
//WHERE
//        uc1.IS_DELETED=0
//  AND uc1.COMPANY_ID =$FROM
//AND NOT EXISTS(SELECT * FROM db_aries_user.TBL_USER_COMPANY uc2 WHERE uc2.USER_ID=uc1.USER_ID AND uc2.COMPANY_ID=$TO AND uc2.IS_DELETED=0);
//
//    """.trimIndent(), ColumnMapRowMapper())["c"]

//        println("$line: ${TO_VALIDATE.size}")
        println("$c")
    }

}