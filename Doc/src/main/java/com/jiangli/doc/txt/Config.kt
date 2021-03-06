package com.jiangli.doc.txt

import org.apache.commons.dbcp.BasicDataSource
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.util.regex.Pattern

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 15:11
 */

object DB{
    fun getSrc1IdList( jdbc:JdbcTemplate): List<Int> {
        val ret = mutableListOf<Int>()

        val query = jdbc.query("select ID from db_teacher_home.TH_TEACHER where src=1", ColumnMapRowMapper())
        println(query)
        query.forEach {
            ret.add(it["ID"].toString().toInt())
        }

        return ret
    }
    fun getIdList( jdbc:JdbcTemplate,src:Int?=null): List<Int> {
        val ret = mutableListOf<Int>()

        val query = jdbc.query("select ID from db_teacher_home.TH_TEACHER ${if(src!=null) "where src=$src" else ""}", ColumnMapRowMapper())
        println(query)
        query.forEach {
            ret.add(it["ID"].toString().toInt())
        }

        return ret
    }
    fun getTeacherList( jdbc:JdbcTemplate,src:Int?=null): List<Map<String,Any>> {
        val ret = mutableListOf<Map<String,Any>>()

        val query = jdbc.query("select * from db_teacher_home.TH_TEACHER ${if(src!=null) "where src=$src" else ""}", ColumnMapRowMapper())
        println(query)
        query.forEach {
            ret.add(it)
        }

        return ret
    }
    fun getSrc1UserIdList( jdbc:JdbcTemplate): List<Long> {
        val ret = mutableListOf<Long>()

        val query = jdbc.query("select USER_ID from db_teacher_home.TH_TEACHER where src=1", ColumnMapRowMapper())
        println(query)
        query.forEach {
            ret.add(it["USER_ID"].toString().toLong())
        }

        return ret
    }
    fun getUserIdList( jdbc:JdbcTemplate,src:Int?=null): List<Long> {
        val ret = mutableListOf<Long>()

        val query = jdbc.query("select USER_ID from db_teacher_home.TH_TEACHER  ${if(src!=null) "where src=$src" else ""}", ColumnMapRowMapper())
        println(query)
        query.forEach {
            ret.add(it["USER_ID"].toString().toLong())
        }

        return ret
    }

     fun getJDBCForWaiWang(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
//    dataSource.url = "jdbc:mysql://192.168.9.223:3306"
        dataSource.url = "jdbc:mysql://120.27.136.140:3306" //外网
//    dataSource.username = "root"
//    dataSource.password = "ablejava"
        dataSource.username = "yanfa"
        dataSource.password = "BaQWxiaA852;?;>|"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }
     fun getWendaJDBCForWaiWang(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://118.178.128.52:3306" //外网
        dataSource.username = "jinx"
        dataSource.password = "jinx@2017"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }

     fun getJDBCForTHWaiWang(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://120.27.136.140:3306" //外网
        dataSource.username = "jiangli"
        dataSource.password = "JL@2017"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }

    //倾听
     fun getJDBCFor2CWaiWang(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://121.196.228.36:3306" //外网
        dataSource.username = "zwl"
        dataSource.password = "ZwL@2016#push"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }

     fun getJDBCForYanFa(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://192.168.9.223:3306"
        dataSource.username = "root"
        dataSource.password = "ablejava"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }
     fun getJDBCForYuFa(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://120.27.148.6:3306"
        dataSource.username = "root"
        dataSource.password = "ablejava"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }

    fun getQAJDBCForWaiwang(): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://118.178.128.52:3306"
        dataSource.username = "jinx"
        dataSource.password = "jinx@2017"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }
}


object Redis{
    fun getYufaPool(): JedisPool {
        return JedisPool(GenericObjectPoolConfig(), "114.55.4.242", 19000)
    }
    fun getYanfaPool(): JedisPool {
        return JedisPool(GenericObjectPoolConfig(), "192.168.9.170", 19000)
    }
}

fun JedisPool.execute(action: (Jedis) -> Unit) {
    val resource = resource

    action(resource)

    resource.close()
}

val deleteCol = mutableListOf<String>()
fun Jedis.executeDel(k: String) {
    val del = del(k)
    deleteCol.add(k)

    println("del $k")
//    println("del $k $del")
}

fun Jedis.executeDelSplit(k: String) {
    var s = k
    if (s.startsWith("del")) {
        s = s.substring(3)
    }
    s = s.trim()

    val split = s.split(Pattern.compile("""\s+"""))
    split.forEach{
        del(it)

        println("del $it")
    }
}

