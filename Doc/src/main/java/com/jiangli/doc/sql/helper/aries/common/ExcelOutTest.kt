package com.jiangli.doc.sql.helper.aries.common

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 * 教学企业数量 分布数据
 *
 * @author Jiangli
 * @date 2019/12/20 14:00
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val questions = jdbc.query("""
SELECT DISTINCT(COMPANY) FROM (
SELECT TRIM(COMPANY_NAME) as COMPANY FROm db_aries_user.tbl_user
WHERE
        IS_DELETED=0
AND COMPANY_NAME IS NOT NULL
AND COMPANY_NAME != ''
UNION
SELECT TRIM(COMPANY) as COMPANY FROM `db_aries_erp`.`ACTIVITY_CUSTOMER` WHERE IS_DELETE=0
              ) x; # 100003739
    """.trimIndent(), ColumnMapRowMapper())

    writeMapToExcel(PathUtil.desktop("""testoutc.xlsx"""), questions)
}