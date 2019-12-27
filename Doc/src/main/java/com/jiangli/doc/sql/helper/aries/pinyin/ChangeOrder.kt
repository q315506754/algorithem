package com.jiangli.doc.sql.helper.aries.pinyin

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.PinyinUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2019/11/1 17:35
 */
fun main(args: Array<String>) {
    val env = Env.YANFA
//    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val query = jdbc.query("""
SELECT * FROM `db_aries_erp`.`CRM_PROVINCE`;
    """.trimIndent(), ColumnMapRowMapper())


    for (mutableMap in query) {
        val name = mutableMap["NAME"]!!.toString()
        val pinyin = PinyinUtil.getFirstLettersUp(name)
        mutableMap.put("py",pinyin)
//        println(mutableMap)
    }

    query.sortWith(Comparator { o1, o2 ->
        o2["py"].toString().compareTo(o1["py"].toString())
    })

    var c = System.currentTimeMillis()
    for (mutableMap in query) {
        var ID = mutableMap["ID"]

        c+=1000
        val date_YYYY_MM_DD = DateUtil.getDate_YYYY_MM_DD(c)

        println("UPDATE `db_aries_erp`.`CRM_PROVINCE` SET CREATE_TIME='$date_YYYY_MM_DD' WHERE ID = $ID;")
//        println(mutableMap)
    }
}