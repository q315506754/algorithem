package com.jiangli.doc.txt

import org.apache.commons.dbcp.BasicDataSource
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

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

