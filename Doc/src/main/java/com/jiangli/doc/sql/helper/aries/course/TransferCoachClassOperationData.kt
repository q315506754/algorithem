package com.jiangli.doc.sql.helper.aries.course

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 10:17
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)


    var fromId = 100003653 //w ge xin
//    var fromId = 100003731//w chen chun h

//    var toId = 100007071 // w xiaoping
    var toId = 100007071 // w wo

    val time = "2020-03-02 02:02:02"

    println("update `db_aries_run`.`COACH_CLASS` SET IS_DELETED=1,UPDATE_TIME='2020-06-18' WHERE USER_ID=$toId AND IS_DELETED=0;")

    val list = jdbc.query("""
SELECT * FROM `db_aries_run`.`COACH_CLASS` WHERE USER_ID = $fromId AND IS_DELETED=0;
            """.trimIndent(), ColumnMapRowMapper())


    list.forEach {
//        println(it)

        println("INSERT INTO `db_aries_run`.`COACH_CLASS`(`USER_ID`, `COURSE_ID`, `CLASS_ID`, `DELI_TRAN_ID`, `DELI_PLAN_ID`, `REMARK`, `IS_DELETED`, `CREATE_TIME`, `UPDATE_TIME`, `CREATE_PERSON`, `DELETE_PERSON`) VALUES ($toId, ${ it["COURSE_ID"]}, ${ it["CLASS_ID"]}, NULL, NULL, NULL, 0, '$time', '$time', -1, NULL);")

    }


}