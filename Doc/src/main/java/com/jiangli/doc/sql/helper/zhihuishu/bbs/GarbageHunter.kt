package com.jiangli.doc.sql.helper.zhihuishu.bbs

import com.jiangli.common.utils.DateUtil
import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import okhttp3.*
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.io.IOException
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

var client = OkHttpClient.Builder()
        // 连接池最大空闲连接30，生存周期30秒(空闲30秒后将被释放)
        .connectionPool(ConnectionPool(30, 30, TimeUnit.SECONDS))
        // 连接超时，10秒，失败时重试，直到10秒仍未连接上，则连接失败
        .connectTimeout(10, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        // 写超时，向服务端发送数据超时时间
        .writeTimeout(10, TimeUnit.SECONDS)
        // 读超时，从服务端读取数据超时时间
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

val DEBUG_MODE=false
//val DEBUG_MODE=true

//!!!!!!!dangerous!!!!!!!!!!
val DELETE_OPEN=true

//safe
//val DELETE_OPEN=false

//val log:(Any?)->Unit = ::println

//nothing
val log:(Any?)->Unit = { any ->
//    生产模式才打印
    if(!DEBUG_MODE)
        println(any)
}



fun main(args: Array<String>) {
    val env = Env.WAIWANG_ALL
    val jdbc = Zhsutil.getJDBC(env)
    val PAGE_SIZE = 100
    val INTERVAL: Long = if(DEBUG_MODE) 1 else 3
    val timeUnit = if(DEBUG_MODE) TimeUnit.SECONDS else TimeUnit.MINUTES
    val domain= "http://114.55.26.161:9080/courseqa/student/qa"
//    "userId=%d&page=%d&pageSize=%d"
    request("http://114.55.26.161:9080/courseqa/testDeploy.jsp", "?")

    val pool = Executors.newScheduledThreadPool(3)
//    http://114.55.26.161:9080/courseqa/student/qa/issueQuestion
//    delQuestion questionId deletePeron
//    delAnswer answerId deletePeron
//    delComment commentId deletePeron
//    http://114.55.26.161:9080/courseqa/testDeploy.jsp
    pool.scheduleAtFixedRate(BaseGreenWork(jdbc,PAGE_SIZE,"ZHS_BBS.QA_QUESTION","QUESTION_ID","CONTENT","CREATE_USER",{ vId, vContent, vUserId ->
        val param = String.format("questionId=%s&deletePeron=%s", vId, vUserId)
        request("${domain}/delQuestion", param+ContentAnalyser.signParam(param))
    }), 0, INTERVAL, timeUnit)
    pool.scheduleAtFixedRate(BaseGreenWork(jdbc,PAGE_SIZE,"ZHS_BBS.QA_ANSWER","ID","A_CONTENT","A_USER_ID",{ vId, vContent, vUserId ->
        val param = String.format("answerId=%s&deletePeron=%s", vId, vUserId)
        request("${domain}/delAnswer", param+ContentAnalyser.signParam(param))
    }), 0, INTERVAL, timeUnit)
    pool.scheduleAtFixedRate(BaseGreenWork(jdbc,PAGE_SIZE,"ZHS_BBS.QA_COMMENT","ID","COMMENT_CONTENT","COMMENT_USER_ID",{ vId, vContent, vUserId ->
        val param = String.format("commentId=%s&deletePeron=%s", vId, vUserId)
        request("${domain}/delComment", param+ContentAnalyser.signParam(param))
    }), 0, INTERVAL, timeUnit)

}

private fun request(url: String, param: String) {
    if (!DELETE_OPEN) {
        return
    }
    val request = Request.Builder()
            .url(url)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36")
            .post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), param))
            .build()
    // 执行请求
    var response: Response? = null
    try {
        response = client.newCall(request).execute()
        val text = response.body()?.string()
        println(response)
        println(text)
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

class BaseGreenWork(val jdbc: JdbcTemplate, val pagE_SIZE: Int, val tbl: String, val mainIdField: String, val contentField: String,val creatorField: String,val actionDelete: (vId:Long,vContent:String,vUserId:String)->Unit) :Runnable{
    var lastId:Long?=null

    override fun run() {
//        val env = Env.WAIWANG_ALL
//        val jdbc = Zhsutil.getJDBC(env)

            log("$tbl  "+ curTime())

        if (lastId==null) {
//            DEBUG时取最新的 100 页 否则最后一页
            val OFFSET = if(DEBUG_MODE) pagE_SIZE*10 else pagE_SIZE

            val listsNew = jdbc.query("""
SELECT
  $mainIdField
FROM $tbl
WHERE
  IS_DELETED=0
ORDER BY  $mainIdField DESC
LIMIT $OFFSET,1;
        """.trimIndent(), ColumnMapRowMapper())

            lastId = listsNew[0]["$mainIdField"].toString().toLong()
            log("$tbl  initial lastId:$lastId")
        }

        if (lastId!=null) {
            val lists = jdbc.query("""
            SELECT
  $mainIdField
  ,$contentField
  ,$creatorField
FROM $tbl
WHERE
  IS_DELETED=0
AND  $mainIdField > $lastId
ORDER BY  $mainIdField ASC
LIMIT $pagE_SIZE;
        """.trimIndent(), ColumnMapRowMapper())
//            log(lists)

            if (lists.size>0) {
                val oldLastId = lastId
                lastId = lists[lists.lastIndex]["$mainIdField"].toString().toLong()
                log("$tbl  $oldLastId -> $lastId")

                lists.forEach {
                    val vId = it["$mainIdField"].toString().toLong()
                    val vContent = it["$contentField"].toString()
                    val vUserId = it["$creatorField"].toString()
//                    log("$vId $vContent")

                    val pl:(Any?)->Unit = System.err::println
                    val analyse = ContentAnalyser.analyse(vContent)
                    if (analyse!=AnaRs.OK) {
                        pl("${curTime()} ${tbl} $analyse $vId $vUserId $vContent")

                        actionDelete(vId,vContent,vUserId)
                    }
                }
            } else {
                log("$tbl  $lastId ( newest...)")
            }
        }
    }

    private fun curTime() = DateUtil.getCurrentDate_YMDHms()
}