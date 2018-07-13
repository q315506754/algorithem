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
//    codis.execute("hgetall cc:course:2019316")
    codis.execute("del th:teacher:184 th:byConcernNum:184 th:personalGloryIds:184 th:wonderVideo:ids:184 th:quotations:ids:184 th:teacher:185 th:byConcernNum:185 th:personalGloryIds:185 th:wonderVideo:ids:185 th:quotations:ids:185")
//    codis.execute("hgetall g:base:video:68419")
//    codis.execute("hgetall g:base:video:556007")
//    codis.execute("hgetall qa:question:hash:418")
//    del qa2:openapi:students:guardTime:forStudent

//    hgetall qa:question:hash:418

}