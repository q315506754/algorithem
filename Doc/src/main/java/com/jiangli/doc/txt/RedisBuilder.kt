package com.jiangli.doc.txt

/**
 *
 *
 * @author Jiangli
 * @date 2017/8/11 14:39
 */


fun main(args: Array<String>) {
//    val pool = Redis.getYanfaPool()
//    val jdbc = DB.getJDBCForYanFa()

    val pool = Redis.getYufaPool()
    val jdbc = DB.getJDBCForYuFa()

    //名师id列表
    val idList = DB.getSrc1IdList(jdbc)
    val userIdList = DB.getSrc1UserIdList(jdbc)

    pool.execute{
        val jedis = it
        println(jedis.smembers("th:lock:totals"))

        //12名师api缓存
        jedis.executeDel("th:openapi:teachers:remoteresult:src:1")


        //映射缓存
        userIdList.forEach {
            jedis.executeDel("th:userid:to:teacherid:${it}") //教师单个缓存

            jedis.executeDel("th:myConcernIds:${it}") //我关注的人ids
        }


        idList.forEach {
            jedis.executeDel("th:teacher:${it}") //教师单个缓存

            jedis.executeDel("th:byConcernIds:${it}") //被关注人的teacherId 缓存300个
            jedis.executeDel("th:byConcernNum:${it}") //被关注数量

            jedis.executeDel("th:personalGloryIds:${it}") //个人荣耀 列表
            jedis.executeDel("th:wonderVideo:ids:${it}") //风采视频 列表
            jedis.executeDel("th:quotations:ids:${it}") //我的语录 列表

            jedis.executeDel("th:filtrationMeetCourseIds:${it}") //被过滤的见面课ids
            jedis.executeDel("th:filtrationCourse:ids:${it}") //被过滤的课程ids

            jedis.executeDel("th:webcontroller:teacher:courseIdsStudyCount:ids:${it}") //老师下的课程对应的学习人数 2C
            jedis.executeDel("th:webcontroller:teacher:courseIdsStudyTotalCount:ids:${it}") //老师下的课程对应的学习总人数 （2C+共享课）
        }

//        it.smembers()
    }




}