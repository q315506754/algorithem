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

    val ids = arrayListOf(100013972
            ,100013560
            ,100011323
            ,100013192
            )

    ids.forEach {
    val sql = """
hgetall aries:$it:login
""".trimIndent()

        println(sql)
    }

}