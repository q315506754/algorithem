package com.jiangli.doc.sql.helper.zhihuishu.bbs

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.EmailUtil
import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.UserIdQueryer
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import okhttp3.*
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.util.*
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

val domain= "http://114.55.26.161:9080/courseqa/student/qa"

//val DEBUG_MODE=false
val DEBUG_MODE=true
val DEBUG_PREV_PAGE = 1000

//!!!!!!!dangerous!!!!!!!!!!
val DELETE_OPEN=true
//val DELETE_OPEN=false //safe

val CASCADE_DELETE=true
//val CASCADE_DELETE=false

//开启邮件发送
//val SEND_MAIL=false
val SEND_MAIL=true
val mailTo = arrayListOf("315506754@qq.com")

//val log:(Any?)->Unit = ::println

//val timeStr = "0930-1200/30,1330-2100/30"
val timeStr = "0730-2300/30"

//nothing
val debug:(Any?)->Unit = { any ->
//    生产模式才打印
    if(!DEBUG_MODE)
        println(any)
}
val info:(Any?)->Unit = { any ->
//    生产模式才打印
    if(!DEBUG_MODE)
        println(any)
}
val error:(Any?)->Unit = { any ->
    System.err.println(any)
}

data class IllegalQa(val tbl: String,val analyse: AnaRs,val vId: Long,val vUserId: String,val vContent: String,val deleteRs: String)
val illegalList = Vector<IllegalQa>()
fun parseHtml():String? {
    if (illegalList.isNotEmpty()) {
        val trs = mutableListOf<String>()
        illegalList.forEach {
            trs.add("""
 <tr>
    <td>${curTime()}</td>
    <td>${it.tbl}</td>
    <td>${it.analyse}</td>
    <td>${it.vId}</td>
    <td>${it.vUserId}</td>
    <td>${it.vContent}</td>
    <td>${it.deleteRs}</td>
  </tr>
""".trimIndent())
        }
        val ret = """
<table border="1">
  <tr>
    <th>检测时间</th>
    <th>库</th>
    <th>违规策略</th>
    <th>主键id</th>
    <th>用户id</th>
    <th>内容</th>
    <th>删除结果</th>
  </tr>
  ${trs.joinToString("")}
</table>
""".trimIndent()
        return ret
    }
    return null
}
fun curTime() = DateUtil.getCurrentDate_YMDHms()

fun requestQuestion(jdbc:JdbcTemplate,domain: String, qId: Long, vUserId: String): String {
    val param = String.format("questionId=%s&deletePeron=%s", qId, vUserId)
    var rs = requestWithsign("${domain}/delQuestion", param)
    if (CASCADE_DELETE && !rs.contains("\"status\":\"200\"")) {
        val aMap = jdbc.query("""
SELECT ID,A_USER_ID as USERID from ZHS_BBS.QA_ANSWER WHERE Q_ID = $qId and IS_DELETED = 0;
        """.trimIndent(), ColumnMapRowMapper())

        if (aMap.isNotEmpty()) {
            aMap.forEach {
                val aId = it["ID"].toString().toLong()
                val USERID = it["USERID"].toString()
                requestAnswer(jdbc,domain,aId, USERID)
            }

            rs = requestWithsign("${domain}/delQuestion", param)
        }
    }
    return rs
}
fun requestAnswer(jdbc:JdbcTemplate,domain: String, aId: Long, vUserId: String): String {
    val param = String.format("answerId=%s&deletePeron=%s", aId, vUserId)
    var rs = requestWithsign("${domain}/delAnswer", param)

    if (CASCADE_DELETE && !rs.contains("\"status\":\"200\"")) {
        val aMap = jdbc.query("""
SELECT ID,COMMENT_USER_ID  as USERID from ZHS_BBS.QA_COMMENT WHERE A_ID = $aId and IS_DELETED = 0;
        """.trimIndent(), ColumnMapRowMapper())

        if (aMap.isNotEmpty()) {
            aMap.forEach {
                val cId = it["ID"].toString().toLong()
                val USERID = it["USERID"].toString()
                requestComment(jdbc,domain,cId, USERID)
            }

            rs = requestWithsign("${domain}/delAnswer", param)
        }
    }
    return rs
}
fun requestComment(jdbc:JdbcTemplate,domain: String, cId: Long, vUserId: String): String {
    val param = String.format("commentId=%s&deletePeron=%s", cId, vUserId)
    val rs = requestWithsign("${domain}/delComment", param)
    return rs
}


