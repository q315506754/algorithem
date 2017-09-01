package com.jiangli.doc.txt

import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 14:39
 */


fun main(args: Array<String>) {
//    println(System.currentTimeMillis())
//    return
//    val pool = Redis.getYanfaPool()
//    val jdbc = DB.getJDBCForYanFa()

    val pool = Redis.getYufaPool()
    val jdbc = DB.getJDBCForYuFa()


    var  COURSE_IDS = jdbc.query("SELECT l.ID,c.COURSE_ID from db_G2S_OnlineSchool.CC_LESSON l,db_G2S_OnlineSchool.TBL_COURSE c WHERE l.IS_DETELED=0 AND l.COURSE_ID = c.COURSE_ID AND c.TYPE = 4", ColumnMapRowMapper())
    println(COURSE_IDS.size)

    pool.execute{
        val jedis = it

        COURSE_IDS.forEach {
            val LESSON_ID = it["ID"]
            val COURSE_ID = it["COURSE_ID"]

            jedis.executeDel("cc:lesson:${LESSON_ID}")
            jedis.executeDel("cc:course:${COURSE_ID}:lessons")
        }


    }




}