package com.jiangli.doc.sql.helper.zhihuishu

import com.jiangli.doc.sql.BaseConfig
import com.jiangli.doc.sql.NamedSimpleCachedTableQueryer
import org.apache.commons.dbcp.BasicDataSource
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.hashids.Hashids
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import redis.clients.jedis.JedisPool
import java.lang.Exception


/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 17:09
 */
object Zhsutil {
    val hashids = Hashids("Able@2018#UserId2UUID", 8)
    fun convertUUID(l: Long): String {
        return hashids.encode(l)
    }

    fun convertUUID(l: Int): String {
        return convertUUID(l.toLong())
    }

    fun convertUUID(l: String): Long {
        return hashids.decode(l)[0]
    }

    fun getYufaPool(): JedisPool {
        return JedisPool(GenericObjectPoolConfig(), "114.55.4.242", 19000)
    }
    fun getYanfaPool(): JedisPool {
        return JedisPool(GenericObjectPoolConfig(), "192.168.9.170", 19000)
    }

    fun getJDBC(env: Env,db:String?=null): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        var str = ""
        if (db!=null) {
            str = "/$db?characterEncoding=UTF-8&allowMultiQueries=true"
        }

        dataSource.url = "jdbc:mysql://${env.host}$str"
        dataSource.username = "${env.username}"
        dataSource.password = "${env.pwd}"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }

    fun getDB(env: Env): BasicDataSource {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://${env.host}"
        dataSource.username = "${env.username}"
        dataSource.password = "${env.pwd}"
        return dataSource
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
            val s = "SELECT ID,REAL_NAME,PHONE_NUMBER,E_MAIL FROM db_G2S_OnlineSchool.TBL_USER WHERE PHONE_NUMBER=$mobile and IS_DELETED = 0;"
            println("执行sql")
            println("   $s")
            val query = jdbc.query(s, ColumnMapRowMapper())
            validateNum("根据手机号", query)
            return query[0]["ID"].toString()
        }
        if (email?.isNotEmpty()!!) {
            val s = "SELECT ID,REAL_NAME,PHONE_NUMBER,E_MAIL FROM db_G2S_OnlineSchool.TBL_USER WHERE E_MAIL='$email' and IS_DELETED = 0;"
            println("执行sql")
            println("   $s")
            val query = jdbc.query(s, ColumnMapRowMapper())
            validateNum("根据邮箱", query)
            return query[0]["ID"].toString()
        }
        if (name?.isNotEmpty()!!) {
            val s = "SELECT ID,REAL_NAME,PHONE_NUMBER,E_MAIL FROM db_G2S_OnlineSchool.TBL_USER WHERE REAL_NAME like '%$name%' and IS_DELETED = 0;"
            val query = jdbc.query(s, ColumnMapRowMapper())
            println("执行sql")
            println("   $s")
            validateNum("根据名字", query)
            return query[0]["ID"].toString()
        }
        throw Exception("没有足够的参数查询user")
    }

    fun confirmUserId(jdbc: JdbcTemplate, uId: Number) {
        val s = "SELECT ID,REAL_NAME,PHONE_NUMBER,E_MAIL,CREATE_TIME,PASSWORD FROM db_G2S_OnlineSchool.TBL_USER WHERE ID = $uId and IS_DELETED = 0;"
        val query = jdbc.queryForObject(s, ColumnMapRowMapper())
        println("确认用户($uId):$query")
    }
    fun confirm2CCourseId(jdbc: JdbcTemplate, courseId: Long) {
        val s = "SELECT ID,REAL_NAME,PHONE_NUMBER,E_MAIL,CREATE_TIME,PASSWORD FROM db_G2S_OnlineSchool.TBL_USER WHERE ID = $courseId and IS_DELETED = 0;"
        val query = jdbc.queryForObject(s, ColumnMapRowMapper())
        println("确认2c课程($courseId):$query")
    }

    fun confirmUUID(jdbc: JdbcTemplate, uId: String) {
        confirmUserId(jdbc, convertUUID(uId))
    }

    fun injectTest(jdbc: JdbcTemplate, id:Any?, inf: NamedSimpleCachedTableQueryer): Map<String, Any?> {
        return BaseConfig.injectTest(jdbc, id, inf)
    }

    fun injectFromUserId(jdbc: JdbcTemplate, userId: Long): MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map.put("userId", userId)

        BaseConfig.injectData(jdbc, map
                , *allZhsQueryer
        )
        return map
    }

    fun injectFromQuestionId(jdbc: JdbcTemplate, questionId: Long): MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map.put("questionId", questionId)

        BaseConfig.injectData(jdbc, map
                , *allZhsQueryer
        )
        return map
    }
}

enum class Env( val host: String, val username: String, val pwd: String) {
    YANFA("192.168.9.223:3306", "root", "ablejava")
    ,YUFA("120.27.148.6:3306", "root", "ablejava")
    ,WAIWANG_BBS("118.178.128.52:3306", "jinx", "jinx@2017")
    ,WAIWANG_ONLINESCHOOL("120.27.136.140:3306", "yanfa", "BaQWxiaA852;?;>|")
    ,WAIWANG_TEACHERHOME("120.27.136.140:3306", "jiangli", "JL@2017")
    ,WAIWANG_ALL("114.55.32.89:3306", "yanfa", "BaQWxiaA852;?;>|")
    ,WAIWANG_ARMY("121.196.228.36:3306", "jiangl", "jiangl123!")
    ,WAIWANG_2C_LISTEN("121.196.228.36:3306", "zwl", "ZwL@2016#push")
}

fun main(args: Array<String>) {
//    val env = Env.YUFA
    val env = Env.WAIWANG_ONLINESCHOOL
    val jdbc = Zhsutil.getJDBC(env)

    println(Zhsutil.convertUUID("E7q2ZbY0"))
//    println(Zhsutil.convertUUID("dBaJpLjy"))
//    println(Zhsutil.convertUUID(100002065))
//    println(Zhsutil.confirmUserId(jdbc,100002215))
//    println(Zhsutil.confirmUserId(jdbc,100002065))
//    println(Zhsutil.confirmUUID(jdbc,"ykRXob2n"))

//    println(Zhsutil.confirmUserId(jdbc, 100008058))
    println(Zhsutil.getUserId(jdbc, "","13761156786"))


}