package com.jiangli.doc.txt.importdata

import com.jiangli.doc.txt.DB
import com.jiangli.doc.txt.Redis
import com.jiangli.doc.txt.execute

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


    pool.execute{
        val jedis = it

        val map = mutableMapOf<String, String?>()
//        map.put("aa","bb")
////        map.put("cc",null)
//        map.put("dd","")
//        map.put("ee","undefined")
//        map.put("ff","nil")
//        map.put("ggg","数据*****9999")
        jedis.hmset("test111", map)
//        jedis.hmset("test111", null)

    }

}