package com.jiangli.doc.sql.helper.aries.user

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env

/**
 *
 * 查用户信息
 * @author Jiangli
 * @date 2018/8/27 10:58
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

//    val userId = Ariesutil.getUserId(jdbc, name="", mobile = "", email = "")
    val userId = Ariesutil.getUserId(jdbc, name="武凌寒", mobile = "", email = "")
//    val userId = Ariesutil.getUserId(jdbc, name="", mobile = "13761156786", email = "")
//    val userId = Ariesutil.getUserId(jdbc, name="", mobile = "", email = "pengjing@noahwm.com")

    //2b
//    val userId =100008058

    //2c
//    val userId =100011250

    //group 张伍倩
//    val userId =100007830

    //order
//    val userId =100007076

    //guest
//    val userId =100011568
//    val userId =100013735

    Ariesutil.confirmUserId(jdbc, userId.toLong())
    val map = Ariesutil.injectFromUserId(jdbc, userId.toLong())


    println("""

uuid:
${Ariesutil.convertUUID(userId.toLong())}


###重置密码
UPDATE db_aries_user.TBL_USER set PASSWORD ='1aa14247ac5623ca07a25a099cd66527d7c39705e29a225e5e0f2a6a6216b53e'
WHERE ID='$userId' ;

    """.trimIndent())

    println("""

###重置密码
UPDATE db_aries_user.TBL_USER set PASSWORD ='1aa14247ac5623ca07a25a099cd66527d7c39705e29a225e5e0f2a6a6216b53e'
WHERE ID='$userId' ;

###清空密码
UPDATE db_aries_user.TBL_USER set PASSWORD = null
WHERE ID='$userId' ;

###开通【运营后台】登录权限
INSERT INTO `db_aries_manage`.`TBL_USER` (`USER_ID`, `CREATOR_ID`, `DELETER_ID`, `REMARK`, `IS_LOCK`, `IS_DELETE`, `CREATE_TIME`, `UPDATE_TIME`) VALUES ('$userId', '0', NULL, NULL, '0', '0', '2018-08-06 09:50:43', NULL);

####开通【运营后台】 账号管理 权限
INSERT INTO `db_aries_manage`.`TBL_USER_MENU` (`USER_ID`, `MENU_ID`, `CREATOR_ID`, `DELETER_ID`, `IS_DELETE`, `CREATE_TIME`, `UPDATE_TIME`) VALUES ( '$userId', '4000', '0', NULL, '0', '2018-08-06 09:50:43', NULL);


 ###data

${map}

    """.trimIndent())
}