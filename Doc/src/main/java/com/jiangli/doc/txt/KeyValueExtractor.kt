package com.jiangli.doc.txt

import com.jiangli.common.utils.CommonUtil
import com.jiangli.common.utils.PathUtil
import org.apache.commons.dbcp.BasicDataSource
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

/**
 * Created by Jiangli on 2017/8/3.
 */

class Excel{
    var rows =ArrayList<ExcelRow>()
}
class ExcelRow{
    var name:String?=null
    var courses=ArrayList<Course>()
    var listens=ArrayList<Listen>()
}

class Course{
     var courseId:Int? = null
     var courseName:String? = null
    var lessons=ArrayList<Lesson>()
}
class Lesson{
     var index:String? = null
     var videoId:Int? = null
     var courseId:Int? = null
}

class Listen{
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
fun Excel.row(name:String,init : ExcelRow.() -> Unit){
    val excelRow = ExcelRow()
    excelRow.name=name
    rows.add(excelRow)
    excelRow.init()
}
fun ExcelRow.course(name:String,id:Int,init : Course.() -> Unit){
    val cs = Course()
    cs.courseName=name
    cs.courseId=id
    courses.add(cs)
    cs.init()
}
fun ExcelRow.listen(title:String,keyId:Int,init : Listen.() -> Unit){
    val cs = Listen()
    cs.title=title
    cs.keyId=keyId
    listens.add(cs)
    cs.init()
}

fun Course.lesson(name:String,id:Int,init: Lesson.() -> Unit ){
    val cs = Lesson()
    cs.index=name
    cs.videoId=id
    cs.courseId=courseId
    lessons.add(cs)
    cs.init()
}

val excel = excel {
    row("冯玮") {
        course("过去一百年", 2015658) {
            lesson("3.3、中东和平进程 (尴尬握手迎来的和平“曙光”)", 233349) {}
            lesson("3.4、和平的代价（拉宾——谢幕依旧是英雄）", 227627) {}
            lesson("5.4、战争根源：历史•地缘•大国博弈（五十步笑百步的“正义”）", 233351) {}
        }
        course("在历史坐标上解析日本", 2015659) {
            lesson("7.5、“攀登新高山•1208”（珍珠港：“苦肉计”or“沉沙戟”？）", 162849) {}
            lesson("8.1、日本真的是“无条件投降”？（有条件的“无条件投降”）", 161711) {}
            lesson("8.2、保留天皇制：不认罪主要根源（“天皇制”如何“刀口求生”？）", 162851) {}
        }

        listen("尴尬握手迎来的和平“曙光”", 346839) {}
        listen("拉宾——谢幕依旧是英雄", 346989) {}

        listen("“无上神圣”的日本天皇", 346793) {}
        listen("“无条件投降”背后的条件", 348119) {}
    }
}


fun main(args: Array<String>) {
    val jdbcTemplate = getJDBC()

    val totalWonderVideos = LinkedHashMap<String, ArrayList<Lesson>>()

    val userName2UserId = HashMap<String, Int>()
    val UserId2TeacherId = HashMap<String, Int>()

//    println(list)
//    println(excel)

    excel.rows.forEach {
        val excelTeaName = it.name
        var userId = -1

        //查询userId
        it.courses.forEach {
            println(it.courseName)

            val tbl_mp = jdbcTemplate.queryForObject("select NAME,MAJOR_SPEAKER,TEACHER_NAME from db_G2S_OnlineSchool.TBL_COURSE WHERE COURSE_ID="+it.courseId, ColumnMapRowMapper())
//            println(tbl_mp)

            val db_teacher = tbl_mp["TEACHER_NAME"] as String
            if (excelTeaName==db_teacher) {
                userId = tbl_mp["MAJOR_SPEAKER"] as Int

                val user_mp = jdbcTemplate.queryForObject("select REAL_NAME from db_G2S_OnlineSchool.TBL_USER WHERE ID="+userId, ColumnMapRowMapper())
                val db_username = user_mp["REAL_NAME"] as String
                if (db_username!=excelTeaName) {
                    userId = -1
                }
            }

            userName2UserId.put(excelTeaName!!,userId)
        }


        //收集风采视频
        val size = it.courses.size
        val wonderList = ArrayList<Lesson>()

        if (size==1) {
            it.courses[0].lessons.forEach {
                if(wonderList.size < 2)
                    wonderList.add(it)
            }
        }else if(size >=2){
            it.courses[0].lessons[0]?.let {
                wonderList.add(it)
            }

            it.courses[1].lessons[0]?.let {
                wonderList.add(it)
            }
        }
        totalWonderVideos.put(excelTeaName!!,wonderList)

//        println(wonderList)
    }

    println(userName2UserId)


    val base = "C:\\Users\\DELL-13\\Desktop\\codeReview\\教师主页\\0803 教师端主页"

    val baseinfoPath = PathUtil.buildPath(base, "14.txt")
    val imginfoPath = PathUtil.buildPath(base, "img.txt")

//    val file = File(baseinfoPath)
//    println(file.absolutePath)
//    println(file.exists())

    val teacherBaseInfoList = parseTxt(File(baseinfoPath), "：")
    val teacherNameToInfoMap = LinkedHashMap<String,Map<String, ArrayList<String>>>()

    teacherBaseInfoList.forEach {
        teacherNameToInfoMap.put(it["姓名"]!![0],it)
    }


    //合并背景图
    val imgList = parseTxt(File(imginfoPath), ":")
    imgList.forEach {
        it.entries.forEach {
            val map = teacherNameToInfoMap[it.key] as MutableMap
            map.put("背景图",it.value)
        }
    }

    //合并userId
    userName2UserId.entries.forEach {
        val map = teacherNameToInfoMap[it.key] as MutableMap

        map.put("用户ID", arrayListOf(it.value.toString()))
    }

//    println(teacherNameToInfoMap)
    println(teacherBaseInfoList)
//    println(imgList)


    println("----------教师基本信息TH_TEACHER--------------")
    teacherBaseInfoList.forEach {
        val sql= "insert into db_teacher_home.TH_TEACHER(NAME,USER_ID,SCHOOL,TITLE,ACADEMIC,IMG,SRC) values(" +
                "'${it["姓名"]!![0]}'" +
                ",${it.get("用户ID")?.get(0)?: "-1"}" +
                ",'${it["学校"]!![0]}'" +
                ",'${it["职称"]!![0]}'" +
                ",'${it["学术方向"]!![0]}'" +
                ",'${it.get("背景图")?.get(0)?: ""}'" +
                ",1" +
                ");"

        println(sql)
    }

    println("----------风采视频TH_WONDER_VIDEO--------------")
    totalWonderVideos.entries.forEach {
        val teacherName = it.key
        var userId = userName2UserId.get(teacherName)?:-1

        it.value.forEach {
            val videoId = it.videoId
            val index = it.index
            val chapterIndex = index!!.substring(0, index!!.indexOf("、"))
            val arrl = chapterIndex.split("\\.").size

//            println(arrl)

            var LESSON_ID:Int?=null
            var SMALL_LESSON_ID:Int?=null

            //节
            when (arrl) {
                1 -> {
                    var  lOrLv_mp = jdbcTemplate.queryForObject("select ID as LESSON_ID from db_G2S_OnlineSchool.CC_LESSON WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
                    LESSON_ID = lOrLv_mp["LESSON_ID"].toString().toInt()
                }
                2 -> {
                    var  lOrLv_mp = jdbcTemplate.queryForObject("select ID,LESSON_ID as SMALL_LESSON_ID from db_G2S_OnlineSchool.CC_LESSON_VIDEO  WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
                    LESSON_ID = lOrLv_mp["LESSON_ID"].toString().toInt()
                    SMALL_LESSON_ID = lOrLv_mp["SMALL_LESSON_ID"].toString().toInt()
                }
                else -> {
                    println("未知序号 $chapterIndex")
                }
            }

            val sql= "insert into db_teacher_home.TH_WONDER_VIDEO(TEACHER_ID,USER_ID,COURSE_ID,LESSON_ID,SMALL_LESSON_ID,NAME,VIDEO_ID) values(" +
                    "-1" +
                    ",${userId}" +
                    ",${it.courseId}" +
                    ",${LESSON_ID}" +
                    ",${SMALL_LESSON_ID}" +
                    ",${videoId}" +
                    ");"

            println(sql)
        }
    }
}

private fun getJDBC(): JdbcTemplate {
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

//js
//$("#sinfo tr").each(function(i,e) {
//   var $e = $(e);
//    var txt1 = $e.find("td:eq(0)").text()
//    txt1=txt1.substring(0,txt1.length-".jpg".length)
////    txt1=txt1.replace(".jpg","")
////    txt1=txt1.replace(".jpeg","")
//    var href = $e.find("td a").attr("href")
//    console.log(`${txt1}:${href}`)
//});

private fun parseTxt(file: File, split: String): ArrayList<Map<String, ArrayList<String>>> {
    val isNotNull = CommonUtil::isStringNotNull
    val isNull = CommonUtil::isStringNull
    val n2E = CommonUtil::nullToEmpty
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

