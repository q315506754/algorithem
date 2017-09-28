package com.jiangli.doc.txt.importdata

import com.jiangli.doc.txt.DB
import com.jiangli.doc.txt.Redis
import com.jiangli.doc.txt.execute
import com.jiangli.doc.txt.executeDelSplit

/**
 *
 *
 * @author Jiangli
 * @date 2017/9/11 13:27
 */

fun main(args: Array<String>) {
//    val pool = Redis.getYufaPool()
//    val jdbc = DB.getJDBCForYuFa()
    val pool = Redis.getYanfaPool()
    val jdbc = DB.getJDBCForYanFa()


    pool.execute{
        val jedis = it
//        jedis.set("user:uuid:35b94aacb6d13d11666fd656e75da406","161942715")

//        jedis.executeDel("th:userid:to:teacherid:${it}") //教师单个缓存
        jedis.executeDelSplit("del th:teacher:171 th:byConcernNum:171 th:personalGloryIds:171 th:wonderVideo:ids:171 th:quotations:ids:171")
//        jedis.executeDelSplit("del th:openapi:teachers:remoteresult:src:1:lock th:openapi:teachers:remoteresult:src:1")
    }

}
