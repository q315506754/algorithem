package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import com.mongodb.BasicDBObject
import org.springframework.jdbc.core.ColumnMapRowMapper

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

    val ouputDir = PathUtil.desktop("""登录记录_sql.xlsx""")

    val rs = jdbc.query("""
SELECT
    a.ID,a.`NAME`,a.MOBILE
from
    db_aries_user.tbl_user a

WHERE
        a.ID IN (SELECT USER_ID FROM db_aries_user.tbl_user_company WHERE COMPANY_ID = 183) AND IS_DELETED = 0;
    """.trimIndent(), ColumnMapRowMapper())

    //    val MOBILES = "13661749570"
    val list = mutableListOf<MutableMap<String,Any>>()

    var n = 1
    rs.forEach {

        val userId = it["ID"]
        println("${n++}/${rs.size} userId:$userId")

        val query = BasicDBObject()
        query.put("user_id",userId)
//        query.put("appType","ARIES_PC")
        val sort = BasicDBObject()
        sort.put("loginTime",-1)

        val dbCursor = mongo.find(query).sort(sort)
        var count = 0

        var lastLoginTime:java.util.Date? = null
        var firstLoginTime:java.util.Date? = null

        while (dbCursor.hasNext()) {
            val next = dbCursor.next()
            //        println(next)

            val one = mutableMapOf<String,Any>()
            one.putAll(next.toMap() as Map<out String, Any>)
            one.remove("_id")
            one.remove("_class")
//
            val ts = one.get("loginTime") as java.util.Date
            if (lastLoginTime == null) {
                lastLoginTime = ts
            }

            if (!dbCursor.hasNext()) {
                firstLoginTime = ts
            }
//            //        println(ts?.javaClass)
//            one.put("loginTime", DateUtil.getDate_yyyyMMddHHmmss(ts.time))
//
//            list.add(one)
            count++
        }

        it["首次登录时间"]=""
        if (firstLoginTime != null) {
            it["首次登录时间"]= DateUtil.getDate_yyyyMMddHHmmss(firstLoginTime.time)
        }

        it["最后登录时间"]=""
        if (lastLoginTime != null) {
            it["最后登录时间"]= DateUtil.getDate_yyyyMMddHHmmss(lastLoginTime.time)
        }

        it["登录次数"]=count

//        test
//        writeMapToExcel(ouputDir,rs)
//        return
    }

    writeMapToExcel(ouputDir,rs)


}