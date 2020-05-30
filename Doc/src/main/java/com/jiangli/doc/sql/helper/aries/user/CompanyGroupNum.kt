package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2019/10/28 10:17
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    val TARGET_TYPE = 1
    val TYPE = 1 // 部门
    val COMPANY_ID = 93 //

//    非空组数量
    val allMapList = jdbc.query("""
SELECT x.ID,x.NAME,x.TARGET_TYPE,x.TARGET_ID
FROM db_aries_user.TBL_GROUP x
where
 x.IS_DELETED = 0
AND x.TARGET_TYPE=$TARGET_TYPE
AND x.TARGET_ID=$COMPANY_ID
AND x.TYPE=$TYPE
 ;
    """.trimIndent(), ColumnMapRowMapper())

    println("全组:${allMapList.size}")

//    非空组数量
    val numMapList = jdbc.query("""
SELECT gm.GROUP_ID,g.NAME,count(1) as C
    FROM db_aries_user.TBL_GROUP_MEMBERS gm
             LEFT JOIN db_aries_user.TBL_GROUP g ON gm.GROUP_ID = g.ID
    WHERE
            g.IS_DELETED=0
      AND g.TARGET_TYPE=$TARGET_TYPE
      AND g.TYPE=$TYPE
      AND g.TARGET_ID=$COMPANY_ID

      AND gm.IS_DELETED = 0
      AND gm.USER_ID  IN (
        SELECT uc.USER_ID FROM db_aries_user.TBL_USER_COMPANY uc
                                   LEFT JOIN db_aries_user.TBL_USER_SAAS_COMPANY usc on uc.ID =usc.USER_COMPANY_ID
        WHERE uc.IS_DELETED=0 AND usc.IS_DELETED = 0 AND uc.COMPANY_ID=$COMPANY_ID
    )
    GROUP BY gm.GROUP_ID;
    """.trimIndent(), ColumnMapRowMapper())

    println("非空组:${numMapList.size}")

}