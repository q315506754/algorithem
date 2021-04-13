package com.jiangli.doc.sql.helper.aries.menu

/**
 *
 *
 * @author Jiangli
 * @date 2020/12/25 10:31
 */
fun main(args: Array<String>) {
    val fromMenuId = 3030
    val toMenuId = 10010

    val s = toMenuId.toString()
    val toMenuIdLv1 = (s.get(0).toString().toInt() * Math.pow(10.0, (s.length-1).toDouble())).toInt()

//    #补2级菜单
    val sqlLv2 = """
        INSERT INTO `db_aries_manage`.`TBL_USER_MENU`(USER_ID,MENU_ID,CREATOR_ID)
SELECT DISTINCT (USER_ID),$toMenuId,-1 FROM `db_aries_manage`.`TBL_USER_MENU` um
WHERE
      um.IS_DELETE=0
      AND exists( SELECT * FROM `db_aries_manage`.`TBL_USER_MENU` WHERE MENU_ID = $fromMenuId AND USER_ID = um.USER_ID AND IS_DELETE=0)
      AND NOT exists( SELECT * FROM `db_aries_manage`.`TBL_USER_MENU` WHERE MENU_ID = $toMenuId AND USER_ID = um.USER_ID AND IS_DELETE=0)
    """.trimIndent()

//    #补1级菜单
    val sqlLv1 = """
INSERT INTO `db_aries_manage`.`TBL_USER_MENU`(USER_ID,MENU_ID,CREATOR_ID)
SELECT DISTINCT (USER_ID),$toMenuIdLv1,-1 FROM `db_aries_manage`.`TBL_USER_MENU` um
WHERE
      um.IS_DELETE=0
      AND exists( SELECT * FROM `db_aries_manage`.`TBL_USER_MENU` WHERE MENU_ID = $toMenuId AND USER_ID = um.USER_ID AND IS_DELETE=0)
      AND NOT exists( SELECT * FROM `db_aries_manage`.`TBL_USER_MENU` WHERE MENU_ID = $toMenuIdLv1 AND USER_ID = um.USER_ID AND IS_DELETE=0)
;
    """.trimIndent()

    println(sqlLv2)
    println(sqlLv1)
}