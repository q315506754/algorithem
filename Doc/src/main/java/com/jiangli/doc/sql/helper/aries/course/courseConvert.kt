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
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val reqUrl = "http://base-video.g2s.cn/transEncryptM3u8One"
//    val reqUrl = "http://120.92.138.210:20020/transEncryptM3u8One"

//    zhengshi
//    AND IS_PUBLISH=1

    val allCourses = jdbc.query("""
SELECT * FROM db_aries_course.tbl_course
WHERE
      IS_DELETED=0
AND CREATE_TIME>'1999-06-18 16:40:00'
;
         """.trimIndent(), ColumnMapRowMapper())
    println("共有发布课程:"+allCourses.size)

    val courses = arrayListOf(
            "-1"
//            ,"600201"
            ,"600344"
    )
//    val courses = arrayListOf(
//            allCourses.map { it->it["COURSE_ID"]?.toString()!! }.joinToString(",")
//    )

    val play_domain = "http://aries-video.g2s.cn/"
    val failedOnly = false
//    val failedOnly = true

//    val reconvert = true
    val reconvert = false

    val coureseteacherlist = jdbc.query("""
SELECT c.COURSE_ID,c.COURSE_NAME,cc.ID,cc.TITLE,cc.VIDEO_ID,v.M3U8_STORAGE FROm db_aries_course.tbl_course c
LEFT JOIN db_aries_course.tbl_chapter cc on c.COURSE_ID = cc.COURSE_ID
LEFT JOIN  db_aries_base.tbl_video v on v.ID = cc.VIDEO_ID
WHERE
cc.IS_DELETED = 0
AND cc.VIDEO_ID > 0
AND c.COURSE_ID in (${courses.joinToString(",")})
;
            """.trimIndent(), ColumnMapRowMapper())

    var vm = toMap(coureseteacherlist, { it->it["VIDEO_ID"] .toString() },{ it->it["M3U8_STORAGE"] .toString()})
    val v2n = toMap(coureseteacherlist, { it->it["VIDEO_ID"] .toString() },{ it->"${it["COURSE_ID"]}.${it["COURSE_NAME"]}.${it["TITLE"]} "})

    coureseteacherlist.forEach {
        println(it)
    }

    println("过滤前:"+vm.size)

//    vm过滤掉拉不到地址的
    if (failedOnly) {
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
    }

//    return
    if (reconvert) {
        //    请求转码
        vm.keys.forEach {
            val VIDEO_ID = it
//        val VIDEO_ID = it["VIDEO_ID"] .toString()
            println(VIDEO_ID)

            val jsonObject = JSONObject()
            jsonObject.put("videoId",VIDEO_ID)
            jsonObject.put("syncWrite","false")
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
    db_aries_base.tbl_video WHERE ID
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
            println("$t : ${v2n[t]} $play_url")

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

fun isTotallyDifferent(vm: MutableMap<String, String>, vm2: MutableMap<String, String>): Boolean {
    var sameNum = 0
    var diffNum = 0
    vm.forEach { t, u ->
        if (vm2[t] == u) {
            sameNum++
        } else {
            diffNum++
        }
    }
    println("sameNum:$sameNum diffNum:$diffNum")

    return sameNum == 0
}


fun toMap(coureseteacherlist: List<MutableMap<String, Any>>, key: (MutableMap<String, Any>)->String,value: (MutableMap<String, Any>)->String): MutableMap<String, String> {
    var ret = mutableMapOf<String,String>()

    coureseteacherlist.forEach {
        ret.put(key(it),value(it))
    }
    return ret
}
