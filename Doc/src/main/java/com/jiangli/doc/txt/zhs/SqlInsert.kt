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
    val pool = Redis.getYanfaPool()
    val jdbc = DB.getJDBCForYanFa()

//    val pool = Redis.getYufaPool()
//    val jdbc = DB.getJDBCForYuFa()


    //     ALTER table ZHS_BBS.QA_QUESTION  modify  CONTENT  varchar(1200) character set utf8mb4 collate utf8mb4_unicode_ci;
    //     ALTER table ZHS_BBS.QA_QUESTION  modify  CONTENT  varchar(1200);
   jdbc.execute("insert into ZHS_BBS.QA_QUESTION(CONTENT) values('\u8c22问题啊啊啊 \ude02\ud83d 额嗯嗯')")
//   jdbc.execute("insert into ZHS_BBS.QA_QUESTION(CONTENT) values('问题啊啊啊\\\\ude02\\\\ud83d 额嗯嗯')")
//   jdbc.execute("insert into ZHS_BBS.QA_QUESTION(CONTENT) values('问题啊啊啊\xF6\x9D\x98\x84 额嗯嗯')")
//   jdbc.execute("insert into ZHS_BBS.QA_QUESTION(CONTENT) values('问题啊啊啊\\xF6\\x9D\\x98\\x84 额嗯嗯')")
//   jdbc.execute("insert into ZHS_BBS.QA_QUESTION(CONTENT) values('问题啊啊啊\\\\xF6\\\\x9D\\\\x98\\\\x84 额嗯嗯')")

    val query = jdbc.query("SELECT * from ZHS_BBS.QA_QUESTION  WHERE QUESTION_ID >= 288", ColumnMapRowMapper())

    query.forEach {
        val any = it["content"]
        println("content: $any")
    }

}