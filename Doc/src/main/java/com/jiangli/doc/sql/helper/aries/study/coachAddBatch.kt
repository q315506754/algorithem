package com.jiangli.doc.sql.helper.aries.study

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

    val list = mutableListOf(
            Ariesutil.getUserId(jdbc,mobile = "18616356337")
    )

    list.forEach {
        println(it)
    }
    println("#########################")

//    val questions = jdbc.query("""
//SELECT * FROm db_aries_run.tbl_class WHERE COMPANY_ID = 348;
//    """.trimIndent(), ColumnMapRowMapper())
    val questions = jdbc.query("""
select * from `db_aries_run`.`tbl_class` where `COURSE_ID` = '600143' AND `IS_DELETE` = '0';
    """.trimIndent(), ColumnMapRowMapper())


    questions.forEach {
        val CLASS_ID = it["CLASS_ID"]
        val COURSE_ID = it["COURSE_ID"]
        val mp = it

        list.forEach {
            val USER_ID = it

            println("INSERT INTO `db_aries_run`.`COACH_CLASS`( `USER_ID`, `COURSE_ID`, `CLASS_ID`) VALUES ('$USER_ID', '$COURSE_ID',' $CLASS_ID');")
        }

    }

}