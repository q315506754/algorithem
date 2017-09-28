package com.jiangli.doc.txt.importdata

import com.jiangli.doc.txt.DB

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 14:39
 */


fun main(args: Array<String>) {
    val jdbc = DB.getJDBCForYanFa()
//    val jdbc = DB.getJDBCForYuFa()
//    val jdbc = DB.getJDBCForTHWaiWang()


    val list = arrayListOf(
            "冯玮",
            "于海",
            "黄天中",
            "张静",
            "葛剑雄",

            ""
    )

    println("##----------sql 更新--------------;")
    val remove_sql = "update db_teacher_home.TH_TEACHER set SORT=NULL WHERE SRC=1 AND SORT IS NOT NULL;"
    println(remove_sql)

    list.forEachIndexed { index, s ->
        if (s.isNotEmpty()) {
            val sql ="update db_teacher_home.TH_TEACHER set SORT=${index+1} WHERE SRC=1 AND NAME='$s';"
            println(sql)
        }
    }


    println("##----------redis --------------;")
    val keys = arrayListOf<String>()
    keys.add("th:openapi:teachers:remoteresult:src:1:ts") //12名师
    delKeysPage(keys)
}