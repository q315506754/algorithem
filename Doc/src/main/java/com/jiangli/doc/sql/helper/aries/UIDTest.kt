package com.jiangli.doc.sql.helper.aries

/**
 *
 *
 * @author Jiangli
 * @date 2018/6/6 13:34
 */
fun main(args: Array<String>) {
    println(Ariesutil.convertUUID("yGAJXoxK"))
    println(Ariesutil.convertUUID("dN4JBGYn"))
    println(Ariesutil.convertUUID(100000060.toLong()))
    println(Ariesutil.convertUUID(100002190.toLong()))
    println(Ariesutil.convertUUID(100002318.toLong()))
}