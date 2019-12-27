package com.jiangli.doc.sql.helper.aries

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.BaseConfig
import com.jiangli.doc.sql.NamedSimpleCachedTableQueryer
import com.mongodb.DBCollection
import com.mongodb.Mongo
import com.mongodb.ServerAddress
import org.apache.commons.dbcp.BasicDataSource
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.hashids.Hashids
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import java.util.*


/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 17:09
 */

fun JedisPool.execute(action: (Jedis) -> Unit) {
    val resource = resource

    action(resource)

    resource.close()
}

//redis
public object Ariesutil {
    val hashids = Hashids("user#2018@g2s.cn", 8)

     fun getYufaPool(): JedisPool {
//        return JedisPool(GenericObjectPoolConfig(), "114.55.4.242", 19000)
        return JedisPool(GenericObjectPoolConfig(), "120.92.138.210", 6379,3000,"Ablejava!2018")
    }
     fun getYanfaPool(): JedisPool {
//        return JedisPool(GenericObjectPoolConfig(), "192.168.9.205", 6379)
        return JedisPool(GenericObjectPoolConfig(), "192.168.9.170", 19000)
    }

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
//        val dataSource = BasicDataSource()
//        dataSource.driverClassName = "com.mysql.jdbc.Driver"
//        dataSource.url = "jdbc:mysql://${env.host}"
//        dataSource.username = "${env.username}"
//        dataSource.password = "${env.pwd}"
//        val jdbcTemplate = JdbcTemplate(dataSource)
//        return jdbcTemplate

        return getJDBC(env,null)
    }
    fun getJDBC(env: Env, db:String?=null): JdbcTemplate {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.jdbc.Driver"
        var str = ""
        if (db!=null) {
            str = "/$db?characterEncoding=UTF-8&allowMultiQueries=true"
        }else {
            str = "?characterEncoding=UTF-8&allowMultiQueries=true"
        }

        dataSource.url = "jdbc:mysql://${env.host}$str"
        dataSource.username = "${env.username}"
        dataSource.password = "${env.pwd}"
        val jdbcTemplate = JdbcTemplate(dataSource)
        return jdbcTemplate
    }

    enum class MongoDb{
        aries_login_logs
    }
    enum class MongoDbCol(val db: MongoDb) {
        ARIES_LOGIN(MongoDb.aries_login_logs);

    }

    fun getMongo(env: Env,col:MongoDbCol): DBCollection {
        val mongo = Mongo(Arrays.asList(ServerAddress("120.27.218.215", 27017)))
        val db = mongo.getDB(col.db.name)
        val collection = db.getCollection(col.name)

//        if (env == Env.WAIWANG) {
//        }

        return collection!!
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
        return getUser(jdbc, name, mobile, email)!![0]["ID"].toString()
    }

    fun getCompany(jdbc: JdbcTemplate, name: String? = "", id: Int? = 0): MutableMap<String, Any> {
        val companyDto = if (name?.isBlank() == false) {
            jdbc.queryForObject("""
SELECT * from db_aries_company.TBL_COMPANY c
WHERE  c.IS_DELETED=0
AND c.NAME like '%${name}%'
;
""".trimIndent(), ColumnMapRowMapper())
        } else {
            jdbc.queryForObject("""
SELECT * from db_aries_company.TBL_COMPANY c
WHERE  c.IS_DELETED=0
AND c.ID =$id
;
""".trimIndent(), ColumnMapRowMapper())
        }

        return companyDto
    }

    fun processCompanyClass(jdbc: JdbcTemplate, companyDto: MutableMap<String, Any>,processor:(it: Map<String, Any>)->Unit ) {
        val COMPANY_ID = companyDto["ID"]

        //    查询企业下班级
        val queryClassAndCompany = jdbc.query("""
SELECT * from db_aries_run.TBL_CLASS cls
 LEFT JOIN db_aries_company.TBL_COMPANY c on cls.COMPANY_ID=c.ID
WHERE cls.IS_DELETE=0
AND c.IS_DELETED=0
AND c.ID = ${COMPANY_ID}
;
""".trimIndent(), ColumnMapRowMapper())

        queryClassAndCompany.forEachIndexed { index, it ->
            val CLASS_NAME = it["CLASS_NAME"]!!
            val CLASS_ID = it["CLASS_ID"]
            println("classes:${index + 1}/${queryClassAndCompany.size} $CLASS_ID $CLASS_NAME")

            processor(it)
        }

    }


    fun processCompanyClassStudents(jdbc: JdbcTemplate, companyDto: MutableMap<String, Any>,processor:(companyclass: Map<String, Any>,student: Map<String, Any>)->Unit) {
        processCompanyClass(jdbc, companyDto){
            val companyclass = it
            val CLASS_NAME = it["CLASS_NAME"]!!
            val CLASS_ID = it["CLASS_ID"]

            //    查询班级下学生
            val queryClassStudents = jdbc.query("""
SELECT u.* from db_aries_study.TBL_USER_COURSE uc
LEFT JOIN db_aries_user.TBL_USER u on uc.USER_ID = u.ID
WHERE
uc.IS_GUEST=0
AND uc.CLASS_ID=${CLASS_ID}
AND uc.IS_DELETE=0
;
""".trimIndent(), ColumnMapRowMapper())

            queryClassStudents.forEachIndexed { index, it ->
                val USER_NAME = it["NAME"] ?: ""
                val USER_ID = it["ID"] ?: ""
                println("   students:${index + 1}/${queryClassStudents.size} $USER_ID $USER_NAME")

                processor(companyclass,it)
            }
        }
    }

    fun getUser(jdbc: JdbcTemplate, name: String? = "", mobile: String? = "", email: String? = ""): MutableList<MutableMap<String, Any>>? {
        if (mobile?.isNotEmpty()!!) {
            val s = "SELECT ID,NAME,MOBILE,EMAIL FROM db_aries_user.TBL_USER WHERE MOBILE=$mobile and IS_DELETED = 0;"
            println("执行sql")
            println("   $s")
            val query = jdbc.query(s, ColumnMapRowMapper())
            validateNum("根据手机号", query)
            return query
        }
        if (email?.isNotEmpty()!!) {
            val s = "SELECT ID,NAME,MOBILE,EMAIL FROM db_aries_user.TBL_USER WHERE EMAIL='$email' and IS_DELETED = 0;"
            println("执行sql")
            println("   $s")
            val query = jdbc.query(s, ColumnMapRowMapper())
            validateNum("根据邮箱", query)
            return query
        }
        if (name?.isNotEmpty()!!) {
            val s = "SELECT ID,NAME,MOBILE,EMAIL FROM db_aries_user.TBL_USER WHERE NAME like '%$name%' and IS_DELETED = 0;"
            val query = jdbc.query(s, ColumnMapRowMapper())
            println("执行sql")
            println("   $s")
            validateNum("根据名字", query)
            return query
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
        return BaseConfig.injectTest(jdbc, id, inf)
    }

    fun injectFromUserId(jdbc: JdbcTemplate, userId: Long): MutableMap<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        map.put("userId", userId)

        BaseConfig.injectData(jdbc, map
                , *allAriesQueryer
        )
        return map
    }
}

