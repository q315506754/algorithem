package com.jiangli.doc.sql.helper.aries.order

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * #查不在会员企业的人员
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val outputpath = PathUtil.desktop("""不在组织架构.xls""")


    val questions = jdbc.query("""
UcSOut.kt
    """.trimIndent(), ColumnMapRowMapper())


//    questions.forEach {
//        val ID = it["ID"]
//        val mp = it
//
//        val courses = jdbc.query("""
//SELECT  DISTINCT (cse.COURSE_NAME) as 'NAME' FROM `db_aries_run`.`TBL_CLASS` cls
//LEFT JOIN `db_aries_course`.`TBL_COURSE` cse on cls.COURSE_ID = cse.COURSE_ID
//WHERE
//cls.IS_DELETE=0
//AND cls.COMPANY_ID=$ID;
//    """.trimIndent(), ColumnMapRowMapper())
//
//        var cCount = 1
//
//        courses.forEach {
//            mp.put("课程${cCount++}",it["NAME"])
//        }
//    }
//    println(questions)

    writeMapToExcel(outputpath, questions)
}