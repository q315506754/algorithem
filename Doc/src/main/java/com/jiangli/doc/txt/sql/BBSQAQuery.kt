package com.jiangli.doc.txt.sql

import com.jiangli.doc.txt.DB
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.jdbc.core.ColumnMapRowMapper
import java.io.File
import java.io.FileOutputStream

/**
 *
 *
//    查学校下所有学生id、姓名
SELECT s.STUDENT_ID as USER_ID,s.SCHOOL_ID,u.REAL_NAME FROM db_G2S_OnlineSchool.STUDENT s
LEFT JOIN db_G2S_OnlineSchool.V2_RECRUIT vr ON s.RECRUIT_ID=[图片]vr.ID
LEFT JOIN db_G2S_OnlineSchool.TBL_USER u on u.ID=s.STUDENT_ID
WHERE s.IS_DELETE=0 AND vr.IS_DELETE=0 AND s.RECRUIT_ID=$RECRUIT_ID
AND s.SCHOOL_ID=$SCHOOL_ID;

//    查招生下所有学生对应问题数
SELECT CREATE_USER as USER_ID,count(*) as QCOUNT from  ZHS_BBS.QA_QUESTION
WHERE RECRUIT_ID = $RECRUIT_ID
GROUP BY CREATE_USER;

//    查招生下所有学生对应回答数
SELECT A_USER_ID as USER_ID,count(*) as ACOUNT from  ZHS_BBS.QA_ANSWER LEFT JOIN ZHS_BBS.QA_QUESTION ba ON Q_ID = ba.QUESTION_ID
WHERE RECRUIT_ID = $RECRUIT_ID
GROUP BY A_USER_ID;

//    查招生下所有学生对应评论数
SELECT COMMENT_USER_ID as USER_ID,count(*) as CCOUNT from  ZHS_BBS.QA_COMMENT LEFT JOIN ZHS_BBS.QA_QUESTION ba ON Q_ID = ba.QUESTION_ID
WHERE RECRUIT_ID = $RECRUIT_ID
GROUP BY COMMENT_USER_ID;

 */

fun main(args: Array<String>) {
    val qaDB = DB.getWendaJDBCForWaiWang()
    val xuetangDB = DB.getJDBCForWaiWang()
    val ouputFile = """C:\Users\Jiangli\Desktop\学生.xlsx"""

    val RECRUIT_ID  = 6707
    val COURSE_ID  = 2023204
    val SCHOOL_ID  = 450

//    查学校下所有学生id、姓名
    val sql_user = """
SELECT s.STUDENT_ID as USER_ID,s.SCHOOL_ID,u.REAL_NAME FROM db_G2S_OnlineSchool.STUDENT s
 LEFT JOIN db_G2S_OnlineSchool.V2_RECRUIT vr ON s.RECRUIT_ID=vr.ID
  LEFT JOIN db_G2S_OnlineSchool.TBL_USER u on u.ID=s.STUDENT_ID
WHERE s.IS_DELETE=0 AND vr.IS_DELETE=0 AND s.RECRUIT_ID=$RECRUIT_ID
      AND s.SCHOOL_ID=$SCHOOL_ID;
    """
//    val sql_user = """
//SELECT DISTINCT(s.STUDENT_ID) as USER_ID,s.SCHOOL_ID,u.REAL_NAME,u.NICK_NAME FROM db_G2S_OnlineSchool.STUDENT s
//  JOIN db_G2S_OnlineSchool.V2_RECRUIT vr ON s.RECRUIT_ID=$RECRUIT_ID
//  LEFT JOIN db_G2S_OnlineSchool.TBL_USER u on u.ID=USER_ID
//WHERE s.IS_DELETE=0 AND vr.IS_DELETE=0
//      AND s.SCHOOL_ID=$SCHOOL_ID;
//    """
    val query_user = xuetangDB.query(sql_user, ColumnMapRowMapper())
    println(query_user)
    println("query_user ${query_user.size}")
    val userIds = extractSetFromMap(query_user, "USER_ID")
    println("userIds ${userIds.size}")
    val queryUserStr = """(${userIds.joinToString()})"""

//    查招生下所有学生对应问题数
    val sql_q = """
SELECT CREATE_USER as USER_ID,count(*) as QCOUNT from  ZHS_BBS.QA_QUESTION
  WHERE RECRUIT_ID = $RECRUIT_ID and CREATE_USER in $queryUserStr
GROUP BY CREATE_USER;
    """

//    查招生下所有学生对应回答数
    val sql_a = """
SELECT A_USER_ID as USER_ID,count(*) as ACOUNT from  ZHS_BBS.QA_ANSWER LEFT JOIN ZHS_BBS.QA_QUESTION ba ON Q_ID = ba.QUESTION_ID
WHERE RECRUIT_ID = $RECRUIT_ID and A_USER_ID in $queryUserStr
GROUP BY A_USER_ID;
    """

//    查招生下所有学生对应评论数
    val sql_c = """
SELECT COMMENT_USER_ID as USER_ID,count(*) as CCOUNT from  ZHS_BBS.QA_COMMENT LEFT JOIN ZHS_BBS.QA_QUESTION ba ON Q_ID = ba.QUESTION_ID
WHERE RECRUIT_ID = $RECRUIT_ID  and COMMENT_USER_ID in $queryUserStr
GROUP BY COMMENT_USER_ID;
    """

    val query_q = qaDB.query(sql_q, ColumnMapRowMapper())
    println("query_q ${query_q.size}")
    val query_a = qaDB.query(sql_a, ColumnMapRowMapper())
    println("query_a ${query_a.size}")
    val query_c = qaDB.query(sql_c, ColumnMapRowMapper())
    println("query_c ${query_c.size}")

    val mergeMapList = mergeMapList("USER_ID",query_user, query_q, query_a, query_c)
//    println(mergeMapList)

    makeupMapList(mergeMapList,"QCOUNT" to 0,"ACOUNT" to 0,"CCOUNT" to 0,"REAL_NAME" to "未知")
    println(mergeMapList)
    println(mergeMapList.size)


    mergeMapList.forEach {
//        println(it)
    }

    //create
    val exconfig = arrayListOf(
            "USER_ID" to "用户id"
            ,"REAL_NAME" to "姓名"
            ,"QCOUNT" to "问题数"
            ,"ACOUNT" to "回答数"
            ,"CCOUNT" to "评论数"
            )

    writeMapToExcel(ouputFile, exconfig, mergeMapList)

}

