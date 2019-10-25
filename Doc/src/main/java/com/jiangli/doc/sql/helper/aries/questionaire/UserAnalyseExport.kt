package com.jiangli.doc.sql.helper.aries.questionaire

import com.jiangli.common.utils.DateUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * 查询各个维度数据
 *
 * @author Jiangli
 * @date 2019/1/19 15:17
 */
fun main(args: Array<String>) {
//    val env = Env.YANFA
    //    val env = Env.YUFA
        val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val fromEndDate = "2019-10-10 17:10:00"
    val toEndDate = "2019-10-10 17:10:00"
//    val toEndDate = "2019-03-25 17:10:00"

    var start = DateUtil.getDate(fromEndDate).time
    val end = DateUtil.getDate(toEndDate).time

    val pslist =  mutableListOf<String>()
    var prev = 0
    while (start<=end) {
        println("\n\n")
        val createTimeGt = "AND CREATE_TIME > '2019-02-19 06:30:00'"
        //    val createTimeGt = ""

        val IT = DateUtil.getDate_yyyyMMddHHmmss(start)
        val createTimeLess = "AND CREATE_TIME < '$IT'"
        //    val createTimeLess = ""


        println("时间范围为 $createTimeGt ~ $createTimeLess")

        val userInfos  = jdbc.query("""
SELECT * FROM db_aries_questionnaire.dim_user_apply WHERE  IS_DELETED = 0 $createTimeGt $createTimeLess;
    """.trimIndent(),
                ColumnMapRowMapper())

        val outputpath = PathUtil.desktop("""用户分析.xlsx""")
        val rsList:MutableList<MutableMap<String,Any>> = mutableListOf()

        val mp:MutableMap<IntRange,Int> = linkedMapOf()
        //    mp.put(0..20,0)
        //    mp.put(20..25,0)
        //    mp.put(25..30,0)
        //    mp.put(30..35,0)
        //    mp.put(35..40,0)
        //    mp.put(40..45,0)
        //    mp.put(45..50,0)
        //    mp.put(50..999,0)

        mp.put(0..20,0)
        mp.put(20..30,0)
        mp.put(30..40,0)
        mp.put(40..50,0)
        mp.put(50..999,0)

        userInfos.forEach {
            val BIRTHDAY = it["BIRTHDAY"]!!.toString()

            val c1 = Calendar.getInstance()
            c1.setTime(SimpleDateFormat("yyyy-MM-dd").parse(BIRTHDAY))

            val now = Calendar.getInstance()
            val age = now.get(Calendar.YEAR) - c1.get(Calendar.YEAR)

            for (entry in mp) {
                val from = entry.key.first
                val to = entry.key.last
                if (age>from && age <= to) {
                    mp[entry.key]= mp[entry.key]!! +1
                }
            }

            //        println("$BIRTHDAY $age")
        }

        println("总人数:"+userInfos.size)

        var increment = ""
        if (prev > 0) {
            increment = "比昨日增加:${userInfos.size - prev}"
        }
        prev = userInfos.size

        pslist.add("${IT}(${DateUtil.getWeekCn(start)}) 总人数:${userInfos.size} ${increment}")

        println("年龄分布:"+mp)

        println("职务等级分布"+joinMap(linkedMapOf(
                "1" to "基层"
                ,"2" to "中层"
                ,"3" to "高层"
        ), userInfos.groupBy {
            it["JOB_CATEGORY"]!!.toString()
        }))

        println("性别分布:"+joinMap(linkedMapOf(
                "0" to "男"
                ,"1" to "女"
        ), userInfos.groupBy {
            it["SEX"]!!.toString()
        }))

        println("受教育等级分布:"+joinMap(linkedMapOf(
                "1" to "中专"
                ,"2" to "大专"
                ,"3" to "本科"
                ,"4" to "硕士"
                ,"5" to "MBA"
                ,"6" to "EMBA"
                ,"7" to "博士及以上"
        ), userInfos.groupBy {
            it["EDUCATION"]!!.toString()
        }))

        println("工作年限分布:"+joinMap(linkedMapOf(
                "1" to "1-3年"
                ,"2" to "3-5年"
                ,"3" to "5-10年"
                ,"4" to "10-20年"
                ,"5" to "20年以上"
        ), userInfos.groupBy {
            it["WORK_EXPERIENCE"]!!.toString()
        }))

        println("公司规模分布:"+joinMap(linkedMapOf(
                "1" to "少于50人"
                ,"2" to "50-150人"
                ,"3" to "150-500人"
                ,"4" to "500-1000人"
                ,"5" to "1000-5000人"
                ,"6" to "5000-10000人"
                ,"7" to "10000人以上"
        ), userInfos.groupBy {
            it["COMPANY_SCALE"]!!.toString()
        }))

        println("行业类别分布:"+joinMap(linkedMapOf(
                "1" to "O2O"
                ,"2" to "旅游"
                ,"3" to "分类信息"
                ,"4" to "音乐/视频/阅读"
                ,"5" to "在线教育"
                ,"6" to "社交网络"
                ,"7" to "人力资源服务"
                ,"8" to "企业服务"
                ,"9" to "信息安全"
                ,"10" to "智能硬件"
                ,"11" to "移动互联网"
                ,"12" to "互联网"
                ,"13" to "计算机软件"
                ,"14" to "通信/网络设备"
                ,"15" to "数据服务"
                ,"16" to "电子商务"
                ,"17" to "广告/公关/会展"
                ,"18" to "金融"
                ,"19" to "物流/仓储"
                ,"20" to "贸易/进出口"
                ,"21" to "咨询"
                ,"22" to "工程施工"
                ,"23" to "汽车生产"
                ,"24" to "区块链"
                ,"25" to "人工智能"
                ,"26" to "游戏"
                ,"27" to "医疗健康"
                ,"28" to "生活服务"
                ,"29" to "其他行业"
        ), userInfos.groupBy {
            it["SECTORS_CATEGORY"]!!.toString()
        }))

        start+=TimeUnit.DAYS.toMillis(1) //add 1 day
    }

    println()
    println("汇总数据:")
    pslist.forEach {
        println("$it")
    }
//    writeMapToExcel(outputpath,rsList)
}

fun joinMap(disMap: Map<String, String>, mp: Map<String, List<MutableMap<String, Any>>>): MutableMap<String,Int> {
    val ret = linkedMapOf<String,Int>()

//    ret { xx行业：123 }
//    ret包含左disMap
    disMap.forEach { n, u ->
        var list = mp.get(n)
        if (list == null) {
            ret.put(u,0)
        } else {
            ret.put(u,list.size)
        }
    }

//  ret不包含左disMap的情况
    mp.forEach { t, u ->
        var dis = disMap.get(t)
//        获取其行业名称
        if (dis == null) {
            dis = t
        }

        var c = ret.get(dis)
        if (c == null) {
            ret.put(dis,0)
        }
    }
    return ret
}
