package com.jiangli.doc.sql.helper.aries.course

import com.jiangli.common.utils.HttpPostUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import net.sf.json.JSONObject
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 10:17
 */
fun main(args: Array<String>) {
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val reqUrl =  if (env == Env.WAIWANG)
        "http://base-video.g2s.cn/transEncryptM3u8One"
     else
        "http://120.92.172.209:8088/transEncryptM3u8One"

//    zhengshi
//    AND IS_PUBLISH=1
    val play_domain = "http://aries-video.g2s.cn/"

//    val reconvert = true
    val reconvert = false

//    AND UPDATE_TIME < '2020-06-30 12:00:00'
    val coureseteacherlist = jdbc.query("""
SELECT v.ID,v.NAME,v.STORAGE,v.M3U8_STORAGE,v.CREATE_TIME,v.BUSI_SRC,vt.ID,vt.JOB,vc.* FROm
    db_aries_base.TBL_VIDEO v
        LEFT JOIN db_aries_base.TBL_VIDEO_TASK vt ON v.ID = vt.VIDEO_ID AND vt.STATUS=1 AND vt.SPEC=101
        LEFT JOIN db_aries_base.TBL_VIDEO_CONVERT vc ON vc.TASK_ID = vt.ID
WHERE
        v.TYPE=1
  AND v.IS_DELETED=0
  AND v.BUSI_SRC=0
AND vc.ID IS NULL
;
            """.trimIndent(), ColumnMapRowMapper())

    var vm = toMap(coureseteacherlist, { it->it["ID"] .toString() },{ it->it["M3U8_STORAGE"] .toString()})

    println("过滤前:"+vm.size)

//    vm过滤掉拉不到地址的
        var c = 0
        var t = 0
        val vmIter = vm.iterator()
        while (vmIter.hasNext()) {
            t++

            val (t, u) = vmIter.next()
            val play_url = "${play_domain}${u}"

            val rs = HttpPostUtil.getUrlRequest("""
    $play_url
    """.trimIndent(),  JSONObject())
            if (rs != null ) {
                vmIter.remove()
            } else {
                c++
                println("${c} : ${t}/${vm.size} 拉不到地址 $t $u")
            }
        }
        println("拉不到地址的:"+vm.size)

//    return
    if (reconvert) {
        //    请求转码
        vm.keys.forEach {
            val VIDEO_ID = it
//        val VIDEO_ID = it["VIDEO_ID"] .toString()
            println(VIDEO_ID)

            val jsonObject = JSONObject()
            jsonObject.put("videoId",VIDEO_ID)
            if (env == Env.WAIWANG) {
                jsonObject.put("syncWrite","false")
            }
            val rs = HttpPostUtil.postUrl("""
$reqUrl
                """.trimIndent(), jsonObject)

            println(rs)
        }
    }


    var vm2:MutableMap<String, String>? = null

    //    重新获取m3u8地址
    while(vm.keys.size > 0){
        val requerylist = jdbc.query("""
SELECT * FROM
    db_aries_base.TBL_VIDEO WHERE ID
IN (${vm.keys.joinToString(",")})
;
            """.trimIndent(), ColumnMapRowMapper())

        vm2 = toMap(requerylist, { it->it["ID"] .toString() },{ it->it["M3U8_STORAGE"] .toString()})
        println(vm2)

        if ( !reconvert || isTotallyDifferent(vm,vm2)) {
            break
        }

        Thread.sleep(500)
    }

    println("同步完毕")



    var failedNum = mutableListOf<Pair<String,String>>()
    vm2?.forEach { t, u ->
        failedNum.add(t to u )
    }

    var times = 0
    while(true){
        val iterator = failedNum.iterator()
        while (iterator.hasNext()) {
            val (t, u) = iterator.next()

            val play_url = "${play_domain}${u}"
            println("$t :  $play_url")

            val rs = HttpPostUtil.getUrlRequest("""
    $play_url
    """.trimIndent(),  JSONObject())
            if (rs != null ) {
                iterator.remove()
            }
        }

        times++
        println("${times} failedNum:${failedNum.size}/${vm.size}  $failedNum")
        if (failedNum.isEmpty() ) {
            break
        }

        Thread.sleep(10000)
    }
}

