package com.jiangli.doc.bbs.test

import com.jiangli.doc.sql.helper.zhihuishu.bbs.ContentAnalyser
import com.jiangli.doc.sql.helper.zhihuishu.bbs.numberSet
import org.junit.Test

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/30 9:57
 */
class ContentAnalyserTest {

    @Test
    fun allnumberTest() {
        ContentAnalyser.isNum('0')

        numberSet.forEach {
            println("$it ${it.toChar()}")
        }

        println(ContentAnalyser.getDiffs("55555555"))
    }

    @Test
    fun getInvalidChar() {
        val s = "8⃣❾4⃣6⃣ⓞ２❾⓸7⃣"
        val ret = arrayListOf<Int>()
        s.toCharArray().forEach {
            val element = it.toInt()
            System.out.println("!!!!!!!!!!!$it $element ${ContentAnalyser.isNum(it)}")
            if (!ContentAnalyser.isNum(it) && !ret.contains(element)) {
                ret.add(element)
            }
        }
        println(ret.joinToString())
    }

    @Test
    fun numberRange() {
        ContentAnalyser.isNum('0')

//        val list = ContentAnalyser.getNumberPos("智慧树网课Q群：714322198，有啥疑问都加进来问吧~")
//        println(list)
//
//        println(ContentAnalyser.getNumberRange(list,7,2))//111
//        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("丽江师范高等专科学校的童鞋们，给你们安利一个实用的筘群【⑦①❹3221❾8】,我在里面找到了题，这课已经95分了"),7,2))//111
//
//        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("丽江师范高等专科学校【⑦①❹3221❾8】的童鞋们，给你们安利一个实用的筘群【⑦①❹3221❾8】,我在里面找到了题，这课已经95分了"),7,2))//111
//        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("【⑦①❹3221❾8】丽江师范高等专科学校【⑦①❹3221❾8】的童鞋们，给你们安利一个实用的筘群【⑦①❹3221❾8】,我在里面找到了题，这课已经95分了【⑦①❹3221❾8】"),7,2))//111

        val str = """这个课的题也太多了吧，全网都没找到，老天啊，怎么办，来加入釦群
【 ❺ ④ ② ６ ５ ⓪ ６ ❺ ⒉】帮你ђ˖ҮьǜgĄ"""
        println(ContentAnalyser.getNumberPos(str))
        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), ContentAnalyser.conNum, 2))
        println( ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), 7, 2))
        var message = ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), 7, 2)
        if (message.size>0) {
            println(message)//111
            println(str[message[0].range.first].toChar()+" "+str[message[0].range.last].toChar())
        }
        println(ContentAnalyser.analyse(str))
    }

    @Test
    fun rangeTest() {
        println('５'.toInt())//48
        println('２'.toInt())//48
        (9420..9480).forEach {
            println("$it "+it.toChar())
        }
    }

    @Test
    fun intuniTest() {
        println("\uD83D\uDE01\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDE13")
        println(java.lang.Integer.toHexString(8914))
        println(java.lang.Integer.toHexString(49))
        println(""+0xD83D)
        println('加'.toInt())
        println("aa\u0008bb")
        println("aa\u0008bb".length)
        println("aa\u22d2bb")
        println('\u22d2'.toInt())
        println("【8⃣❾4⃣6⃣ⓞ２❾⓸7⃣】")
        println("【8⃣❾4⃣6⃣ⓞ２❾⓸7⃣】".length)
    }

    @Test
    fun testPos() {
        ContentAnalyser.isNum('0')
        println('\u0008')
        println("aa\u0008bb")
        println(ContentAnalyser.isNum('2'))
//        val s = "【8⃣❾4⃣6⃣ⓞ２❾⓸7⃣】ļ˞ǉˌ̉ѾÎ"
        val s = "㈠㈡㈢㈣㈤㈥㈦㈧㈨㈩"
        println(ContentAnalyser.getNumberPos(s))
        s.toCharArray().forEach {
             System.err.println("!!!!!!!!!!!$it ${it.toInt()} ${ContentAnalyser.isNum(it)}")
        }
    }

    @Test
    fun unicodeTest() {

        println('５'.toInt())//48
        println('２'.toInt())//48

        println('0'.toInt())//48
        println('9'.toInt())//57

        println('一'.toInt())//48
        println('二'.toInt())//48
        println('九'.toInt())//48

        println('o'.toInt())//111
        println('O'.toInt())//79


        println(ContentAnalyser.getNumberPos("xs⑧x⑦x零④⑨零y③y⑤xd"))
        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("xs⑧x⑦x零④⑨零y③y⑤xd"),7,2))

        val x = 9311
//        (9310..9500).forEach {
//            println("$it "+it.toChar())
//        }

//        (10090..10150).forEach {
//            println("$it "+it.toChar())
//        }

        println('①'.toInt())//9312
        println('②'.toInt())//9313
        println('⑨'.toInt())//9320

        println('⓿'.toInt())//9471
        println('⓿'.toInt())//9460

        println('⓿'.toInt())//9460

        println('❶'.toInt())//10102
        println('❸'.toInt())//10104
        println('❽'.toInt())//10109
    }

    @Test
    fun enc() {
        println(ContentAnalyser.signUrl("http://114.55.26.161:9080/courseqa/student/qa/delQuestion?deletePeron=189011443&questionId=572008"))
    }
}