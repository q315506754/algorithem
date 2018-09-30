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
    fun numberTest() {
        ContentAnalyser.isNum('0')

        numberSet.forEach {
            println("$it ${it.toChar()}")
        }

        println(ContentAnalyser.getDiffs("55555555"))
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

        val str = "想高芬过了这门课吗，那么推荐【扣⑧⑦0④⑨0③⑤】专业的，老司机发车了！"
        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), ContentAnalyser.conNum, 2))
        println( ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), 7, 2))
        val message = ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), 7, 2)[0]
        println(message)//111
        println(str[message.range.first].toChar()+" "+str[message.range.last].toChar())
        println(ContentAnalyser.analyse(str))
    }

    @Test
    fun unicodeTest() {

        println('0'.toInt())//48
        println('9'.toInt())//57

        println('一'.toInt())//48
        println('二'.toInt())//48
        println('九'.toInt())//48

        println('o'.toInt())//111
        println('O'.toInt())//79

        "x⑧⑦零④⑨零③⑤x".toCharArray().forEach {
            if (!ContentAnalyser.isNum(it)) {
                System.err.println("!!!!!!!!!!!$it ${it.toInt()}")
            }
        }

        println(ContentAnalyser.getNumberPos("x⑧⑦零④⑨零③⑤x"))
        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("x⑧⑦零④⑨零③⑤x"),7,2))

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