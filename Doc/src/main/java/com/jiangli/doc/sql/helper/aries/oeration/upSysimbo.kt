package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * 共生课堂
 *
 * @author Jiangli
 * @date 2019/11/16 16:29
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    var list: MutableList<MutableMap<String, Any>>?

    list = jdbc.query("""
    SELECT * FROM  db_aries_operation.SYM_SYMBIOTIC_CLASSROOM WHERE IS_DELETED = 0;
                """.trimIndent(), ColumnMapRowMapper())


    list.forEach {
        val wId = it["ID"]
        val videoId = it["TRAILER_VIDEO"]

        val vInfo = jdbc.queryForObject("""
SELECT * FROM  `db_aries_base`.`TBL_VIDEO` WHERE ID=$videoId;
            """.trimIndent(), ColumnMapRowMapper())
        val newLog = "http://image.g2s.cn/"+vInfo["THUMBNAIL"]
//        println(newLog)

        println("#cur data ${it["COVER"]}")
        println("UPDATE  db_aries_operation.SYM_SYMBIOTIC_CLASSROOM SET COVER='$newLog' WHERE ID =$wId;")
    }
}