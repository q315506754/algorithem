package com.jiangli.doc.sql.helper.aries

import org.apache.commons.lang.time.DateFormatUtils
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2018/10/22 19:29
 */
fun main(args: Array<String>) {
//    val mobile="17521383715"
    val mobile="13761156786"
    println("get sms:$mobile:code")
    println("get sms:$mobile:code:times")
    println("get sms:+86$mobile:code")
    println("get sms:+86$mobile:code:times")
    println("get sms:code:power")
    println("set sms:code:power 8888")

    val dt = DateFormatUtils.format(Date(), "yyyyMMdd")
    println("hset base:sms:$mobile:$dt:times GLOBAL 0")
    println("hset base:sms:$mobile:$dt:times CODE 0")

    val jedisPool = Ariesutil.getYanfaPool()
//    val jedisPool = Ariesutil.getYufaPool()

    jedisPool.execute {
        println(it.get("sms:code:power"))
    }

    val split = """
18862280181
17621168106
17721385968
17521306824
13917543249
15295996600
18626269852
13061758719
16602152404
18516503957
13761156786
+8613761156786
+14075429267
+16365179382
18612988685
15295996600
1376115678
17521768010
    """.trimIndent().split("\n")

    split.forEach {
        val trim = it.trim()
        if (trim.isNotBlank()) {
            println("del base:sms:$trim:$dt:times")
        }
    }

    print("del ")
    split.forEach {
        val trim = it.trim()
        if (trim.isNotBlank()) {
            print("base:sms:$trim:$dt:times ")
        }
    }

    println()
    println()
    print("\r\nsadd base:sms:whites")
    split.forEach {
        val trim = it.trim()
        if (trim.isNotBlank()) {
            print(" $trim")
        }
    }
    println()
    println()

    println("set base:sms:code:provider DAHANTC")
    println("set base:sms:text:provider DAHANTC")
    println("get base:sms:code:provider")
    println("get base:sms:text:provider")
    println("sadd base:sms:whites")
    println("smembers base:sms:whites")
    println("hgetall base:sms:code:provider")

    println("PUBLISH  base:sms:whites:channel 11")

    println("hgetall base:video:3130")
    println("hgetall base:video:3168")

    println("hgetall base:sms:17621168106:20181221:times")
    println("hset base:sms:17621168106:17621168106:times CODE 25")

    println("hgetall base:sms:17621168106:$dt:times")
    println("hset base:sms:17621168106:$dt:times CODE 25")

    println("hgetall base:video:3387")
    println("hgetall base:video:7017")
    println("smembers aries:lotteryActivityApply:35")

    println("get sms:17521768010:code")
    println("get sms:17521768010:code:times")
    println("get sms:+8617521768010:code:times")
    println("get sms:13761156786:code:times")
}
