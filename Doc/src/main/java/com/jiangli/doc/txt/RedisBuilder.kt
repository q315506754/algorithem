package com.jiangli.doc.txt

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 14:39
 */


fun main(args: Array<String>) {
//    val pool = Redis.getYanfaPool()
//    val jdbc = DB.getJDBCForYanFa()

    val pool = Redis.getYufaPool()
    val jdbc = DB.getJDBCForYuFa()

    //名师id列表
    val idList = DB.getSrc1IdList(jdbc)


    pool.execute{
        val jedis = it
        println(jedis.smembers("th:lock:totals"))

        jedis.del("th:openapi:teachers:remoteresult:src:1")

        idList.forEach {
            jedis.del("th:teacher:${it}") //教师单个缓存
        }

//        it.smembers()
    }




}