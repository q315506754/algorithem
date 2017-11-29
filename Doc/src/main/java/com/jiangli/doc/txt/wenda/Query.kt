package com.jiangli.doc.txt.wenda

import com.jiangli.doc.txt.DB
import com.jiangli.doc.txt.Redis

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

    val userId = 167254279
    val questionId = 909463

    println("zrevrange qa:my:questionIds:$userId 0 -1")
    println("zrevrange qa:answer:qustionForAnswer:$questionId 0 -1")
    println("smembers qa:operation:putquestionbyansweroperating:$userId")
    println("hget qa:operation:auditrecords:600102:2 contentShowStatus")
    println("hget qa:operation:auditrecords:600103:2 contentShowStatus")



//    curl -X POST 192.168.9.252:8080/jenkins/job/%E5%BC%80%E5%8F%91-%E9%A2%84%E5%8F%91codis%20%E6%93%8D%E4%BD%9C%EF%BC%88%E5%8D%95%E6%9D%A1%E5%91%BD%E4%BB%A4%EF%BC%89/build \
//    --user kaifa:KaiFa#ShangHai@2016 \
//    --data-urlencode json='{"parameter": [{"name":"command", "value":"high"}]}'

//    pool.execute{
//        val jedis = it
//
////        println(it.get("qa:operation:myQuestioninfo:$userId:operationType"))
//        //我提出的问题
//        it.zrevrange("qa:my:questionIds:$userId",0,-1)
//
//        //问题下的回答
//        it.zrevrange("qa:answer:qustionForAnswer:$questionId",0,-1)
//
//        //操作过的问题id
//        println(it.smembers("qa:operation:putquestionbyansweroperating:$userId"))
//    }



}