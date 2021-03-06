package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import com.mongodb.BasicDBObject

/**
 *
 *
 * @author Jiangli
 * @date 2018/12/21 14:14
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val mongo = Ariesutil.getMongo(env, Ariesutil.MongoDbCol.ARIES_LOGIN)

    val EMAIL = "jinpeng@zhishish.com"
    val ouputFile = PathUtil.desktop("""$EMAIL-登录日志.xlsx""")

    val userId = Ariesutil.getUserId(jdbc, "","", EMAIL).toLong()
    val uuid = Ariesutil.convertUUID(userId)

    println("userId:$userId uuid:$uuid")

    val list = mutableListOf<MutableMap<String,Any>>()
    val query = BasicDBObject()
    query.put("user_id",userId)
//    query.put("user_id",100003685)
//    query.put("user_id",100003685L)
    val sort = BasicDBObject()
    sort.put("loginTime",-1)
    val dbCursor = mongo.find(query).sort(sort)
    while (dbCursor.hasNext()) {
        val next = dbCursor.next()
        println(next)

        val one = mutableMapOf<String,Any>()
        one.putAll(next.toMap() as Map<out String, Any>)
        one.remove("_id")
        one.remove("_class")

        val ts = one.get("loginTime") as java.util.Date
//        println(ts?.javaClass)
        one.put("loginTime", DateUtil.getDate_yyyyMMddHHmmss(ts.time))

        list.add(one)
    }

    writeMapToExcel(ouputFile,list)

}