package com.jiangli.doc.txt.zhs

import com.jiangli.doc.txt.DB
import com.jiangli.doc.txt.Redis
import com.jiangli.doc.txt.execute
import com.jiangli.doc.txt.executeDel

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


    val courseIds = arrayOf(2021158,2021149)
//    val courseId = 2021149

    pool.execute{
        val jedis = it

        courseIds.forEach {
            jedis.executeDel("cc:course:$it:treenity")
            jedis.executeDel("cc:course:$it")
        }
    }




}