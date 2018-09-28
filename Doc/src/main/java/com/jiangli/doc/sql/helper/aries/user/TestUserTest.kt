package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
//    val env = Env.YANFA
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)


    val ids = arrayListOf(100000091
            ,100003994
            ,100005460
            ,100006093
            ,100006290
            ,100007071
            ,100007076)

    ids.forEach {
    val sql = """
INSERT INTO `db_aries_manage`.`TBL_TEST_USER` (`USER_ID`) VALUES ($it);
""".trimIndent()

        println(sql)
    }

}