package com.jiangli.doc.sql.helper.zhihuishu.bbs

import com.jiangli.doc.sql.helper.zhihuishu.Env
import com.jiangli.doc.sql.helper.zhihuishu.Zhsutil
import java.util.concurrent.TimeUnit

/**
 *
 *
 * @author Jiangli
 * @date 2018/10/18 16:21
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG_ALL
//    val env = Env.WAIWANG_BBS
    val jdbc = Zhsutil.getJDBC(env)

    val PAGE_SIZE = 100
    val INTERVAL: Long = if (DEBUG_MODE) 100 else 66
    val timeUnit = if (DEBUG_MODE) TimeUnit.MILLISECONDS else TimeUnit.MINUTES

}
