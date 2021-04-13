package com.jiangli.doc.sql.helper.aries.base

import com.jiangli.common.utils.FileUtil
import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.JdbcTemplate

/**
 * 教学企业数量 分布数据
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    //        val env = Env.YANFA
    //        val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)
    val outputpath = PathUtil.desktop("""area_zs.js""")



    var str = """
export default {
  province_list:{
${queryCityData(jdbc, 0)}
  }
  ,city_list:{
${queryCityData(jdbc, 1)}
  }
  ,county_list:{
${queryCityData(jdbc, 2)}
  }
};
    """.trimIndent()

    FileUtil.writeStr(str,outputpath)
}

private fun queryCityData(jdbc: JdbcTemplate, deep: Int): String {
    val questions = jdbc.query("""
SELECT * FROM `db_aries_base`.`TBL_CITY` WHERE `DEEP` IN ($deep) ORDER BY ID ASC;
""".trimIndent(), ColumnMapRowMapper())

    var each = ""
    questions.forEach {
        each += "${it["ID"]}:'${it["EXT_NAME"]}',\n"
    }
    return each
}