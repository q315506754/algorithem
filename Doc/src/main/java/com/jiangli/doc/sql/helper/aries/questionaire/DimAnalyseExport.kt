package com.jiangli.doc.sql.helper.aries.questionaire

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.math.BigDecimal


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


    val mp:MutableMap<IntRange,Int> = linkedMapOf()
    mp.put(0..20,0)
    mp.put(20..30,0)
    mp.put(30..40,0)
    mp.put(40..50,0)
    mp.put(50..999,0)

    println("各个维度得分比较情况：")
    mp.forEach { t, u ->
        val userInfos  = jdbc.query("""
SELECT dd.TITLE,AVG(sud.WEIGHT_SCORE)AS WEIGHT_SCORE from db_aries_questionnaire.STATIS_USER_DIM sud
  LEFT JOIN db_aries_questionnaire.DIM_DIMENSION dd ON sud.DIM_ID=dd.ID
WHERE sud.USER_AGE>=${t.first} AND sud. USER_AGE <${t.last}
GROUP BY sud.DIM_ID
ORDER BY WEIGHT_SCORE ASC
;
    """.trimIndent(),
                ColumnMapRowMapper())

        var sb = "{"
        userInfos.forEachIndexed { idx, it ->
            sb = sb + "${it["TITLE"]}:${it["WEIGHT_SCORE"]}"
            if (idx<userInfos.lastIndex){
                sb = sb +","
            }
        }
        sb+="}"

        var sb2 = ""
        userInfos.forEachIndexed { idx, it ->
            sb2 += "${it["TITLE"]}"
            if (idx<userInfos.lastIndex){
                sb2 += "<"
            }
        }

        var str = if(t.first == 0) "${t.last}岁以下" else if (t.last == 999) "${t.first}岁以上" else "$t"
//        打印数字
//        println("$str: $sb")

//        打印比较结果
        println("$str: $sb2")

    }


    val percents  = jdbc.query("""
SELECT t3.DIM_ID,dd.TITLE,COUNT(t3.DIM_ID) as MIN,
  COUNT(t3.DIM_ID) / (SELECT COUNT(DISTINCT(DIM_USER_ID)) FROM db_aries_questionnaire.STATIS_USER_DIM)*100 as PERCENT

from (

  SELECT d1.USER_AGE,d1.WEIGHT_SCORE,d1.DIM_ID
  FROM db_aries_questionnaire.STATIS_USER_DIM d1
    RIGHT JOIN
    (
      SELECT d2.DIM_USER_ID,MIN(d2.WEIGHT_SCORE) as WEIGHT_SCORE
      FROM db_aries_questionnaire.STATIS_USER_DIM d2
      GROUP BY d2.DIM_USER_ID
    ) as t2 ON d1.DIM_USER_ID = t2.DIM_USER_ID AND d1.WEIGHT_SCORE = t2.WEIGHT_SCORE

  GROUP BY d1.DIM_USER_ID

) as t3
  LEFT JOIN db_aries_questionnaire.DIM_DIMENSION dd ON t3.DIM_ID=dd.ID
GROUP BY  t3.DIM_ID
ORDER BY MIN DESC ;
    """.trimIndent(),
            ColumnMapRowMapper())


    println("测试结果五个维度中：")
    percents.forEach {
        println("${it["TITLE"]}最低 ${BigDecimal(it["PERCENT"].toString()).setScale(2,BigDecimal.ROUND_DOWN)}%")
    }
}