enum class Env(val rechargeCbUrl: String, val host: String, val username: String, val pwd: String) {
    YANFA("http://127.0.0.1:89/aries-pay-account/recharge", "192.168.222.8:3306", "root", "ablejava")
    ,
//    YUFA("http://yf-account-pay.g2s.cn/aries-pay-account/recharge", "122.112.239.142:8635", "test", "Test@9527")
//    YUFA("http://yf-account-pay.g2s.cn/aries-pay-account/recharge", "163.53.169.237:3306", "root", "ablejava")
    YUFA("http://yf-account-pay.g2s.cn/aries-pay-account/recharge", "120.92.138.210:3306", "root", "ablejava")
    ,
    WAIWANG("http://account-pay.g2s.cn/aries-pay-account/recharge", "rm-bp1yjg70fe47ml9gueo.mysql.rds.aliyuncs.com:3306", "huyue", "huyue@2018")
}

fun main(args: Array<String>) {
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    println(Ariesutil.convertUUID("dBaJpLjy"))
//    println(Ariesutil.convertUUID("yGAJXE0K"))
//    println(Ariesutil.convertUUID("KVo6v8ln"))
//    println(Ariesutil.convertUUID("y5xVm39n"))

    println(Ariesutil.convertUUID("npo6a7kK"))
//    println(Ariesutil.convertUUID(100002323))
    println(Ariesutil.convertUUID(100000062))
    println(Ariesutil.convertUUID(10001234))
    println(Ariesutil.convertUUID(100002253))
    println(Ariesutil.convertUUID(100001936))
    println(Ariesutil.convertUUID(100002190))


    println(Ariesutil.injectFromUserId(jdbc,Ariesutil.convertUUID("dgr62RQy")))

    //    println(Ariesutil.convertUUID(Ariesutil.getUserId(jdbc, "", "13300000000").toInt()))
    println(Ariesutil.convertUUID(Ariesutil.getUserId(jdbc, "", "13661749570").toInt()))

//    println(Ariesutil.confirmUserId(jdbc,100002215))
//    println(Ariesutil.confirmUserId(jdbc,100002065))
//    println(Ariesutil.confirmUUID(jdbc,"ykRXob2n"))

//    println(Ariesutil.confirmUserId(jdbc, 100008058))

//    println(Ariesutil.getUserId(jdbc, "武凌寒"))
//    println(Ariesutil.getUserId(jdbc, "陆"))
//    println(Ariesutil.getUserId(jdbc, "张陆诗"))
//    println(Ariesutil.getUserId(jdbc, null,"13588708991"))
//    println(Ariesutil.getUserId(jdbc, "","13588708991",""))

    println(PathUtil.desktop("aa.txt"))
}