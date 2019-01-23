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

    val qnId = 1

    //    val inputpath = PathUtil.desktop("""关键词.xlsx""")
    val outputpath = PathUtil.desktop("""test_试卷.xlsx""")
    val rsList: MutableList<MutableMap<String, Any>> = mutableListOf()


    val questions = jdbc.query("""
SELECT q.* from db_aries_questionnaire.dim_factor_rela_question frq
LEFT JOIN db_aries_questionnaire.tbl_question q on frq.QUESTION_ID = q.ID AND q.IS_DELETED = 0
LEFT JOIN db_aries_questionnaire.TBL_QUESTIONNAIRE_QUESTION qq on frq.QUESTIONNAIRE_ID = qq.QUESTIONNAIRE_ID AND qq.IS_DELETED = 0
WHERE frq.IS_DELETED=0 AND frq.QUESTIONNAIRE_ID = $qnId
ORDER BY qq.RANK;
    """.trimIndent(), ColumnMapRowMapper())

    var qSort = 1
    for (question in questions) {
        val questionName = question["CONTENT"]!!
        val questionScore = question["SCORE"]!!
        val qId = question["ID"]!!

        val one = mutableMapOf<String, Any>()
        rsList.add(one)

        one.put("试卷", qnId!!)
        one.put("题目序号", "${qSort++}/${questions.size}")
        one.put("题目内容", questionName)

        val belong = jdbc.queryForObject("""
SELECT f.TITLE as factor,d.TITLE as dim from db_aries_questionnaire.dim_factor_rela_question frq
  LEFT JOIN db_aries_questionnaire.dim_dim_rela_factor drf ON frq.FACTOR_ID = drf.FACTOR_ID AND drf.IS_DELETED = 0
  LEFT JOIN db_aries_questionnaire.dim_dimension d ON d.ID = drf.DIMENSION_ID AND d.IS_DELETED = 0
  LEFT JOIN db_aries_questionnaire.dim_factor f ON f.ID = frq.FACTOR_ID AND f.IS_DELETED = 0
WHERE frq.QUESTION_ID=$qId AND  frq.IS_DELETED = 0 ;
    """.trimIndent(), ColumnMapRowMapper())
        one.put("所属维度", belong["dim"]!!)
        one.put("所属因素", belong["factor"]!!)

        var oCount = 1
        val options = jdbc.query("""
SELECT * from db_aries_questionnaire.tbl_question_option
WHERE QUESTION_ID=$qId AND IS_DELETED = 0
ORDER BY RANK ASC;
    """.trimIndent(), ColumnMapRowMapper())


        for (option in options) {
            val optionName = option["CONTENT"]!!
            val optId = option["ID"]!!
            val QUESTION_ID = option["QUESTION_ID"].toString()!!
            val SCORE = option["SCORE"]!!


            one.put("选项${oCount++}", "${SCORE}/$questionScore $optionName")
        }
    }

    writeMapToExcel(outputpath, rsList)
}