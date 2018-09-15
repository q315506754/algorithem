package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.YufaRedis

/**
 *
 *
 * @author Jiangli
 * @date 2018/5/29 15:50
 */

fun main(args: Array<String>) {
    val codis = YufaRedis()
//    codis.execute("del qa2:openapi:students:guardTime:forStudent")
    codis.execute("hset user:170754026:login lastLoginTime \"2018-08-22\u001A11:11:11\"")
//    codis.execute("hset user:170754026:login lastLoginTime \"2018-08-22%2011:11:11\"")

//    println("\u001aaa")

//    codis.execute("hgetall user:170754026:login")

}