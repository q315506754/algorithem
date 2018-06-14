package com.jiangli.doc.txt.importdata

import com.jiangli.common.utils.YufaCodis

/**
 *
 *
 * @author Jiangli
 * @date 2018/5/29 15:50
 */

fun main(args: Array<String>) {
    val codis = YufaCodis()
//    "courseId", "name", "type", "turnType"
    codis.execute("hgetall cc:course:2019316")
//    codis.execute("hgetall g:base:video:68419")
//    codis.execute("hgetall g:base:video:556007")
}