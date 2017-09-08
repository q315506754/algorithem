package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.CommonUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.txt.DB
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*


val error:(Any) -> Unit = System.err::println
/**
 *
 *
 * @author Jiangli
 * @date 2017/9/8 13:31
 */

/**
 * Created by Jiangli on 2017/8/3.
 */
@DslMarker
annotation class Contex

@Contex
open class Base

class Excel: Base(){
    var rows = ArrayList<ExcelRow>()
}
class ExcelRow: Base(){
    var name:String?=null
    var courses= ArrayList<Course>()
    var listens= ArrayList<Listen>()
}

class Course: Base(){
    var courseId:Int? = null
    var courseName:String? = null
    var lessons= ArrayList<Lesson>()
}
class Lesson: Base(){
    var index:String? = null
    var videoId:Int? = null
    var courseId:Int? = null
}

class Listen: Base(){
    var keyId:Int? = null
    var title:String? = null
}

//
//fun <T: Tag> Tag.doInit(tag: T, init: T.() -> Unit): T {
//    tag.init()
//    children.add(tag)
//    return tag
//}

fun excel(init: Excel.() -> Unit): Excel = Excel().apply(init)
fun Excel.row(name:String, init : ExcelRow.() -> Unit){
    val excelRow = ExcelRow()
    excelRow.name=name
    rows.add(excelRow)
    excelRow.init()
}
fun ExcelRow.course(name:String, id:Int, init : Course.() -> Unit){
    val cs = Course()
    cs.courseName=name
    cs.courseId=id
    courses.add(cs)
    cs.init()
}
fun ExcelRow.listen(title:String, keyId:Int, init : Listen.() -> Unit){
    val cs = Listen()
    cs.title=title
    cs.keyId=keyId
    listens.add(cs)
    cs.init()
}

fun Course.lesson(name:String, id:Int, init: Lesson.() -> Unit ){
    val cs = Lesson()
    cs.index=name
    cs.videoId=id
    cs.courseId=courseId
    lessons.add(cs)
    cs.init()
}

