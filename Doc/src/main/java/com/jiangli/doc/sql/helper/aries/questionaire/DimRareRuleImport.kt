package com.jiangli.doc.sql.helper.aries.questionaire

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.apache.commons.lang.StringUtils

/**
 *
 *
 * @author Jiangli
 * @date 2019/1/19 15:17
 */
fun main(args: Array<String>) {

    val env = Env.YANFA
    //    val env = Env.YUFA
    //    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    //    val inputpath = PathUtil.desktop("""关键词.xlsx""")
    val inputpath = PathUtil.desktop("""rule22.xlsx""")

    ExcelUtil.processRow(inputpath, 0, 2) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 2) {
            val range = ExcelUtil.getCellValue(row, 0)
            val rare = ExcelUtil.getCellValue(row, 1)
            val summary = ExcelUtil.getCellValue(row, 2)
            if (range == null) {
                return@processRow
            }

            if (StringUtils.isNotBlank(range)) {
                //                rule
                if (range.contains("-")) {
                    val split = range.split("-")
                    val min = split[0]
                    val max = split[1]

                    println("""INSERT INTO db_aries_questionnaire.DIM_RULE_RARE(MIN_SCORE,MAX_SCORE,RARITY,SUMMARY) VALUES ('$min','$max','$rare','$summary');""")
                }
            }
        }

    }


}