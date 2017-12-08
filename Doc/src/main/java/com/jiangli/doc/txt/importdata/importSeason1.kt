package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.txt.DB
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.lang.Exception
import java.util.*

val excel_season1 = excel {
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

    row("于海") {
        course("西方社会思想两千年", 2016130) {
            lesson("4.3.3、关于理性与情感的关系（正义源于“自私”）", 225619) {}
            lesson("4.3.5、社会秩序是怎么演化来的？（你变成今天的“自己”，是谁影响了你？）", 225625) {}
            lesson("4.2.2、为了自利而利他（“不脱缰”的自私，让你变成人生赢家）", 232695) {}
        }


        listen("正义源于“自私”", 346917) {}
        listen("你变成今天的“自己”，是谁影响了你？", 348099) {}
    }

    row("张鸣") {
        course("中国近代史新编", 2015572) {
            lesson("2.3、林则徐登场（林则徐登场）", 205241) {}
            lesson("4.10、清政府对于变法的态度（躺枪的光绪，背锅的袁世凯）", 205297) {}
            lesson("4.11、西方对于变法的态度（西太后的母子情仇）", 225513) {}
        }


        listen("林则徐--中国第一个睁眼看世界的人", 346961) {}
        listen("西太后的母子情仇", 346995) {}
    }


    row("张静") {
        course("中国古典诗词中的品格与修养", 2016195) {
            lesson("2.3.1、韦庄《菩萨蛮五首》其二（美景佳人亦难抵乡愁）", 231499) {}
            lesson("2.3.3、韦庄《菩萨蛮五首》其四（论“呵呵”的意境）", 231507) {}
            lesson("2.3.4、韦庄《菩萨蛮五首》其五（未改初心却未得始终）", 231511) {}
        }


        listen("美景佳人亦难抵乡愁", 347817) {}
        listen("论“呵呵”的意境", 347819) {}
    }


    row("葛剑雄") {
        course("中国历史地理概况", 2016128) {
            lesson("8.1、汉族的形成（汉民族为何“人多势众”）", 232027) {}
            lesson("10.2.5、婚丧节庆与民间信仰（守土一方的神仙，是怎样“养成”的?）", 204829) {}
            lesson("11.1.1、黄河（黄河为什么这么“任性”？）", 204831) {}
        }

        listen("汉民族为何“人多势众”", 346911) {}
        listen("守土一方的神仙，是怎样“养成”的?", 346941) {}
    }

    row("韩茂莉") {
        course("中国历史地理", 2015584) {
        }
    }


    row("朱孝远") {
        course("文艺复兴经典名著选读", 2015557) {
            lesson("3.1.1、但丁的生平（但丁：冰火两重天的人生）", 160189) {}
            lesson("3.2.1、《新生》的故事原型（但丁和他一个人的爱情）", 149279) {}
            lesson("3.4、但丁：文艺复兴的先驱（但丁的幸与不幸）", 158339) {}
        }

        listen("但丁：冰火两重天的人生", 347981) {}
        listen("但丁的幸与不幸", 346901) {}
    }


    row("王加") {
        course("世界著名博物馆艺术经典", 2015487) {
            lesson("4.3.1、“埃尔金大理石雕”引发的恩怨情仇（“埃尔金大理石雕”引发的恩怨情仇）", 232295) {}
            lesson("4.3.2、“一年一会”的女史箴图（被25英镑贱卖的中国国宝）", 232279) {}
            lesson("7.4、神秘的“瓦萨里长廊”（“回家的路”，住了1000位画家）", 232667) {}
        }

        listen("希腊骨肉分离的痛--埃尔金大理石雕", 347007) {}
        listen("《亡灵之书》——不是书的书", 346923) {}
    }

    row("李占才") {
        course("公共关系与人际交往能力", 2015481) {
            lesson("4.4.1、如何认识交往对象（黑格尔说：“看来世界上再也没有人理解黑格尔了！”）", 151207) {}
            lesson("4.4.2、慎友（交友需谨慎，且行且珍惜！）", 151209) {}
            lesson("4.6.2、表达应该注意的问题（你是个奔驰车，不要和手扶拖拉机置气啊！）", 151221) {}
        }
    }


    row("吴焕加") {
        course("外国建筑赏析", 2015527) {
            lesson("3.4、1851年伦敦水晶宫（“打酱油”的花匠，和他的伦敦水晶宫）", 234201) {}
            lesson("2.1.2、哥特式建筑特色-尖拱、半圆拱、飞扶壁（小石头堆起来的鸟笼）", 234193) {}
            lesson("7.3.1、“朗香教堂”何以让人产生深刻印象（惊艳时光的朗香教堂）", 227161) {}
        }

        listen("“打酱油”的花匠和他的伦敦水晶宫", 349813) {}
        listen("金字塔是怎么红起来的", 347147) {}
    }

    row("丁方") {
        course("丝绸之路文明启示录", 2015515) {
            lesson("4.3、护法雄狮和孔雀磨光（站在西方巨人之肩上成就自己）", 206851) {}
            lesson("8.2、钱币的美学（印到钱上的，才敢叫“压倒一切的信仰”）", 233073) {}
            lesson("5.2、释迦牟尼的思想（佛祖不快乐）", 233093) {}
        }

        listen("站在西方巨人之肩上成就自己", 346925) {}
        listen("印到钱上的才敢叫“压倒一切的信仰”", 346933) {}
    }


    row("熊浩") {
        course("思辨与创新", 2016129) {
            lesson("2.4.3、说服要素：从众、稀缺、一致、权威（你不是被说服，而是败给了技巧）", 211631) {}
            lesson("1.2.2、当红楼梦遇到建筑心理学（大观园里的心机已经升级，亭台楼阁的心思你别猜）", 202495) {}
            lesson("2.4.1、主观证明与客观证明（去哪里和与谁同行，哪个更重要）", 231865) {}
        }

        listen("书法在开发iPhone的过程中的作用", 351931) {}
        listen("大观园在建造亭子时隐藏着怎样的小心机？", 351929) {}
    }


    row("毛利华") {
        course("探索心理学的奥秘", 2016198) {
            lesson("8.2、情绪的表达（情绪的表达也许是写在我们的基因里）", 232445) {}
            lesson("8.3、情绪的功能（任性有理？听从情绪的召唤）", 260341) {}
            lesson("3.2、解决方案：争斗（上）（我们天生善于攻击）", 232345) {}
        }

        listen("情绪的表达也许是写在我们的基因里", 346931) {}
        listen("我们天生善于攻击", 346999) {}
    }

    row("黄洋") {
        course("古希腊文明", 2015714) {
            lesson("2.1.2、城邦的生活方式（热爱公共活动的古希腊男人们）", 232543) {}
            lesson("2.3、“荷马社会”（荷马史诗，是“诗”）", 232547) {}
            lesson("3.3.5、雅典民主政治的运作机制：官僚制度和监察制度（回到希腊当将军）", 232573) {}
        }

        listen("热爱公共活动的古希腊男人们", 346769) {}
        listen("男女之间那是婚姻，兄弟之间才是真正的爱情", 346891) {}
    }

}


