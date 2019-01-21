package com.jiangli.doc.sql.helper.aries.study

import com.jiangli.common.utils.PathUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 * 用户学习有效期
 *
 * @author Jiangli
 * @date 2018/12/10 15:57
 */
fun main(args: Array<String>) {
    //    val env = Env.YANFA
    //    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val companyMap = mapOf(
            "波司登集团" to "100"
    )

    val outputpath="""${PathUtil.desktop("用户学习有效期.xlsx")}"""

    companyMap.forEach { companyName, companyId ->

    }

    val s = companyMap.values.joinToString ("," )
    println(s)

    val sql = """

SELECT uc.COMPANY_ID,com.NAME as COMPANY_NAME,uc.COURSE_ID,c.COURSE_NAME,uc.RECRUIT_ID,r.START_TIME,r.END_TIME,r.VALID_TIME,uc.CLASS_ID,cls.CLASS_NAME,uc.USER_ID,u.NAME as USER_NAME,uc.CREATE_TIME as 'JOIN_TIME', DATE_ADD(uc.CREATE_TIME,INTERVAL r.VALID_TIME DAY) as 'LAST_VALID_TIME',uc.IS_GUEST FROM db_aries_study.TBL_USER_COURSE uc
LEFT JOIN db_aries_user.TBL_USER u on uc.USER_ID = u.ID
LEFT JOIN db_aries_company.TBL_COMPANY com on uc.COMPANY_ID = com.ID
LEFT JOIN db_aries_course.TBL_COURSE c on uc.COURSE_ID = c.COURSE_ID
LEFT JOIN db_aries_run.TBL_RECRUIT r on uc.RECRUIT_ID = r.RECRUIT_ID
LEFT JOIN db_aries_run.TBL_CLASS cls on uc.CLASS_ID = cls.CLASS_ID
WHERE
  uc.IS_DELETE = 0
  AND uc.COMPANY_ID in (${s})
ORDER BY uc.COMPANY_ID ASC,uc.RECRUIT_ID ASC,uc.CLASS_ID ASC,uc.USER_ID ASC
;
""".trimIndent()
    val userAndCourse = jdbc.query(sql, ColumnMapRowMapper())
    writeMapToExcel(outputpath,userAndCourse)
}

