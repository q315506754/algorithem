package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.MD5
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.txt.DB
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
val excel_season2 = excel {
    row("韩茂莉") {
        course("中国历史地理", 2015584) {
            lesson("史前文化的空间组合与地理选择", 224687) {}
            lesson("“九州与四海=中国+夷狄”", 224743) {}
        }
        listen("郡县制不是始于秦朝？这和说好的不太一样！", 377933) {}
        listen("起初人们是怎么想到畜牧的？", 377923) {}
        listen("山河界线何以成为地方割据的保护伞？", 377959) {}
    }
    row("刘勇强") {
        course("伟大的《红楼梦》", 2015488) {
            lesson("对人物心理的精细把握与揭示", 233749) {}
            lesson("言行失态和梦境反映人物潜意识", 233753) {}
        }
        listen("黛玉独特的“脸红”技能", 350383) {}
        listen("“外面的形容”如何出卖你的内心想法", 350373) {}
        listen("你真的读懂宝黛二人的心理了吗？", 350371) {}
    }

    row("丁宁") {
        course("世界著名博物馆艺术经典", 2015487) {
            lesson("扑朔迷离的《蒙娜丽莎》（蒙娜丽莎的眉毛去哪啦？）", 206189) {}
            lesson("《米罗岛的维纳斯》的由来", 233469) {}
        }
        listen("我希望创造奇迹！", 357113) {}
        listen("佛祖不开心", 375105) {}
    }
    row("徐凯文") {
        course("大学生心理健康", 2015524) {
            lesson("人有权决定自己的生死吗？（你以为你可以自杀么？）", 156867) {}
            lesson("网络时代大学生的心理危机（危机四伏的网络时代）", 156871) {}
        }
        listen("该出手时就出手", 375099) {}
        listen("是不是人人皆曰可杀便可杀？", 352907) {}
        listen("危机的网络时代", 350369) {}
    }
    row("叶朗") {
        course("艺术与审美", 2015490) {
            lesson("什么是人生境界", 112468) {}
            lesson("海伦·凯勒的故事", 111344) {}
        }
        listen("九百年前，北宋汴京城素面朝天的美", 385163) {}
        listen("我们头顶上的灿烂星空，依然放射着神圣光芒", 385161) {}
        listen("看见美，除了张开眼睛还要打开胸怀", 385159) {}
    }

    row("张荣明") {
        course("中华国学", 2015601) {
            lesson("商鞅与韩非", 177015) {}
            lesson("奖惩严明与富国强兵", 177005) {}
        }
        listen("智慧也分三六九等", 383929) {}
        listen("真人的世界是怎样的世界？", 383925) {}
        listen("庄子的可爱，你get到了吗？", 383915) {}
    }


    row("龚克") {
        course("生态文明", 2016543) {
            lesson("从生态危机到生态文明", 234549) {}
            lesson("历史地看问题", 234551) {}
        }
        listen("在自然面前，人类如何不颤抖？", 385037) {}
    }


    row("吴志成") {
        course("生态文明", 2016543) {
            lesson("什么是全球生态环境治理？", 234607) {}
            lesson("谁来治理？", 234609) {}
        }
        listen("生态环境由谁拯救",385041 ) {}
    }



    row("余新忠") {
        course("生态文明", 2016543) {
            lesson("霍乱与卫生运动", 226777) {}
            lesson("血吸虫病与血防运动", 234637) {}
        }
        listen("几场鼠疫，促进了近代中国的卫生建设", 385045) {}
        listen("中国人和血吸虫的战斗史", 385051) {}
    }




    row("陈军") {
        course("生态文明", 2016543) {
            lesson("煤炭和石油的利用", 226805) {}
            lesson("水电和海洋能的利用", 226809) {}
        }
        listen("减小代价缓解矛盾—资源节约与能源结构调整", 385059) {}
        listen("智能电网中“削峰填谷”的储能技术", 385067) {}
    }


    row("孙红文") {
        course("生态文明", 2016543) {
            lesson("化学与环境", 234661) {}
            lesson("有机污染物（上）", 234673) {}
        }
        listen("脆弱的环境圈层里，人类莫再踩着高跷起舞", 385069) {}
        listen("被人类“忽视”了100余年的神奇惊喜", 385073) {}
    }

    row("杨光明") {
        course("生态文明", 2016543) {
            lesson("化学与人类是什么关系", 234687) {}
            lesson("“化”说《天工开物》", 234689) {}
        }
        listen("绿色化学：彰显分子间和谐的“情感默契”", 385093) {}
        listen("化学发展与环境的友好共存—解铃还须系人", 385097) {}
    }

    row("原新") {
        course("生态文明", 2016543) {
            lesson("人类的发展和文明的进程", 234691) {}
            lesson("世界人口发展及人口环境关系认知的演进", 234697) {}
        }
        listen("认知的进步，是改变的前提", 385099) {}
        listen("资源环境与人口发展间的紧张关系，该作何解", 385115) {}
    }



    row("史学瀛") {
        course("生态文明", 2016543) {
            lesson("保护优先原则", 226899) {}
            lesson("综合治理原则", 234727) {}
        }
        listen("可持续发展与生态文明", 385119) {}
        listen("由邻避现象和环境正义运动引发的思考", 385121) {}
    }


    row("包国章") {
        course("人文视野中的生态学", 2015496) {
            lesson("饮食文化与生态的关系", 88516) {}
            lesson("人类社会的r,k-对策", 88544) {}
        }
        listen("是什么决定了生物的性取向？", 387399) {}
        listen("即使是生物本能，恋爱也好处多多", 387387) {}
        listen("男女对“性魅力”要求的差异究竟有多大？", 387393) {}
    }


    row("刘燕") {
        course("奇异的仿生学", 2015507) {
            lesson("鸟、昆虫与飞机", 182065) {}
            lesson("鱼、海兽与潜艇", 182063) {}
        }
        listen("仿生导弹", 387379) {}
        listen("红外夜视仪的“前世今生”", 387385) {}
        listen("用呼吸图谱判断疾病", 387395) {}
    }


    row("王建华") {
        course("汽车行走的艺术", 2015501) {
            lesson("马的驯服", 218373) {}
            lesson("瓦特变革", 221299) {}
        }
        listen("最初那个把电“关进笼子”的神奇容器", 387377) {}
        listen("足以写进科学史的一条“青蛙腿”", 387383) {}
    }


    row("李芳菲") {
        course("材料学概论", 2015576) {
            lesson("金属晶体", 155947) {}
            lesson("认识陶瓷", 155967) {}
        }
        listen("集“软硬”于一身的神奇“水玻璃”", 387389) {}
        listen("小材料的“小秉性”，创造大世界的“大福音”", 387397) {}
    }


    row("胡远超") {
        course("大学生心理健康", 2015524) {
            lesson("学习策略：记忆策略", 158745) {}
            lesson("职业生涯规划", 158789) {}
        }
        listen("乐观的哲学你参透了吗？", 383955) {}
        listen("原来还有这种提升信心的操作！", 383941) {}
        listen("希望是如何与资本扯上关系的？", 383943) {}
    }


    row("乔璐") {
        course("服装色彩搭配", 2015608) {
            lesson("认识色彩上", 163707) {}
            lesson("服装色彩的调和", 163719) {}
        }
        listen("伴随一生的服饰搭配", 387375) {}
        listen("四季服装搭配指南", 387365) {}
    }


    row("周洪涛") {
        course("包装设计", 2018387) {
            lesson("包装的发展（上）", 251279) {}
            lesson("包装风格设计", 223663) {}
        }
        listen("电商带来的包装革命", 387391) {}
        listen("包装设计10步走", 387363) {}
    }



    row("修娜") {
        course("电子产品生产工艺", 2013260) {
            lesson("电子产品的检验", 198725) {}
            lesson("质量控制与品质保证", 198731) {}
        }
        listen("电子产品调试", 387373) {}
        listen("电子产品故障排除", 387381) {}
    }


    row("王新艳") {
        course("电子产品生产工艺", 2013260) {
            lesson("实训室布局", 199967) {}
            lesson("静电技术", 200019) {}
        }
    }


    row("赵静") {
        course("看美剧，学口语", 2015610) {
            lesson("十二星座的奇妙故事 The fairy tale of twelve constellation", 232767) {}
            lesson("教你如何不客气Daily  Expressions", 363473) {}
        }
    }


}


