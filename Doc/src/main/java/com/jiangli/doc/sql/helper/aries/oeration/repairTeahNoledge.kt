package com.jiangli.doc.sql.helper.aries.oeration

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * 标签同步器
 *
 * @author Jiangli
 * @date 2019/11/11 15:03
 */
fun main(args: Array<String>) {
//        val env = Env.YANFA
//        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val list = jdbc.query("""
SELECT * FROM db_aries_operation.tbl_knowledge_coach_student
            """.trimIndent(), ColumnMapRowMapper())

    list.forEach {
//        println("$it")

        val img = it["IMG"].toString()

        if (img.endsWith("_s1.jpg")) {
            val img2 = img.replace("_s1.jpg",".jpg")
            println("UPDATE db_aries_operation.tbl_knowledge_coach_student SET IMG = '$img2' WHERE ID=${it["ID"]};")
        }


    }

}