package com.jiangli.doc.txt

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


    pool.execute{
        val jedis = it

        println(jedis.smembers("cc:course:2021149:chapters:all"))
        println(jedis.smembers("cc:course:2021149:chapters"))

    }



}