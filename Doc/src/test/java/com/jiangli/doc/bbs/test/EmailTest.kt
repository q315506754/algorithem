package com.jiangli.doc.bbs.test

import com.jiangli.common.utils.EmailUtil.sendByJavaMail
import com.jiangli.doc.sql.helper.zhihuishu.bbs.*
import org.junit.Test
import java.util.*

/**
 *
 *
 * @author Jiangli
 * @date 2018/9/30 9:57
 */
class EmailTest {
    fun accept(a:DailyWork) {

    }

    @Test
    fun objTest() {
        val a: DailyWork = object : DailyWork("aaaa") {
            override fun doWork() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        accept(a)
    }

    @Test
    fun TimeDtoTest() {
        val timeDto = TimeDto(12, 66)
        println(timeDto)
    }

    @Test
    fun uniTest() {
        val of = "日月年号:-元.分秒班年级".toCharArray().toSet()
        println(of.intersect(setOf("小写（大写）如：20000.00（贰万圆整）".toCharArray())))
        println(of.intersect(setOf("11月30号23.59分以前".toCharArray())))
        println(of.intersect("1日月年号".toCharArray().toSet()))
    }

    @Test
    fun DailyWorkTest() {
        val sendMailWork = object : DailyWork("0930-1200/30,1330-2100/30") {
            override fun doWork() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        println(sendMailWork.getNow())
        println(sendMailWork.findNext(sendMailWork.getNow()))
        println(sendMailWork.findNext(TimeDto(0,0)))
        println(sendMailWork.findNext(TimeDto(9,29)))
        println(sendMailWork.findNext(TimeDto(9,30)))
        println(sendMailWork.findNext(TimeDto(11,30)))
        println(sendMailWork.findNext(TimeDto(12,0)))
        println(sendMailWork.findNext(TimeDto(20,30)))
        println(sendMailWork.findNext(TimeDto(21,30)))
        println(sendMailWork.findNext(TimeDto(22,30)))

//        Executors.newSingleThreadExecutor().execute(sendMailWork)
//        LockSupport.park()
    }

    @Test
    fun dateTest() {
        val message = Calendar.getInstance()
        println(message.get(Calendar.DATE))
        println(message.get(Calendar.DAY_OF_YEAR))
        println(message.get(Calendar.DAY_OF_MONTH))
        println(message.get(Calendar.DAY_OF_WEEK))
        println(message.get(Calendar.DAY_OF_WEEK_IN_MONTH))

        println(message.get(Calendar.HOUR))
        println(message.get(Calendar.HOUR_OF_DAY))
        println(message.get(Calendar.MINUTE))
    }

    @Test
    fun numberTest2() {
        illegalList.addElement(IllegalQa("ZHS_BBS.QA_ANSWER",AnaRs.UNICODE_NUMBER,132,"23333","1252784899@qq.com 感谢！！222","{sdsd}"))
        illegalList.addElement(IllegalQa("ZHS_BBS.QA_ANSWER",AnaRs.UNICODE_NUMBER,132,"23333","1252784899@qq.com 感谢！！222","{sdsd}"))
        println(parseHtml())

//        sendByJavaMail("问答广告内容检测",
//                "315506754@qq.com,315506754@qq.com,315506754@qq.com",
//                parseHtml())
    }

    @Test
    fun numberTest() {
        sendByJavaMail("问答广告内容检测",
                "315506754@qq.com,315506754@qq.com,315506754@qq.com",
                """
<table border="1">
  <tr>
    <th>检测时间</th>
    <th>库</th>
    <th>违规策略</th>
    <th>主键id</th>
    <th>用户id</th>
    <th>内容</th>
  </tr>
  <tr>
    <td>2018-09-30 09:11:06</td>
    <td>ZHS_BBS.QA_ANSWER</td>
    <td>CONTINUOUS_NUMBER</td>
    <td>1271025</td>
    <td>187916017</td>
    <td>1252784899@qq.com 感谢！！</td>
  </tr>
  <tr>
    <td>2018-09-30 09:11:06</td>
    <td>ZHS_BBS.QA_ANSWER</td>
    <td>CONTINUOUS_NUMBER</td>
    <td>1271025</td>
    <td>187916017</td>
    <td>1252784899@qq.com 感谢！！222</td>
  </tr>
</table>
""".trimIndent())
    }
}