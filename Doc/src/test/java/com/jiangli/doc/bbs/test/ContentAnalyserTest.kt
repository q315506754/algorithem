package com.jiangli.doc.bbs.test

import com.jiangli.common.utils.PinyinUtil
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
    fun numberRange2223() {
        println("谁能拼出这段拼音")
    }

    @Test
    fun numberRange() {
        ContentAnalyser.isNum('0')

        val str = """谁能拼出这段拼音 dai kan dai zuo wang ke jia Q{8.7.0.4.9.0.3.5}"""
        println(str)
        println("谁能拼出这段拼音")
//        val str = """6666"""
//        val str = """这课太难了，不过还好，我在⒬|群【⒌４２６⒌Ⓞ❻５⒉】找到了人帮我ԚkǘԱ˞Ɉ"""
//        val str = """智慧树的题真的很难，不过运气不错，我在这个筘群【 ⑤ ④ ⒉ ６ ⒌ O ６ ❺ ❷】找到了answerɚӭʙ֋ҊΎ˻"""
        println(PinyinUtil.getPinyinString(str))
        println(ContentAnalyser.getNumberPos(str))
        println(ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), ContentAnalyser.conNum_least, ContentAnalyser.NUM_DIST))
        println( ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), ContentAnalyser.conNum_least, ContentAnalyser.NUM_DIST))
        var message = ContentAnalyser.getNumberRange(ContentAnalyser.getNumberPos(str), ContentAnalyser.conNum_least, ContentAnalyser.NUM_DIST)
        if (message.size>0) {
            println(message)//111
            println(str[message[0].range.first].toChar()+" "+str[message[0].range.last].toChar())
        }
        println(ContentAnalyser.analyse(str))
    }

    @Test
    fun testWeight() {
        ContentAnalyser.isNum('0')
        println(ContentAnalyser.getWeight("""1373313870"""))
        println(ContentAnalyser.getWeight("""已知对学生记录的描述为：structstudent{intnum;charname[20],sex;struct{intyear,month,day;}birthday;};structstudentstu;设变量stu中的'生日'是'1995年11月12日'，对'birthday'正确赋值的程序是______。"""))
        println(ContentAnalyser.getWeight("""我 ke yi 代 zuo 【课程】拿man fen ,全 bao 我de ..Q【8-7-0-4-9-0-3-5】"""))
        println(ContentAnalyser.getWeight("""大【代】家【看】的【网】学【课】习【加】进【Q】度【8】做【7】的【0】怎【4】么【9】样【0】了【3】，【5】是【全】否【包】满分了？"""))
        println(ContentAnalyser.getWeight("""我 ke yi 代 zuo 【课程】拿man fen ,全 bao 我de ..Q【8-7-0-4-9-0-3-5】"""))

        println(ContentAnalyser.getWeight("""不想看的，了解一下，1373313870 """))
        println(ContentAnalyser.getWeight("""分数组成是24分的学习进度，16分的章节测试，30分见面课，30分的期末考"""))
        println(ContentAnalyser.getWeight("""这课太难了，不过还好，我在ⓠ|群【５４２6⃣50⓺5２】找到了人帮我ųƯĻΡϢ֨Ӿ"""))
        println(ContentAnalyser.getWeight("""这（代）个（看）课（加）讲（Q）的(8)真（7）的(0)很（4）棒（9）很（0）不（3）错（5）学（全）到（包）了（高）很（分）多！！！"""))
    }

    @Test
    fun getPinyinWeight() {
        ContentAnalyser.isNum('0')
        println(ContentAnalyser.getPinyinWeight("""1373313870"""))
        println(ContentAnalyser.getPinyinWeight("""根据《食品添加剂使用卫生标准》（GB2760-2007）的规定，（）属于食品添加剂。"""))
        println(ContentAnalyser.getPinyinWeight("""已知对学生记录的描述为：structstudent{intnum;charname[20],sex;struct{intyear,month,day;}birthday;};structstudentstu;设变量stu中的'生日'是'1995年11月12日'，对'birthday'正确赋值的程序是______。"""))
        println(ContentAnalyser.getPinyinWeight("""我 ke yi 代 zuo 【课程】拿man fen ,全 bao 我de ..Q【8-7-0-4-9-0-3-5】"""))
        println(ContentAnalyser.getPinyinWeight("""大【代】家【看】的【网】学【课】习【加】进【Q】度【8】做【7】的【0】怎【4】么【9】样【0】了【3】，【5】是【全】否【包】满分了？"""))
        println(ContentAnalyser.getPinyinWeight("""我 ke yi 代 zuo 【课程】拿man fen ,全 bao 我de ..Q【8-7-0-4-9-0-3-5】"""))

        println(ContentAnalyser.getPinyinWeight("""谁能拼出这段拼音 dai kan dai zuo wang ke jia Q{8.7.0.4.9.0.3.5}"""))
        println(ContentAnalyser.getPinyinWeight("""不想看的，了解一下，1373313870 """))
        println(ContentAnalyser.getPinyinWeight("""分数组成是24分的学习进度，16分的章节测试，30分见面课，30分的期末考"""))
        println(ContentAnalyser.getPinyinWeight("""这课太难了，不过还好，我在ⓠ|群【５４２6⃣50⓺5２】找到了人帮我ųƯĻΡϢ֨Ӿ"""))
        println(ContentAnalyser.getPinyinWeight("""这（代）个（看）课（加）讲（Q）的(8)真（7）的(0)很（4）棒（9）很（0）不（3）错（5）学（全）到（包）了（高）很（分）多！！！"""))
    }

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
    fun rangeTest() {
//        println('５'.toInt())//48
//        println('２'.toInt())//48
        (9380..9400).forEach {
            println("$it "+it.toChar())
        }
    }

    @Test
    fun intuniTest() {
        println("\uD83D\uDE01\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDE13")
        println(java.lang.Integer.toHexString(8914))
        println(java.lang.Integer.toHexString(49))
        println(""+0xD83D)
//        println('加'.toInt())
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

//        println('５'.toInt())//48
//        println('２'.toInt())//48

        println('0'.toInt())//48
        println('9'.toInt())//57

//        println('一'.toInt())//48
//        println('二'.toInt())//48
//        println('九'.toInt())//48

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

//        println('①'.toInt())//9312
//        println('②'.toInt())//9313
//        println('⑨'.toInt())//9320
//
//        println('⓿'.toInt())//9471
//        println('⓿'.toInt())//9460
//
//        println('⓿'.toInt())//9460
//
//        println('❶'.toInt())//10102
//        println('❸'.toInt())//10104
//        println('❽'.toInt())//10109
    }

    @Test
    fun enc() {
        println(ContentAnalyser.signUrl("http://114.55.26.161:9080/courseqa/student/qa/delQuestion?deletePeron=189011443&questionId=572008"))
    }
}