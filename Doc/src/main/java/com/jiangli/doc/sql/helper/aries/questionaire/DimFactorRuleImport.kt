package com.jiangli.doc.sql.helper.aries.questionaire

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.ExcelUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.apache.commons.lang.StringUtils
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
    val env = Env.YANFA
    //    val env = Env.YUFA
    //    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    val inputpath = PathUtil.desktop("""关键词.xlsx""")
    val inputpath = PathUtil.desktop("""key22.xlsx""")

    val list = mutableListOf<String>()

    val typeId = 1
    var dimSort = 1

    var prevFactorIds:MutableList<String>? = null
    var prevDimId:String? = null

    ExcelUtil.processRow(inputpath, 0, 2) { file, workbook, sheet, lastRowIdx, lastColIdx, rowIdx, row ->
        if (rowIdx >= 2) {
            val cellValue = ExcelUtil.getCellValue(row, 0)
            if (cellValue == null) {
                return@processRow
            }
            //            println(cellValue)
            val keys = ExcelUtil.getCellValue(row, 1..3)


            if (StringUtils.isNotBlank(cellValue)) {
                //                rule
                if (cellValue.contains("-")) {
                    val split = cellValue.split("-")
                    val min = split[0]
                    val max = split[1]

                    keys.forEachIndexed { index, s ->
                        val factorId = prevFactorIds!![index]
                        println("""INSERT INTO db_aries_questionnaire.DIM_RULE_FACTOR_KEYWORD(DIMENSION_ID,FACTOR_ID,MIN_SCORE,MAX_SCORE,KEYWORD) VALUES ('$prevDimId','$factorId','$min','$max','$s');""")
                    }
                } else {
                    //fac
                    val query = jdbc.query("""SELECT ID FROM db_aries_questionnaire.DIM_DIMENSION WHERE TITLE='$cellValue' AND TYPE_ID = '$typeId'""", ColumnMapRowMapper())
                    if (query.size == 1) {
                        val DIM_ID = query[0]["ID"].toString()
                        prevDimId = DIM_ID
                        prevFactorIds = mutableListOf()
//                        println("$keys")

                        var relaSort = 1
                        keys.forEach {
                            val fac = jdbc.query("""SELECT ID FROM db_aries_questionnaire.DIM_FACTOR WHERE TITLE='$it' """, ColumnMapRowMapper())
                            if (fac.size == 1) {
                                val FACTOR_ID = fac[0]["ID"].toString()
                                prevFactorIds!!.add(FACTOR_ID)

//                                todo
//                                println("""INSERT INTO db_aries_questionnaire.DIM_DIM_RELA_FACTOR(DIMENSION_ID,FACTOR_ID,SORT) VALUES ('$DIM_ID','$FACTOR_ID','${relaSort++}');""")

                            } else {
//                                println("""INSERT INTO db_aries_questionnaire.DIM_FACTOR(TITLE) VALUES ('$it');""")
                            }

                        }

                    } else {
                        //dim fac
                        println("""INSERT INTO db_aries_questionnaire.DIM_DIMENSION(TITLE,TYPE_ID,SORT) VALUES ('$cellValue','$typeId','${dimSort++}');""")

                    }
                }

            }
        }

    }
}


