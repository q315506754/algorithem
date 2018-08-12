package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.YufaRedis

/**
 *
 *
 * @author Jiangli
 * @date 2018/5/29 15:50
 */

fun main(args: Array<String>) {
    val codis = YufaRedis()
    codis.execute("del qa2:openapi:students:guardTime:forStudent")
}