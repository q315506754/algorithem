package com.jiangli.doc.bbs.test

import com.jiangli.doc.sql.helper.zhihuishu.bbs.AnaRs
import com.jiangli.doc.sql.helper.zhihuishu.bbs.ContentAnalyser
import com.jiangli.doc.sql.helper.zhihuishu.bbs.numberSet
import org.junit.Test
import kotlin.test.assertTrue

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/29 16:05
 */
class GarbageHunterTest {
    @Test
    fun numberTest() {
        ContentAnalyser.isNum('0')

        numberSet.forEach {
            println("$it ${it.toChar()}")
        }
    }

    @Test
    fun numberRange() {
        ContentAnalyser.isNum('0')

        val list = ContentAnalyser.getNumberPos("智慧树网课Q群：714322198，有啥疑问都加进来问吧~")
        println(list)

        println(ContentAnalyser.getNumberRange(list,7,2))//111
        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("丽江师范高等专科学校的童鞋们，给你们安利一个实用的筘群【⑦①❹3221❾8】,我在里面找到了题，这课已经95分了"),7,2))//111

        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("丽江师范高等专科学校【⑦①❹3221❾8】的童鞋们，给你们安利一个实用的筘群【⑦①❹3221❾8】,我在里面找到了题，这课已经95分了"),7,2))//111
        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos("【⑦①❹3221❾8】丽江师范高等专科学校【⑦①❹3221❾8】的童鞋们，给你们安利一个实用的筘群【⑦①❹3221❾8】,我在里面找到了题，这课已经95分了【⑦①❹3221❾8】"),7,2))//111

        val str = "三年来专为学生网课服务 高效质量好，各种课玳做，详情=【扣⑧⑦零④⑨零③⑤】可送全套答案"
        val message = ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), 7, 2)[0]
        println(message)//111
        println(str[message.range.first].toChar()+" "+str[message.range.last].toChar())
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

    @Test
    fun unitanaGarbage() {
        anaGarbage("不想看的，了解一下，1373313870 ")
        anaGarbage("13733138701")
        anaGarbage("智慧树网课Q群：714322198，有啥疑问都加进来问吧~")
        anaGarbage("内蒙古大学的童鞋们，给你们安利一个实用的筘群【⑦❶❹❸②②19❽】,我在里面找到了题，这课已经95分了")
        anaGarbage("丽江师范高等专科学校的童鞋们，给你们安利一个实用的筘群【⑦①❹3221❾8】,我在里面找到了题，这课已经95分了")
        anaGarbage("大家做完了没啊，我做完了哟，在这个扣群\n" +
                "【7⃣1⃣4⃣3⃣2⃣2⃣1⃣9⃣8⃣】里找的撘案哦Śְ̀Ȉ͘Ϝĭ")
        anaGarbage("ХԌԎˏÚϸ大家做完了没啊，我做完了哟，在这个扣群\n" +
                "【⑦❶4③②②①⑨⑧】里找的撘案哦")
        anaGarbage("三年来专为学生网课服务 高效质量好，各种课玳做，详情=【扣⑧⑦零④⑨零③⑤】可送全套答案")
        anaGarbage("分享一个知到答案免费共享交流群给大家。群号：一八八二五一零六一（群号是大写的，小写的平台会检测违规。）")
    }

    @Test
    fun unitanaNoGarbage() {
        anaNoGarbage("%\uD83D\uDE01\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDE13(｡･∀･)ﾉﾞヾ让你知道在人任性(･ω･。)77-")
        anaNoGarbage("为什么我看完了心里健康教育视频不是100%而是98.8%。")
        anaNoGarbage("小写（大写）如：20000.00（贰万圆整）")
        anaNoGarbage("11月30号23.59分以前")
        anaNoGarbage("10.18  13:30")
        anaNoGarbage("09-10 09:22")
        anaNoGarbage("2018年9月29日19:11:24")
//        anaNoGarbage("20000000元")
    }

    fun anaGarbage(str: String): Boolean {
        val b = ContentAnalyser.analyse(str) != AnaRs.OK
        assertTrue(b,"预期包含广告！: $str")
        return b
    }
    fun anaNoGarbage(str: String): Boolean {
        val b = ContentAnalyser.analyse(str) != AnaRs.OK
        assertTrue(!b,"预期不包含广告！: $str")
        return b
    }
}