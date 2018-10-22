package com.jiangli.doc.sql.helper.zhihuishu.user

import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/27 10:58
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG_ALL
    val jdbc = Zhsutil.getJDBC(env)

//    val userId = Ariesutil.getUserId(jdbc, name="", mobile = "", email = "")
//    val userId = Ariesutil.getUserId(jdbc, name="武凌寒", mobile = "", email = "")
//    val userId = Ariesutil.getUserId(jdbc, name="", mobile = "18801113303", email = "")
//    val userId = Ariesutil.getUserId(jdbc, name="", mobile = "", email = "pengjing@noahwm.com")

//    val userId =187767649
//    val userId =162347707
    val userId =170125517

    val map = Zhsutil.injectFromUserId(jdbc, userId.toLong())

    println(map)

}