// [3,4,2] -> [3->3,4->4,2->2]
fun < T> iterToPair(list:Iterable<T>): List<Pair< T, T>> {
    val ret = mutableListOf<Pair< T, T>>()
    list.forEach {
        ret.add(it to it)
    }
    return ret
}

fun extractSetFromMap(list:List<MutableMap<String, Any>>,k:String):Set<Any> {
    val ret = mutableSetOf<Any>()

    list.forEach {
        mp->mp.forEach { t, u ->
            if (k == t) {
                ret.add(u)
            }
        }
    }

    return ret
}

fun extractMapListKeys( list:List<MutableMap<String, Any>>):Set<String> {
    val ret = mutableSetOf<String>()
    list.forEach {
        mp->mp.forEach { t, u ->
            ret.add(t)
        }
    }
    return ret
}
fun writeMapToExcel(ouputFile: String, mergeMapList: List<MutableMap<String, Any>>) {
    val ret = arrayListOf<Pair<String, String>>()
    mergeMapList[0].forEach {entry ->
        ret.add(entry.key to entry.key)
    }
    writeMapToExcel(ouputFile,ret,mergeMapList)
}

fun writeMapToExcel(ouputFile: String, exconfig: ArrayList<Pair<String, String>>, mergeMapList: List<MutableMap<String, Any>>) {
    val file = File(ouputFile)
    if (!file.exists()) {
        file.createNewFile()
    }

    val workbook = XSSFWorkbook()
    val page1 = workbook.createSheet()

    val row1 = page1.createRow(0)
    var rowIdx = 1
    val nameToIdx = mutableMapOf<String, Int>()
    exconfig.forEachIndexed { index, pair ->
        val c = row1.createCell(index)
        c.setCellValue(pair.second)

        nameToIdx.put(pair.first, index)
    }


    mergeMapList.forEach { mp ->
        val curRow = page1.createRow(rowIdx++)
        mp.forEach { entry ->
            if (nameToIdx[entry.key] != null) {
                val c = curRow.createCell(nameToIdx[entry.key]!!)
                c.setCellValue(entry.value?.toString())
            }
        }
    }

    workbook.write(FileOutputStream(file))
}

fun makeupMapList(mergeMapList: List<MutableMap<String, Any>>, vararg pair: Pair<String, Any>) {
    mergeMapList.forEach {
        mp->
        pair.forEach {
            if (mp[it.first] == null ) {
                mp.put(it.first,it.second)
            }
        }
    }
}

fun mergeMapList(s: String,vararg lists:  List<MutableMap<String, Any>>):List<MutableMap<String, Any>> {
    val ret = mutableListOf<MutableMap<String, Any>>()
    lists.forEach {
        it.forEach {
            mp ->
                if(mp[s]!=null){
                    val fmp  = findOrCreate(ret,s,mp[s]!!)
                    fmp.putAll(mp)
                }
        }
    }
    return ret
}

fun findOrCreate(ret: MutableList<MutableMap<String, Any>>, s: String, any: Any): MutableMap<String, Any> {
    ret.forEach {
        mp->
        if (mp[s]!=null && mp[s] == any) {
            return mp
        }
    }
    var mp = mutableMapOf<String, Any>()
    mp.put(s,any)
    ret.add(mp)
    return mp

}


//fun mergeMapList() {
//}