//row("aaaaaaaaaaaaaaaa") {
//    course("bbbbbbbbbbbbbbbb", 111111) {
//        lesson("ccccccccccccc", 2222222) {}
//        lesson("ddddddddddddd", 333333333) {}
//    }
//    listen("eeeeeeeeeeeeeeeeee", 444444444) {}
//    listen("ffffffffffffffff", 5555555) {}
//    listen("ggggggggggggggggggggg", 666666666) {}
//}


 fun queryUserId(excel: Excel): HashMap<String, Int> {
    val userName2UserId = HashMap<String, Int>()
    val waiwang = DB.getJDBCForWaiWang()

    excel.rows.forEach {
        val excelTeaName = it.name
        var userId = -1

        //查询userId
        it.courses.forEach {
            //            println(it.courseName)

            val tbl_mp = waiwang.queryForObject("select NAME,MAJOR_SPEAKER,TEACHER_NAME from db_G2S_OnlineSchool.TBL_COURSE WHERE COURSE_ID=" + it.courseId, ColumnMapRowMapper())
//            println(tbl_mp)

            val db_teacher = tbl_mp["TEACHER_NAME"] as String

            userId = tbl_mp["MAJOR_SPEAKER"] as Int

            val user_mp = waiwang.queryForObject("select REAL_NAME from db_G2S_OnlineSchool.TBL_USER WHERE ID=" + userId, ColumnMapRowMapper())
            val db_username = user_mp["REAL_NAME"] as String
            if (db_username != excelTeaName) {
                userId = -1
            }

            if (userId == -1){
                val v2_userIds = waiwang.query("select DISTINCT va.USER_ID  from db_G2S_OnlineSchool.V2_ASSISTANTS va LEFT JOIN db_G2S_OnlineSchool.TBL_USER u ON va.USER_ID = u.ID WHERE COURSE_ID=${it.courseId} AND IS_DELETE = 0 AND u.REAL_NAME = '$excelTeaName'" , ColumnMapRowMapper())
                if (v2_userIds.size > 1) {
                    error("V2_ASSISTANTS 定位到了多个用户id  $v2_userIds for $excelTeaName")
                } else if (v2_userIds.size == 1) {
//                    println("V2_ASSISTANTS 定位到了用户id  $v2_userIds for $excelTeaName")
                    userId = (v2_userIds[0]["USER_ID"] as Long).toInt()
                } else {
                    error("V2_ASSISTANTS 定位不到userId for $excelTeaName")
                }
            }

            if (userId == -1)
                error("无法定位用户id for $excelTeaName, ${it.courseId}——${it.courseName} ,TBL_COURSE.TEACHER_NAME:$db_teacher,TBL_COURSE.MAJOR_SPEAKER:${tbl_mp["MAJOR_SPEAKER"]},TBL_USER.REAL_NAME:${db_username}")

            userName2UserId.putIfAbsent(excelTeaName!!, userId)
        }
    }
    return userName2UserId
}

 fun getWonderVideoList(excel: Excel):HashMap<String, ArrayList<Lesson>> {
    val totalWonderVideos = LinkedHashMap<String, ArrayList<Lesson>>()

    excel.rows.forEach {
        val excelTeaName = it.name
        var userId = -1

        //收集风采视频
        val size = it.courses.size
        val wonderList = ArrayList<Lesson>()

        if (size == 1) {
            it.courses[0].lessons.forEach {
                if (wonderList.size < 2)
                    wonderList.add(it)
            }
        } else if (size >= 2) {
            it.courses[0].lessons[0]?.let {
                wonderList.add(it)
            }

            it.courses[1].lessons[0]?.let {
                wonderList.add(it)
            }
        }
        totalWonderVideos.put(excelTeaName!!, wonderList)

//        println(wonderList)
    }

    return totalWonderVideos
}



 fun parseTxt(file: File, split: String): ArrayList<Map<String, ArrayList<String>>> {
    val isNotNull = CommonUtil::isStringNotNull
    val isNull = CommonUtil::isStringNull
    val n2E = CommonUtil::nullToEmpty
//    val repCommas:(s:String) -> String  = {
//
//    }

    val bf = BufferedReader(InputStreamReader(FileInputStream(file), "gbk"))

    val arrayList = ArrayList<Map<String, ArrayList<String>>>()
    var m = LinkedHashMap<String, ArrayList<String>>()

    var readLine = bf.readLine()
    var prevKey: String? = null
    while (readLine != null) {
//        println(readLine)
        val indexOf = readLine.indexOf(split)
//        if (indexOf <1) {
//            println("$readLine $indexOf")
//            readLine = bf.readLine()
//            continue
//        }

        val key = if (indexOf < 1) "" else n2E(readLine.substring(0, indexOf))
        val value = if (indexOf < 1) "" else n2E(readLine.substring(indexOf + 1, readLine.length))

//        println("$key -> $value")

        if (isNotNull(key)) {
            prevKey = key
        }

        if (isNotNull(value)) {

        }

        if (isNotNull(key) && isNotNull(value)) {
            if(!m.containsKey(key)){
            }else {
                m = LinkedHashMap<String, ArrayList<String>>()
            }

            m.putIfAbsent(key, ArrayList())
            val list = m[key]
            list!!.add(n2E(value))

        } else if (isNull(key) && isNotNull(readLine) && isNotNull(prevKey)) {
            //换行的情况
            m.putIfAbsent(prevKey!!, ArrayList())
            val list = m[prevKey]
            list!!.add(n2E(readLine))
        } else if (isNull(readLine)) {
            if (!m.isEmpty()) arrayList.add(m)

            m = LinkedHashMap<String, ArrayList<String>>()
        }

        readLine = bf.readLine()
    }

    arrayList.add(m)
    return arrayList
}


 fun mergeToBaseMap(input: HashMap<String, Int>, targetMap: LinkedHashMap<String, Map<String, ArrayList<String>>>, s: String) {
    input.entries.forEach {
        val map = targetMap[it.key] as MutableMap

        map.put(s, arrayListOf(it.value.toString()))
    }
}
 fun mergeToBaseMap(input:ArrayList<Map<String, ArrayList<String>>>, targetMap: LinkedHashMap<String, Map<String, ArrayList<String>>>, s: String) {
    input.forEach {
        it.entries.forEach {
            val map = targetMap[it.key] as MutableMap
            map.put(s,it.value)
        }
    }
}

 fun getBaseInfoMap(base: String, s: String): LinkedHashMap<String, Map<String, ArrayList<String>>> {
    val userNameToInfoMap = LinkedHashMap<String, Map<String, ArrayList<String>>>()
    val baseinfoPath = PathUtil.buildPath(base, s)
    //基本信息
    val teacherBaseInfoList = parseTxt(File(baseinfoPath), "：")
//    println(teacherBaseInfoList)

    teacherBaseInfoList.forEach {
        if (it.containsKey("姓名")) {
            userNameToInfoMap.put(it["姓名"]!![0], it)
        }
    }
    return userNameToInfoMap
}