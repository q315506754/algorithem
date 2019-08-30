package com.jiangli.doc.sql.helper.aries.questionaire

import com.jiangli.common.utils.DateUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.util.concurrent.TimeUnit


/**
 *
 *
 * @author Jiangli
 * @date 2019/1/19 15:17
 */
fun main(args: Array<String>) {
//    val env = Env.YANFA
    //    val env = Env.YUFA
        val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val fromEndDate = "2019-08-23 17:10:00"
    val toEndDate = "2019-08-29 17:10:00"
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

        val userSize  = jdbc.queryForObject("""
SELECT COUNT(*) c FROM db_aries_questionnaire.dim_user_apply WHERE  IS_DELETED = 0 $createTimeGt $createTimeLess;
    """.trimIndent(),
                ColumnMapRowMapper())["c"].toString().toInt()

        var increment = ""
        if (prev > 0) {
            increment = "比昨日增加:${userSize - prev}"
        }
        prev = userSize

        pslist.add("${IT}(${DateUtil.getWeekCn(start)}) 总人数:${userSize} ${increment}")

        start+=TimeUnit.DAYS.toMillis(1) //add 1 day
    }

    println()
    println("汇总数据:")
    pslist.forEach {
        println("$it")
    }
//    writeMapToExcel(outputpath,rsList)
}

