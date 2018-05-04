package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.txt.DB
import com.jiangli.doc.txt.excel.parseExcel
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import java.util.*

/**
 * Created by Jiangli on 2017/8/3.
 */

fun main(args: Array<String>) {
    val base = "C:\\Users\\DELL-13\\Desktop\\codeReview\\教师主页\\教师主页四期汇总"
//    val excel  = parseExcel(File(PathUtil.buildPath(base, "教师主页 ID 汇总0908.xlsx")))
    val excel  = parseExcel(File(PathUtil.buildPath(base, "教师主页ID汇总.xlsx")))


        val CURRENT_ENV = Env.DEV
//    val CURRENT_ENV = Env.YUFA
//    val CURRENT_ENV = Env.WAIWANG

//    val INSERT_CONCERN = false
    val INSERT_CONCERN = true
    val EXCEL_TXT_NAMES_SYNC = true  //若为false txt可能名称多于excel

    val configMap = getConfig()

    val targetDB = configMap[CURRENT_ENV]!!.jdbc
    val HOST = configMap[CURRENT_ENV]!!.host

//    特殊情况
    val userId2TeacherId = HashMap<Int, Int>() //

    //张三 -> [{lesson1},{lesson2}]
    val totalWonderVideos = getWonderVideoList(excel)
    println(totalWonderVideos)

    //!!query
    //张三 -> 12345
    val userName2UserId = queryUserId(excel)
//    userName2UserId.put("王加",160168971)
//    userName2UserId.put("修娜",167270513)
    println(userName2UserId)


    //张三 -> {baseInfo}
    var userNameToInfoMap = getBaseInfoMap(base, "中国海洋大学老师个人信息荣誉等.txt")
    //同步excel的名字
    if (EXCEL_TXT_NAMES_SYNC) {
        val list = excel.rows.map { it.name }
        val map = userNameToInfoMap.filter {
            (k) ->
            list.contains(k)
        }
        userNameToInfoMap = map as LinkedHashMap<String, Map<String, ArrayList<String>>>
    }

    //合并userId
    mergeToBaseMap(userName2UserId, userNameToInfoMap, "用户ID")

    //合并排序
    val sortMap = LinkedHashMap<String, Int>()
    //    sortMap.put("冯玮", 1)
//    sortMap.put("葛剑雄", 2)
//    sortMap.put("吴焕加", 3)
//    sortMap.put("熊浩", 4)
//    sortMap.put("毛利华", 5)
    mergeToBaseMap(sortMap, userNameToInfoMap, "排序")


    //合并背景图
    val imgList = parseTxt(File(PathUtil.buildPath(base, "教师图片.txt")), "：")
    mergeToBaseMap(imgList, userNameToInfoMap, "背景图")

    //合并背景图
    val imgCarList = parseTxt(File(PathUtil.buildPath(base, "教师图片（方形）.txt")), "：")
    mergeToBaseMap(imgCarList, userNameToInfoMap, "轮播图")


    //check
    userNameToInfoMap.forEach { t, u ->
        val list = u["用户ID"]
        if(list == null || list.size == 0 || list[0] == "-1")
            error("教师 $t  的 userId不合法")
    }
    println(userNameToInfoMap)

    val s_SRC = 4

    println("----------教师基本信息TH_TEACHER--------------")
    userNameToInfoMap.forEach {
        val name = it.key
        val value = it.value
        val userId = value.get("用户ID")?.get(0) ?: "-1"

        try {
            val s_NAME = "'${value["姓名"]!![0]}'"
            val s_SCHOOL = "'${value["学校"]!![0]}'"
            val s_JOB = "'${value["职称"]!![0]}'"
            val s_DIRECT = "'${value.get("学术方向")?.get(0) ?: ""}'"
            val s_BAK = "'${value.get("背景图")?.get(0) ?: ""}'"
            val s_CAROL = "'${value.get("轮播图")?.get(0) ?: ""}'"
            val s_SORT = value.get("排序")?.get(0) ?: "null"

            val s_STATUS = 1

            val insert_sql= "insert into db_teacher_home.TH_TEACHER(NAME,USER_ID,SCHOOL,TITLE,ACADEMIC,IMG,CAROUSEL_IMG,SORT,SRC,STATUS) values(" +
                    s_NAME +
                    ",$userId" +
                    ",$s_SCHOOL" +
                    ",$s_JOB" +
                    ",$s_DIRECT" +
                    ",$s_BAK" +
                    ",$s_CAROL" +
                    ",$s_SORT" +
                    ",$s_SRC" +
                    ",$s_STATUS" +
                    ");"


            val query = targetDB.query("select ID from db_teacher_home.TH_TEACHER where USER_ID = $userId", ColumnMapRowMapper())
            if ( userId == "-1") {
                error(insert_sql)
            } else {
                if (query.size > 0 ) {
                    var CUR_ID = query[0]["ID"]

                    val insert_sql= "update db_teacher_home.TH_TEACHER set"+
                        " name=$s_NAME"+
                        " ,IS_DELETED=0"+
                        " ,SCHOOL=$s_SCHOOL"+
                        " ,TITLE=$s_JOB"+
                        " ,ACADEMIC=$s_DIRECT"+
                        " ,IMG=$s_BAK"+
                        " ,CAROUSEL_IMG=$s_CAROL"+
                        " ,SORT=$s_SORT"+
                        " ,SRC=$s_SRC"+
                        " ,USER_ID=$userId"+
                        " ,STATUS=$s_STATUS"+
                        " where ID=$CUR_ID ;"

                    println(insert_sql)
                } else {
                    println(insert_sql)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
            error("$name 出现问题")
        }
    }

    ////////////////////////////////上面需先执行
    println("##----------redis UserId转码--------------;")
    userName2UserId.entries.forEach {
        (userName, userId) ->
        val uuid = convertUUID(userId)
        println("set user:uuid:$uuid $userId")
    }
    userName2UserId.entries.forEach {
        (userName, userId) ->
        val uuid =  convertUUID(userId)
//        println("$userName $userId http://$HOST/teacherhome/share/home?uuid=$uuid&sourceType=appteacher&sourceUUID=1791b30c0c5db69ed41f2db4c1ec5076&isShare=1")
        println("$userName $userId http://teacherhome.zhihuishu.com/teacherhome/share/home?uuid=$uuid&sourceType=appteacher&sourceUUID=1791b30c0c5db69ed41f2db4c1ec5076&isShare=1")
    }

    //查询userId对应的teacherId
    userName2UserId.entries.forEach {
        (userName, userId) ->

        val query = targetDB.query("select ID from db_teacher_home.TH_TEACHER where USER_ID = $userId", ColumnMapRowMapper())
        if (query.size > 1) {
            error("$userName,$userId 对应了多条TH_TEACHER记录 $query")
        } else if (query.size == 1){
            userId2TeacherId.put(userId,query[0]["ID"] as Int)
        }else {
            error("$userName,$userId 对应0条TH_TEACHER记录")
        }
    }
    println(userId2TeacherId)

    //userId2TeacherId  依赖
    //userId2TeacherId  依赖
    println("##----------个人荣誉TH_PERSONAL_GLORY--------------;")
    userNameToInfoMap.entries.forEach {
        (userName,map) ->
            var userId = userName2UserId.get(userName)?:-1
            val teacherId = userId2TeacherId.get(userId)?:-1

           //先删除已存在的
        removeIfExist(targetDB, "TH_PERSONAL_GLORY", "TEACHER_ID", teacherId)

           //倒序
            map["个人荣誉"]?.reversed()?.forEach {
                val sql= "insert into db_teacher_home.TH_PERSONAL_GLORY(TEACHER_ID,USER_ID,PERSONAL_GLORY_NAME) values(" +
                        "${teacherId}" +
                        ",${userId}" +
                        ",'${replaceLastSymbol(it)}'" +
                        ");"
                println(sql)

            }
    }



    println("##----------风采视频TH_WONDER_VIDEO--------------;")
    totalWonderVideos.entries.forEach {
        val teacherName = it.key
        var userId = userName2UserId.get(teacherName) ?: -1
        val teacherId = userId2TeacherId.get(userId) ?: -1

        //先删除已存在的
        removeIfExist(targetDB, "TH_WONDER_VIDEO", "TEACHER_ID", teacherId)
    }

    val waiwang = DB.getJDBCForWaiWang()
    totalWonderVideos.entries.forEach {
        val teacherName = it.key
        var userId = userName2UserId.get(teacherName)?:-1
        val teacherId = userId2TeacherId.get(userId)?:-1

        it.value.forEach {
            val videoId = it.videoId
            val index = it.index
            val lessonName = it.index?.substringAfter("、")
            val chapterIndex = it.index?.substringBefore("、")?:""
            val arrl = chapterIndex.split(".").size

            var LESSON_ID:Int?=null
            var SMALL_LESSON_ID:Int?=null

            //节
            try {
                var  lOrLv_mp = waiwang.query("select ID as SMALL_LESSON_ID ,LESSON_ID  from db_G2S_OnlineSchool.CC_LESSON_VIDEO  WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
//                println(lOrLv_mp)
                if (lOrLv_mp.isNotEmpty()) {
                    LESSON_ID = lOrLv_mp[0]["LESSON_ID"].toString().toInt()
                    SMALL_LESSON_ID = lOrLv_mp[0]["SMALL_LESSON_ID"].toString().toInt()
                } else {
                    lOrLv_mp = waiwang.query("select ID as LESSON_ID from db_G2S_OnlineSchool.CC_LESSON WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
//                    println(lOrLv_mp)

                    if (lOrLv_mp.isNotEmpty()) {
                        LESSON_ID = lOrLv_mp[0]["LESSON_ID"].toString().toInt()
                    } else {
                        error("#未知LESSON_ID,SMALL_LESSON_ID, COURSE_ID=${it.courseId} VIDEO_ID=$videoId")
                    }
                }

//                LESSON_ID = lOrLv_mp["LESSON_ID"].toString().toInt()

//                when (arrl) {
//                    2 -> {
//                        var  lOrLv_mp = waiwang.queryForObject("select ID as LESSON_ID from db_G2S_OnlineSchool.CC_LESSON WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
//                        LESSON_ID = lOrLv_mp["LESSON_ID"].toString().toInt()
//                    }
//                    3 -> {
//                        var  lOrLv_mp = waiwang.queryForObject("select ID as SMALL_LESSON_ID ,LESSON_ID  from db_G2S_OnlineSchool.CC_LESSON_VIDEO  WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
//                        LESSON_ID = lOrLv_mp["LESSON_ID"].toString().toInt()
//                        SMALL_LESSON_ID = lOrLv_mp["SMALL_LESSON_ID"].toString().toInt()
//                    }
//                    else -> {
////                        println("#未知序号 $chapterIndex 数组长度:$arrl")
//                    }
//                }
            } catch(e: Exception) {
                e.printStackTrace()

                println(chapterIndex)
                println(arrl)
            }

            val sql= "insert into db_teacher_home.TH_WONDER_VIDEO(TEACHER_ID,USER_ID,COURSE_ID,LESSON_ID,SMALL_LESSON_ID,NAME,VIDEO_ID) values(" +
                    "${teacherId}" +
                    ",${userId}" +
                    ",${it.courseId}" +
                    ",${LESSON_ID}" +
                    ",${SMALL_LESSON_ID}" +
                    ",'${lessonName}'" +
                    ",${videoId}" +
                    ");"

            println(sql)
        }
    }


    println("##----------语录TH_WONDER_VIDEO--------------;")
    excel.rows.forEach {
        val teacherName = it.name
        var userId = userName2UserId.get(teacherName)?:-1
        val teacherId = userId2TeacherId.get(userId)?:-1

        //先删除已存在的
        removeIfExist(targetDB, "TH_QUOTATIONS", "TEACHER_ID", teacherId)

        it.listens.reversed().forEach {
//        it.listens.forEach {
            val sql= "insert into db_teacher_home.TH_QUOTATIONS(TEACHER_ID,USER_ID,TITLE,RECORD_ID) values(" +
                    "${teacherId}" +
                    ",${userId}" +
                    ",'${it.title}'" +
                    ",${it.keyId}" +
                    ");"

            println(sql)
        }
//        println(wonderList)
    }
//    return

    if (INSERT_CONCERN) {
        println("##----------关注 TH_CONCERN--------------;")
        val concernFile = File("$base\\concerns_s2.sql")
        val ofw = BufferedWriter(OutputStreamWriter(FileOutputStream(concernFile)))
        println(concernFile)
        //必须查外网
        excel.rows.forEach {
            val teacherName = it.name
            var userId = userName2UserId.get(teacherName)?:-1
            val teacherId = userId2TeacherId.get(userId)?:-1

            //先删除已存在的
            val forObject = targetDB.queryForObject("SELECT count(*) as COUNT FROM db_teacher_home.TH_CONCERN WHERE BY_CONCERN = $teacherId AND CONCERN_SORUCE=2", ColumnMapRowMapper())
            val i = forObject["COUNT"] as Long
            if (i > 0) {
    //                 error("TH_PERSONAL_GLORY已存在 $userName $teacherId ,需要删除已存在的")
                println("update  db_teacher_home.TH_CONCERN set IS_DELETED = 1 WHERE BY_CONCERN = $teacherId AND CONCERN_SORUCE=2 ;")
            }
    //        return@forEach

    //            if (userId.toString()=="115730") {
                    var  COURSE_IDS = waiwang.query("select DISTINCT( COURSE_ID)from db_G2S_OnlineSchool.V2_ASSISTANTS WHERE USER_ID=${userId} AND TYPE NOT in (4,5) AND IS_DELETE=0", ColumnMapRowMapper())

                    var in_str = "("
                    COURSE_IDS.forEach {
                        val COURSE_ID = it["COURSE_ID"]
                        in_str = in_str + COURSE_ID +","

                    }
                    in_str = in_str.substring(0,in_str.length - 1)
                    in_str = in_str  +")"

                    println("$teacherName $userId $in_str")
    //            var  STUDENT_IDS = waiwang.query("select DISTINCT STUDENT_ID from db_G2S_OnlineSchool.STUDENT WHERE COURSE_ID = ${courseId}", ColumnMapRowMapper())
                    var  STUDENT_IDS = waiwang.query("select DISTINCT STUDENT_ID from db_G2S_OnlineSchool.STUDENT WHERE COURSE_ID in ${in_str}", ColumnMapRowMapper())


                    STUDENT_IDS.forEach {
                        val STUDENT_ID = it["STUDENT_ID"]

                        val sql= "insert into db_teacher_home.TH_CONCERN(USER_ID,BY_CONCERN_USER_ID,BY_CONCERN,CONCERN_SORUCE) values(" +
                                "${STUDENT_ID}" +
                                ",${userId}" +
                                ",${teacherId}" +
                                ",2" +
                                ");"

                        ofw.write(sql)
                        ofw.write("\r\n")
                        ofw.flush()
        //                println(sql)
                    }
    //            }
    }

    }


    println("##----------REDIS--------------;")
    val teacherIds = userId2TeacherId.values
    val keys = arrayListOf<String>()
    teacherIds.forEach {
        keys.add("th:teacher:${it}") //教师单个缓存
//            keys.add("th:byConcernIds:${it}") //被关注人的teacherId 缓存300个
        keys.add("th:byConcernNum:${it}") //被关注数量
        keys.add("th:personalGloryIds:${it}") //个人荣耀 列表
        keys.add("th:wonderVideo:ids:${it}") //风采视频 列表
        keys.add("th:quotations:ids:${it}") //我的语录 列表
    }
    delKeysPage(keys)

    //名师缓存
    println("del th:openapi:teachers:remoteresult:src:1:ts")
    println("update TH_TEACHER set SRC=1 where SRC=$s_SRC;")
}