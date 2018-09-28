package com.jiangli.doc.sql.helper.zhihuishu.bbs

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


    val questionId =565279
    val map = Zhsutil.injectFromQuestionId(jdbc, questionId.toLong())
    println(map)
}