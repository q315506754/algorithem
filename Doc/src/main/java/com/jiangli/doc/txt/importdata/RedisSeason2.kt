package com.jiangli.doc.txt.importdata

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
//    val jdbc = DB.getJDBCForYuFa()
    val jdbc =  DB.getJDBCForTHWaiWang()

    //名师id列表
    val idList = DB.getIdList(jdbc,2)
    val userIdList = DB.getUserIdList(jdbc,2)

    pool.execute{
        val jedis = it

        //映射缓存
        userIdList.forEach {
            jedis.executeDel("th:userid:to:teacherid:${it}") //userId映射缓存

            jedis.executeDel("th:myConcernIds:${it}") //我关注的人ids
        }


        //teacherId
        idList.forEach {
            jedis.executeDel("th:teacher:${it}") //教师单个缓存

//            jedis.executeDel("th:byConcernIds:${it}") //被关注人的teacherId 缓存300个
            jedis.executeDel("th:byConcernNum:${it}") //被关注数量

            jedis.executeDel("th:personalGloryIds:${it}") //个人荣耀 列表
            jedis.executeDel("th:wonderVideo:ids:${it}") //风采视频 列表
            jedis.executeDel("th:quotations:ids:${it}") //我的语录 列表
        }

    }

}