fun main(args: Array<String>) {
//    val env = Env.WAIWANG_ALL
    val qajdbc = Zhsutil.getJDBC(Env.WAIWANG_BBS)
    val onlineshooljdbc = Zhsutil.getJDBC(Env.WAIWANG_ONLINESCHOOL)

    val PAGE_SIZE = 100
    val INTERVAL: Long = if(DEBUG_MODE) 100 else 6
    val timeUnit = if(DEBUG_MODE) TimeUnit.MILLISECONDS else TimeUnit.HOURS
    val q  = UserIdQueryer()

//    "userId=%d&page=%d&pageSize=%d"
    request("http://114.55.26.161:9080/courseqa/testDeploy.jsp", "?")

    val pool = Executors.newScheduledThreadPool(3)
    val werror: (Int, String, String, MutableMap<Any?, Any?>) -> Unit = {
        type, id, content, mutableMap
        ->
        System.err.println("$type $id $content")
        if (DELETE_OPEN) {
            when (type) {
                1->requestQuestion(qajdbc, domain, id.toLong(), mutableMap["CREATOR"].toString())
                2->requestAnswer(qajdbc, domain, id.toLong(), mutableMap["CREATOR"].toString())
                3->requestComment(qajdbc, domain, id.toLong(), mutableMap["CREATOR"].toString())
            }
        }
    }

    pool.scheduleAtFixedRate(BaseGreenWork(qajdbc,PAGE_SIZE,"ZHS_BBS.QA_QUESTION","QUESTION_ID","CONTENT","CREATE_USER",{ vId, vContent, vUserId ->
        val rs = requestQuestion(qajdbc, domain, vId, vUserId)
//        queryByGroup(onlineshooljdbc,qajdbc, q,"问题组", linkedSetOf(vUserId.toInt()),false, werror)
        return@BaseGreenWork rs
    }), 0, INTERVAL, timeUnit)
    pool.scheduleAtFixedRate(BaseGreenWork(qajdbc,PAGE_SIZE,"ZHS_BBS.QA_ANSWER","ID","A_CONTENT","A_USER_ID",{ vId, vContent, vUserId ->
        val rs = requestAnswer(qajdbc, domain, vId, vUserId)
//        queryByGroup(onlineshooljdbc,qajdbc, q,"回答组", linkedSetOf(vUserId.toInt()),false, werror)
        return@BaseGreenWork rs
    }), 0, INTERVAL, timeUnit)
    pool.scheduleAtFixedRate(BaseGreenWork(qajdbc,PAGE_SIZE,"ZHS_BBS.QA_COMMENT","ID","COMMENT_CONTENT","COMMENT_USER_ID",{ vId, vContent, vUserId ->
        val rs = requestComment(qajdbc, domain, vId, vUserId)
//        queryByGroup(onlineshooljdbc,qajdbc, q,"评论组", linkedSetOf(vUserId.toInt()),false, werror)
        return@BaseGreenWork rs
    }), 0, INTERVAL, timeUnit)


    if (!DEBUG_MODE && SEND_MAIL) {
        debug("发送邮件线程已启动..."+ curTime())
        pool.scheduleAtFixedRate(SendMailWork(), 0, 1, TimeUnit.MINUTES)
    }

}
private fun requestWithsign(url: String, param: String): String {
    return request(url, param+ContentAnalyser.signParam(param))
}

private fun request(url: String, param: String): String {
    if (!DELETE_OPEN) {
        return "未开启删除"
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
        println("$text ${url}?${param}")
        return text!!
    } catch (e: Exception) {
        e.printStackTrace()
        return e.message!!
    }

}
val NO_NEXT_TODAY=TimeDto(-1,-1)
data class TimeDto(var hour:Int,var min:Int) {
    constructor(x: Int):this(x/100,x%100)
    constructor(x: String):this(x.toInt())

    init {
        adjust()
    }

    fun adjust() {
        if (min >= 60) {
            hour += (min - min%60)/60
            min %= 60
        }
    }
    fun getValue(): Int {
       return hour * 100 + min
    }

    operator fun compareTo(timeDto: TimeDto): Int {
        return getValue().compareTo(timeDto.getValue())
    }

    operator fun rangeTo(timeDto: TimeDto): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    operator fun plus(interval: Int): TimeDto {
        return TimeDto(hour, min+interval)
    }
}

