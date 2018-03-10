package com.jiangli.doc.txt.wenda

import com.jiangli.doc.txt.DB
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/3/9 16:00
 */
fun main(args: Array<String>) {
    var qId = "20253"
    val db = DB.getQAJDBCForWaiwang()
    val question = db.queryForObject("select * from ZHS_BBS.QA_QUESTION where   QUESTION_ID = $qId", ColumnMapRowMapper())
    println("问题数据:$question")

//    删回答
//    删回答下的评论
//    删围观


}