private val s = "TH_PERSONAL_GLORY"

fun main(args: Array<String>) {
    val excel  = excel_season2
    val base = "C:\\Users\\DELL-13\\Desktop\\codeReview\\教师主页\\教师主页二期汇总"


    //
    val waiwang = DB.getJDBCForWaiWang()
//    val waiwang2C = DB.getJDBCFor2CWaiWang()

//    val targetDB = DB.getJDBCForYanFa()
    val targetDB = DB.getJDBCForYuFa()
//    val targetDB = DB.getJDBCForTHWaiWang()



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
    val userNameToInfoMap = getBaseInfoMap(base, "个人信息荣誉等等.txt")
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
    val imgList = parseTxt(File(PathUtil.buildPath(base, "教师背景图片.txt")), "：")
    mergeToBaseMap(imgList, userNameToInfoMap, "背景图")

    //合并背景图
    val imgCarList = parseTxt(File(PathUtil.buildPath(base, "教师背景图片(方形).txt")), "：")
    mergeToBaseMap(imgCarList, userNameToInfoMap, "轮播图")


    //check
    userNameToInfoMap.forEach { t, u ->
        val list = u["用户ID"]
        if(list == null || list.size == 0 || list[0] == "-1")
            error("教师 $t  的 userId不合法")
    }
    println(userNameToInfoMap)


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
            val s_SRC = 2
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


        } catch (e:Exception) {
            e.printStackTrace()
            error("$name 出现问题")
        }
    }

    ////////////////////////////////上面需先执行
    println("##----------UserId转码--------------;")
    userName2UserId.entries.forEach {
        (userName, userId) ->
        val uuid = MD5.getMD5Str(userId.toString()+"zhihuishu").toLowerCase()
        println("set user:uuid:$uuid $userId")
    }
    userName2UserId.entries.forEach {
        (userName, userId) ->
        val uuid = MD5.getMD5Str(userId.toString()+"zhihuishu").toLowerCase()
        println("$userName $userId http://114.55.4.242:8280/teacherhome/share/home?uuid=$uuid&sourceType=appteacher&sourceUUID=1791b30c0c5db69ed41f2db4c1ec5076&isShare=1")
//        println("$userName $userId http://teacherhome.zhihuishu.com/teacherhome/share/home?uuid=$uuid&sourceType=appteacher&sourceUUID=1791b30c0c5db69ed41f2db4c1ec5076&isShare=1")
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
//                        println("#未知序号 $chapterIndex 数组长度:$arrl")
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


    println("##----------语录TH_WONDER_VIDEO--------------;")
    excel.rows.forEach {
        val teacherName = it.name
        var userId = userName2UserId.get(teacherName)?:-1
        val teacherId = userId2TeacherId.get(userId)?:-1

        //先删除已存在的
        removeIfExist(targetDB, "TH_QUOTATIONS", "TEACHER_ID", teacherId)

        it.listens.reversed().forEach {
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
    return

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