//"0930-1200/30,1330-2100/60"
abstract class DailyWork(val timeStr:String):Runnable{
    var next:TimeDto?=null
    var today:Int?=null

    override fun run() {
        resetDate()

        if (checkTime()) {
            debug("checkTime success..${curTime()}/$timeStr")
            doWork()
        }
    }

    fun getToday():Int{
        val message = Calendar.getInstance()
        return message.get(Calendar.DATE)
    }

     fun resetDate(){
         if (today == null) {
             today = getToday()
         } else {
             if (getToday() != today) {
                 today = getToday()
                 caculateNext()
             }
         }
     }

    fun caculateNext(){
        val now = getNow()
        next = findNext(now)?:NO_NEXT_TODAY
        info("next work time:$next")
    }

     fun getNow(): TimeDto {
        val cur = Calendar.getInstance()
        return TimeDto(cur.get(Calendar.HOUR_OF_DAY),cur.get(Calendar.MINUTE))
    }

    fun findNext( timeDto: TimeDto):TimeDto? {
//        "0930-1200/30,1330-2100/60"
        val split = timeStr.split(",")
        split.forEach {
            val interval = it.substring(it.lastIndexOf("/") + 1).toInt()
            val betand = it.substring(0,it.lastIndexOf("/"))
            val start = betand.split("-")[0]
            val end = betand.split("-")[1]
            val sDto = TimeDto(start)
            val eDto = TimeDto(end)

            var st = sDto
            if (timeDto>= sDto && timeDto< eDto) {
                while (st < eDto) {
                    st += interval
                    if (st > timeDto && st <= eDto) {
                        return st
                    }
                }
            } else if (timeDto<sDto) {
                return sDto
            }
        }

        return null
    }

    abstract fun doWork()

    fun checkTime():Boolean {
        if (next == null) {
            caculateNext()
        }

        if(getNow()==next){
            caculateNext()
            return true
        }
        return false
    }
}



class SendMailWork:DailyWork(timeStr){
    var prevTs = System.currentTimeMillis()

    override fun doWork() {
        if (illegalList.isNotEmpty()) {
            debug("SendMailWork working....")

            val parseHtml = parseHtml()
            parseHtml?.let {
                if (EmailUtil.sendByJavaMail("问答广告内容检测(${DateUtil.getDate_yyyyMMddHHmmss(prevTs)}~${DateUtil.getDate_yyyyMMddHHmmss(System.currentTimeMillis())})", mailTo.joinToString(), it)) {
                    //success
                    illegalList.clear()
                    prevTs =  System.currentTimeMillis()
                }
            }

        }
    }
}

class BaseGreenWork(val jdbc: JdbcTemplate, val pagE_SIZE: Int, val tbl: String, val mainIdField: String, val contentField: String,val creatorField: String,val actionDelete: (vId:Long,vContent:String,vUserId:String)->String) :Runnable{
    var lastId:Long?=null
    var terminated = false
    override fun run() {
//        val env = Env.WAIWANG_ALL
//        val jdbc = Zhsutil.getJDBC(env)
        if (terminated) {
            return
        }

            debug("$tbl  "+ curTime())

        if (lastId==null) {
            info("$tbl  query lastId......")
//            DEBUG时取最新的 100 页 否则最后一页
            val OFFSET = if(DEBUG_MODE) {
                pagE_SIZE* DEBUG_PREV_PAGE
            } else pagE_SIZE

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

//            for test
//            lastId=1
            info("$tbl  initial lastId:$lastId")
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
//            debug(lists)

            if (lists.size>0) {
                val oldLastId = lastId
                lastId = lists[lists.lastIndex]["$mainIdField"].toString().toLong()
                debug("$tbl  $oldLastId -> $lastId ${curTime()}")

                lists.forEach {
                    val vId = it["$mainIdField"].toString().toLong()
                    val vContent = it["$contentField"].toString()
                    val vUserId = it["$creatorField"].toString()
//                    log("$vId $vContent")

                    val analyse = ContentAnalyser.analyse(vContent)
                    if (analyse!=AnaRs.OK) {
                        error("${curTime()} ${tbl} $analyse $vId user:$vUserId content:$vContent")

                        val deleteRs = actionDelete(vId, vContent, vUserId)
                        illegalList.add(IllegalQa(tbl,analyse,vId,vUserId,vContent,deleteRs))
                    }
                }
            } else {
                println("$tbl  $lastId ( newest...)")

                if (DEBUG_MODE)
                    terminated = true
            }
        }
    }


}