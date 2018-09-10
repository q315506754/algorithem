package com.jiangli.doc.sql

import org.apache.commons.dbcp.BasicDataSource
import org.hashids.Hashids
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.lang.Exception


/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 17:09
 */
object Ariesutil {
    val hashids = Hashids("user#2018@g2s.cn", 8)
    fun convertUUID(l: Long): String {
        return hashids.encode(l)
    }

    fun convertUUID(l: Int): String {
        return convertUUID(l.toLong())
    }

    fun convertUUID(l: String): Long {
        return hashids.decode(l)[0]
    }

    fun getJDBC(env: Env): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://${env.host}"
        dataSource.username = "${env.username}"
        dataSource.password = "${env.pwd}"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }

    fun validateNum(str: String, query: List<Any>, limit: Int = 1) {
        var message: String? = null
        if (query.size < limit) {
            message = "$str 查到数量 < $limit 个"
        } else if (query.size == limit) {
            message = "$str √"
            println(message)
            return
        } else if (query.size > limit) {
            message = "$str 查到数量 > $limit 个"
        }
        query.forEach {
            println("$it")
        }
        throw Exception(message)
    }

    fun getUserId(jdbc: JdbcTemplate, name: String? = "", mobile: String? = "", email: String? = ""): String {
        if (mobile?.isNotEmpty()!!) {
            val s = "SELECT ID,NAME,MOBILE,EMAIL FROM db_aries_user.TBL_USER WHERE MOBILE=$mobile and IS_DELETED = 0;"
            println("执行sql")
            println("   $s")
            val query = jdbc.query(s, ColumnMapRowMapper())
            validateNum("根据手机号", query)
            return query[0]["ID"].toString()
        }
        if (email?.isNotEmpty()!!) {
            val s = "SELECT ID,NAME,MOBILE,EMAIL FROM db_aries_user.TBL_USER WHERE EMAIL='$email' and IS_DELETED = 0;"
            println("执行sql")
            println("   $s")
            val query = jdbc.query(s, ColumnMapRowMapper())
            validateNum("根据邮箱", query)
            return query[0]["ID"].toString()
        }
        if (name?.isNotEmpty()!!) {
            val s = "SELECT ID,NAME,MOBILE,EMAIL FROM db_aries_user.TBL_USER WHERE NAME like '%$name%' and IS_DELETED = 0;"
            val query = jdbc.query(s, ColumnMapRowMapper())
            println("执行sql")
            println("   $s")
            validateNum("根据名字", query)
            return query[0]["ID"].toString()
        }
        throw Exception("没有足够的参数查询user")
    }

    fun confirmUserId(jdbc: JdbcTemplate, uId: Number) {
        val s = "SELECT ID,NAME,MOBILE,EMAIL,CREATE_TIME,PASSWORD FROM db_aries_user.TBL_USER WHERE ID = $uId and IS_DELETED = 0;"
        val query = jdbc.queryForObject(s, ColumnMapRowMapper())
        println("确认用户($uId):$query")
    }
    fun confirm2CCourseId(jdbc: JdbcTemplate, courseId: Long) {
        val s = "SELECT ID,NAME,MOBILE,EMAIL,CREATE_TIME,PASSWORD FROM db_aries_user.TBL_USER WHERE ID = $courseId and IS_DELETED = 0;"
        val query = jdbc.queryForObject(s, ColumnMapRowMapper())
        println("确认2c课程($courseId):$query")
    }

    fun confirmUUID(jdbc: JdbcTemplate, uId: String) {
        confirmUserId(jdbc, convertUUID(uId))
    }

    fun injectTest(jdbc: JdbcTemplate, id:Any?, inf: NamedSimpleCachedTableQueryer): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()

        val oldField = inf.fields
        inf.fields = "*"
        val idName = inf.props[0]
        map.put("$idName",id)
        injectData(jdbc,map,inf)
        inf.fields = oldField

        val first = map.keys.filter { d -> d != idName }.first()

//        println(map[first])
        return map[first] as Map<String, Any?>
    }

    fun injectData(jdbc: JdbcTemplate, map: MutableMap<String, Any?>, vararg infs: DataQueryer) {
        infs.forEach {
            val inf = it

            val nMap = mutableMapOf<String, Any?>()
            nMap.putAll(map)

            map.forEach { entry ->
                val key = entry.key
                if (inf.intercept(key)) {
                    val rs = inf.query(jdbc, entry.value)
//                    val keyProp = inf.javaClass.simpleName
//                    val keyProp = key+"_Info_"+inf.javaClass.simpleName
                    val keyProp = inf.name(key).replace("${'$'}{params}",entry.value?.toString()?:"null")

                    var rsFinal:Any? = null

                    if (rs.size == 0) {
                        rsFinal = mutableMapOf<String, Any?>()
                    } else if (rs.size == 1) {
                        rsFinal = rs[0]
                    } else {
                        rsFinal = rs
                    }

                    nMap.put(keyProp,rsFinal)

                    rsFinal?.let {
                        if (rsFinal is Map<*, *>) {
                            injectData(jdbc, rsFinal as MutableMap<String, Any?>, *infs)
                        }
                        else if (rsFinal is List<*>) {
                            (rsFinal as List<*>).forEach {
                                injectData(jdbc, it as MutableMap<String, Any?>, *infs)
                            }
                        }
                    }

                }
            }

            map.putAll(nMap)
        }
    }

    fun injectFromUserId(jdbc: JdbcTemplate, userId: Long): MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map.put("userId", userId)

        Ariesutil.injectData(jdbc, map
                ,*allQueryer
        )
        return map
    }
}

enum class Env(val rechargeCbUrl: String, val host: String, val username: String, val pwd: String) {
    YANFA("http://127.0.0.1:89/aries-pay-account/recharge", "192.168.222.8:3306", "root", "ablejava")
    ,
    YUFA("http://yf-account-pay.g2s.cn/aries-pay-account/recharge", "122.112.239.142:8635", "test", "Test@9527")
    ,
    WAIWANG("http://account-pay.g2s.cn/aries-pay-account/recharge", "rm-bp1yjg70fe47ml9gueo.mysql.rds.aliyuncs.com:3306", "huyue", "huyue@2018")
}

fun main(args: Array<String>) {
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    println(Ariesutil.convertUUID("dBaJpLjy"))
//    println(Ariesutil.convertUUID(100002065))
//    println(Ariesutil.confirmUserId(jdbc,100002215))
//    println(Ariesutil.confirmUserId(jdbc,100002065))
//    println(Ariesutil.confirmUUID(jdbc,"ykRXob2n"))

    println(Ariesutil.confirmUserId(jdbc, 100008058))

//    println(Ariesutil.getUserId(jdbc, "武凌寒"))
//    println(Ariesutil.getUserId(jdbc, "陆"))
//    println(Ariesutil.getUserId(jdbc, "张陆诗"))
//    println(Ariesutil.getUserId(jdbc, null,"13588708991"))
//    println(Ariesutil.getUserId(jdbc, "","13588708991",""))

}