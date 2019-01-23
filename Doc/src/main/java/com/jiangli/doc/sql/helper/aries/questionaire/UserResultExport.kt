package com.jiangli.doc.sql.helper.aries.questionaire

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

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

//    val applyId = 12
    val applyId = 17

    val userInfo  = jdbc.queryForObject("""
SELECT ua.* from db_aries_questionnaire.tbl_answer_user_apply aua
  LEFT JOIN db_aries_questionnaire.dim_user_apply ua ON  aua.USER_ID = ua.ID AND ua.IS_DELETED = 0
WHERE aua.ID=$applyId;
    """.trimIndent(),
            ColumnMapRowMapper())
    val NAME = userInfo["NAME"]!!

    //    val inputpath = PathUtil.desktop("""关键词.xlsx""")
    val outputpath = PathUtil.desktop("""test_result_$NAME.xlsx""")
    val rsList:MutableList<MutableMap<String,Any>> = mutableListOf()

    val tranMains  = jdbc.query("""
SELECT * from db_aries_questionnaire.DIM_TRAN_MAIN tr
WHERE IS_DELETED = 0 AND QUESTIONNAIRE_APPLY_ID=$applyId AND STATUS = 1
ORDER BY ID DESC ;
    """.trimIndent(),
            ColumnMapRowMapper())

    var times = tranMains.size
    for (mp in tranMains) {
        val tranId = mp["TRAN_ID"]
        val qnId = mp["QUESTIONNAIRE_ID"]
        val curTimes = times--
        println("$NAME $tranId $qnId")



        val questions  = jdbc.query("""
SELECT q.* from db_aries_questionnaire.dim_factor_rela_question frq
LEFT JOIN db_aries_questionnaire.tbl_question q on frq.QUESTION_ID = q.ID AND q.IS_DELETED = 0
LEFT JOIN db_aries_questionnaire.TBL_QUESTIONNAIRE_QUESTION qq on frq.QUESTIONNAIRE_ID = qq.QUESTIONNAIRE_ID AND qq.IS_DELETED = 0
WHERE frq.IS_DELETED=0 AND frq.QUESTIONNAIRE_ID = $qnId
ORDER BY qq.RANK;
    """.trimIndent(),
                ColumnMapRowMapper())

        var qSort = 1
        for (question in questions) {
            val questionName = question["CONTENT"]!!
            val questionScore = question["SCORE"]!!
            val qId = question["ID"]!!
            var userFinalScore = 0

            val one = mutableMapOf<String,Any>()
            rsList.add(one)

            one.put("用户名",NAME)
            one.put("第几次提交测试",curTimes)
            one.put("试卷",qnId!!)
            one.put("题目序号","${qSort++}/${questions.size}")
            one.put("题目内容",questionName)


            var oCount = 1
            val options  = jdbc.query("""
SELECT * from db_aries_questionnaire.tbl_question_option
WHERE QUESTION_ID=$qId AND IS_DELETED = 0
ORDER BY RANK ASC;
    """.trimIndent(),
                    ColumnMapRowMapper())

            val userOptions = mutableMapOf<String,MutableMap<String,Any>>()
            jdbc.query("""
SELECT * from db_aries_questionnaire.tbl_answer_result
WHERE ANSWER_TRANSACTION_ID=$tranId AND IS_DELETED = 0
ORDER BY ID DESC ;
    """.trimIndent(),ColumnMapRowMapper()).forEach {
                userOptions.put(it["QUESTION_ID"].toString()!!,it!!)
            }


            for (option in options) {
                val optionName = option["CONTENT"]!!
                val optId = option["ID"]!!
                val QUESTION_ID = option["QUESTION_ID"].toString()!!
                val SCORE = option["SCORE"]!!
                val userOpt = userOptions[QUESTION_ID]


//                有答案
                if (userOpt!=null) {
                    val userOptId = userOpt["QUESTION_OPTION_ID"]!!
                    val userSCORE = userOpt["SCORE"]!!
                    userFinalScore = userSCORE.toString().toInt()

                    if (userOptId == optId) {
                        one.put("选项${oCount}","${SCORE}/$questionScore $optionName")
                    }else {
                        one.put("选项${oCount}","")
                    }

                    oCount++
                } else {
                    one.put("选项${oCount}","没有答案???")
                }
            }

            one.put("该选项用户得分","$userFinalScore")
        }
    }

    writeMapToExcel(outputpath,rsList)
}