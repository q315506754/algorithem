package com.jiangli.doc.sql.helper.aries.common

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
160	458
182	254
328	225
86	239
485	478
238	229
163	221
100	430
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

        val c = jdbc.queryForObject("""
SELECT COUNT(*) as c FROm db_aries_user.TBL_USER_COMPANY WHERE  IS_DELETED=0 AND COMPANY_ID = $FROM;
    """.trimIndent(), ColumnMapRowMapper())["c"]

//        println("$line:")
        println("$c")
    }

}