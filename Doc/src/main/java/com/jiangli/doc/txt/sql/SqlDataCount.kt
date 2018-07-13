package com.jiangli.doc.txt.sql

import com.jiangli.doc.txt.DB
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/7/2 11:02
 */
fun main(args: Array<String>) {
    val qaDB = DB.getWendaJDBCForWaiWang()
//    println(qaDB)

    val DB_NAME="ZHS_BBS"
    val list = arrayListOf(
            "QA_QUESTION"
            , "QA_ANSWER"
            , "QA_COMMENT"

            , "QA_OPERATION"

            , "QA_HOTQUESTION_PUSH"
            , "QA_PUSHCONTROL"

            , "QA_REPORT_RECORD"
            , "QA_REPORT_RULES"
            , "QA_REPORT_TRANSACTION"

            , "QA_QUESTION_WATCH_NUMBER_RECORD"
            , "QA_QUESTION_WATCH_NUMBER_RECORD"

            , "QA_USER_BAN"

            , "QA_AUDIT_RECORDS"
            , "QA_PARAMETER_CONFIGURATION"
    )


    var sum:Long = 0

    for (tbl in list) {
        val abs_tbl = "$DB_NAME.$tbl"
        val sql = "SELECT COUNT(1) FROM $abs_tbl ;"
//        println(sql)
        val query = qaDB.query(sql, ColumnMapRowMapper())
        val rows = query[0].values.iterator().next() as Long
//        println(query)
//        println(rows)
        sum+=rows
        println("$abs_tbl $rows")
    }

    println("total: $sum")

}