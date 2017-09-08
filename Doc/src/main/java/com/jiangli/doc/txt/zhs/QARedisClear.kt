package com.jiangli.doc.txt

import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 14:39
 */


fun main(args: Array<String>) {

    val pool = Redis.getYufaPool()
    val jdbc = DB.getJDBCForYuFa()


    var  COURSE_IDS = jdbc.query("SELECT * from ZHS_BBS.QA_ANSWER;", ColumnMapRowMapper())


    pool.execute{
        val jedis = it


        COURSE_IDS.forEach {
            val ANSWER_ID = it["ID"]
            val A_USER_ID = it["A_USER_ID"]
//
            jedis.executeDel("qa:answer:answerInfo:${ANSWER_ID}")
            jedis.executeDel("qa:comment:answerIncomment:${ANSWER_ID}")

            jedis.executeDel("qa:answer:myAnser:${A_USER_ID}")
//            jedis.executeDel("cc:course:${COURSE_ID}:lessons")
        }


    }




}