fun main(args: Array<String>) {
    val excel  = excel_season1
    val error:(Any) -> Unit = System.err::println

    //
    val waiwang = DB.getJDBCForWaiWang()
//    val waiwang2C = DB.getJDBCFor2CWaiWang()

//    val targetDB = DB.getJDBCForYanFa()
//    val targetDB = DB.getJDBCForYuFa()
    val targetDB = DB.getJDBCForTHWaiWang()

    val concernFile = File("C:\\Users\\DELL-13\\Desktop\\concerns_s1.sql")
    val ofw = BufferedWriter(OutputStreamWriter(FileOutputStream(concernFile)))

    val totalWonderVideos = LinkedHashMap<String, ArrayList<Lesson>>()

    val sortMap = LinkedHashMap<String, Int>()
    sortMap.put("冯玮", 1)
    sortMap.put("葛剑雄", 2)
    sortMap.put("吴焕加", 3)
    sortMap.put("熊浩", 4)
    sortMap.put("毛利华", 5)

    val courseIdMap = LinkedHashMap<String, List<Long>>()


    val userName2UserId = HashMap<String, Int>() //外网
//    特殊情况
    userName2UserId.put("王加",160168971)

    val userId2TeacherId = HashMap<Int, Int>() //
    val userNameToInfoMap = LinkedHashMap<String,Map<String, ArrayList<String>>>()

    //必须查外网
    excel.rows.forEach {
        val excelTeaName = it.name
        var userId = -1

        //查询userId
        it.courses.forEach {
//            println(it.courseName)

            val tbl_mp = waiwang.queryForObject("select NAME,MAJOR_SPEAKER,TEACHER_NAME from db_G2S_OnlineSchool.TBL_COURSE WHERE COURSE_ID="+it.courseId, ColumnMapRowMapper())
//            println(tbl_mp)

            val db_teacher = tbl_mp["TEACHER_NAME"] as String

            userId = tbl_mp["MAJOR_SPEAKER"] as Int

            val user_mp = waiwang.queryForObject("select REAL_NAME from db_G2S_OnlineSchool.TBL_USER WHERE ID="+userId, ColumnMapRowMapper())
            val db_username = user_mp["REAL_NAME"] as String
            if (db_username!=excelTeaName) {
                userId = -1
            }

            if(userId == -1)
                error("找不到用户id for $excelTeaName, ${it.courseId}——${it.courseName} ,TBL_COURSE.TEACHER_NAME:$db_teacher,TBL_COURSE.MAJOR_SPEAKER:${tbl_mp["MAJOR_SPEAKER"]},TBL_USER.REAL_NAME:${db_username}")

            userName2UserId.putIfAbsent(excelTeaName!!,userId)
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
    val imgCarouselInfoPath = PathUtil.buildPath(base, "img方.txt")

//    val file = File(baseinfoPath)
//    println(file.absolutePath)
//    println(file.exists())

    //基本信息
    val teacherBaseInfoList = parseTxt(File(baseinfoPath), "：")

    teacherBaseInfoList.forEach {
        userNameToInfoMap.put(it["姓名"]!![0],it)
    }


    //合并背景图
    val imgList = parseTxt(File(imginfoPath), ":")
    imgList.forEach {
        it.entries.forEach {
            val map = userNameToInfoMap[it.key] as MutableMap
            map.put("背景图",it.value)
        }
    }
    //合并轮播图  正方形
    val imgCarList = parseTxt(File(imgCarouselInfoPath), ":")
    imgCarList.forEach {
        it.entries.forEach {
            val map = userNameToInfoMap[it.key] as MutableMap
            map.put("轮播图",it.value)
        }
    }


    //合并userId
    userName2UserId.entries.forEach {
        val map = userNameToInfoMap[it.key] as MutableMap

        map.put("用户ID", arrayListOf(it.value.toString()))
    }

    //合并userId
    sortMap.entries.forEach {
        val map = userNameToInfoMap[it.key] as MutableMap

        map.put("排序", arrayListOf(it.value.toString()))
    }

//    println(userNameToInfoMap)
//    println(teacherBaseInfoList)
//    println(imgList)


    println("----------教师基本信息TH_TEACHER--------------")
    teacherBaseInfoList.forEach {
        val sql= "insert into db_teacher_home.TH_TEACHER(NAME,USER_ID,SCHOOL,TITLE,ACADEMIC,IMG,CAROUSEL_IMG,SORT,SRC) values(" +
                "'${it["姓名"]!![0]}'" +
                ",${it.get("用户ID")?.get(0)?: "-1"}" +
                ",'${it["学校"]!![0]}'" +
                ",'${it["职称"]!![0]}'" +
                ",'${it["学术方向"]!![0]}'" +
                ",'${it.get("背景图")?.get(0)?: ""}'" +
                ",'${it.get("轮播图")?.get(0)?: ""}'" +
                ",${it.get("排序")?.get(0)?: "null"}" +
                ",1" +
                ");"

        println(sql)
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
//    println(userId2TeacherId)

    //userId2TeacherId  依赖
    //userId2TeacherId  依赖
    println("----------个人荣誉 TH_PERSONAL_GLORY--------------")
    userNameToInfoMap.entries.forEach {
        (userName,map) ->
            var userId = userName2UserId.get(userName)?:-1
            val teacherId = userId2TeacherId.get(userId)?:-1
            map["个人荣誉"]?.forEach {
                val sql= "insert into db_teacher_home.TH_PERSONAL_GLORY(TEACHER_ID,USER_ID,PERSONAL_GLORY_NAME) values(" +
                        "${teacherId}" +
                        ",${userId}" +
                        ",'${it}'" +
                        ");"
                println(sql)

            }
    }

    println("----------风采视频TH_WONDER_VIDEO--------------")
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
                when (arrl) {
                    2 -> {
                        var  lOrLv_mp = waiwang.queryForObject("select ID as LESSON_ID from db_G2S_OnlineSchool.CC_LESSON WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
                        LESSON_ID = lOrLv_mp["LESSON_ID"].toString().toInt()
                    }
                    3 -> {
                        var  lOrLv_mp = waiwang.queryForObject("select ID as SMALL_LESSON_ID ,LESSON_ID  from db_G2S_OnlineSchool.CC_LESSON_VIDEO  WHERE COURSE_ID = ${it.courseId} AND VIDEO_ID=$videoId", ColumnMapRowMapper())
                        LESSON_ID = lOrLv_mp["LESSON_ID"].toString().toInt()
                        SMALL_LESSON_ID = lOrLv_mp["SMALL_LESSON_ID"].toString().toInt()
                    }
                    else -> {
                        println("未知序号 $chapterIndex 数组长度:$arrl")
                    }
                }
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

    println("----------语录 TH_WONDER_VIDEO--------------")
    //必须查外网
    excel.rows.forEach {
        val teacherName = it.name
        var userId = userName2UserId.get(teacherName)?:-1
        val teacherId = userId2TeacherId.get(userId)?:-1

        it.listens.forEach {
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

    println("----------关注 TH_CONCERN--------------")
    println(concernFile)
    //必须查外网
    excel.rows.forEach {
        val teacherName = it.name
        var userId = userName2UserId.get(teacherName)?:-1
        val teacherId = userId2TeacherId.get(userId)?:-1

//        it.courses.forEach {
            if (userId.toString()=="115730") {
                val courseId = it.courses[0].courseId

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
            }


//        }
//        println(wonderList)
    }


    println("----------更新 TH_TEACHER--------------")

//    println(imgCarList)
    imgCarList.forEach {
        it.entries.forEach {
            val teacherName = it.key
            var userId = userName2UserId.get(teacherName)?:-1
            val teacherId = userId2TeacherId.get(userId)?:-1
//                val map = userNameToInfoMap[it.key] as MutableMap

            val sql= "update db_teacher_home.TH_TEACHER set CAROUSEL_IMG='${it.value[0]}' where ID='${teacherId}';"

                println(sql)
        }
    }
}



//js 内部上传组件
/**

$("#sinfo tr").each(function(i,e) {
var $e = $(e);
var txt1 = $e.find("td:eq(0)").text()
txt1=txt1.substring(0,txt1.length-".jpg".length)
//    txt1=txt1.replace(".jpg","")
//    txt1=txt1.replace(".jpeg","")
var href = $e.find("td a").attr("href")
console.log(`${txt1}:${href}`)
});

 ***/



