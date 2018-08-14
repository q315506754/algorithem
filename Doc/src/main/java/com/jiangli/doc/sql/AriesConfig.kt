package com.jiangli.doc.sql

import org.apache.commons.dbcp.BasicDataSource
import org.springframework.jdbc.core.JdbcTemplate

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 17:09
 */
object Ariesutil{
    fun getJDBC(env:Env): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        dataSource.url = "jdbc:mysql://${env.host}"
        dataSource.username = "${env.username}"
        dataSource.password = "${env.pwd}"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }
}
enum class Env(val rechargeCbUrl: String,val host: String,val username: String,val pwd: String) {
    YANFA("http://127.0.0.1:89/aries-pay-account/recharge","192.168.222.8:3306","root","ablejava")
    ,YUFA("http://yf-account-pay.g2s.cn/aries-pay-account/recharge","122.112.239.142:8635","test","Test@9527")
    ,WAIWANG("http://account-pay.g2s.cn/aries-pay-account/recharge","rm-bp1yjg70fe47ml9gueo.mysql.rds.aliyuncs.com:3306","huyue","huyue@2018")
}