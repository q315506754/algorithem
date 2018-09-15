package com.jiangli.doc.sql

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/4 16:02
 */
fun main(args: Array<String>) {
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)

    println(Ariesutil.injectTest(jdbc, 100011568, UsersCompanyQueryer()))

    println(Ariesutil.injectTest(jdbc, 3, CourseId2CQueryer()))

    println(Ariesutil.injectTest(jdbc, "'互联网时代的组织、群体与个体'", CourseName2CQueryer()))
}