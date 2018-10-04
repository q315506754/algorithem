package com.jiangli.doc.bbs.test

import com.jiangli.doc.sql.helper.zhihuishu.bbs.AnaRs
import com.jiangli.doc.sql.helper.zhihuishu.bbs.ContentAnalyser
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
    fun unitanaGarbage() {
        anaGarbage("这课太难了，不过还好，我在ⓠ|群【５４２6⃣50⓺5２】找到了人帮我ųƯĻΡϢ֨Ӿ")
        anaGarbage("不会不会真的不会？怎么办，我来帮你呀！安排上了！筘群\n" +
                "【8⃣９❹6⃣⓪❷9❹❼】 ˳ѠъֺǏű")
        anaGarbage("有不有人知道，考试题目类型是怎样的？？进⒬群\n" +
                "【⓹4⃣２❻5⓪6５2】，我知道呀ζœ\u052Eԉ҄ԃŜ")
        anaGarbage("想高芬过了这门课吗，那么推荐【扣⑧⑦0④⑨0③⑤】专业的，老司机发车了！")
//        anaGarbage("专业代课8元一科十五两科，作业期末测试基本满分，稳 +vx  c87506")
        anaGarbage("不想看的，了解一下，1373313870 ")
        anaGarbage("我全都自己一题一题的查，终于写完了，我把这个题全放在了企鹅群里面【8⃣❾4⃣6⃣ⓞ２❾⓸7⃣】ļ˞ǉˌ̉ѾÎ")
        anaGarbage("给你们安利一个实用的筘群【89⃣⓸６029⃣⓸7】,我在里面找到了题，这课已经95分了Ŧ˽ǎͩĸπ֊")
        anaGarbage("㈠㈡㈢㈣㈤㈥㈦㈧㈨㈩")
        anaGarbage("国庆节大家要好好的玩耍，你的课我来做吧  腾迅【八七零四久零三伍】")
        anaGarbage("""13733138701""")
        anaGarbage("""这个课的题也太多了吧，全网都没找到，老天啊，怎么办，来加入釦群
【 ❺ ④ ② ６ ５ ⓪ ６ ❺ ⒉】帮你ђ˖ҮьǜgĄ""")
        anaGarbage("三年来专为学生网课服务 高效质量好，各种课玳做，详情=【扣⑧⑦零④⑨零③⑤】可送全套答案三年来专为学生网课服务 高效质量好，各种课玳做，详情=【扣⑧⑦零④⑨零③⑤】可送全套答案三年来专为学生网课服务 高效质量好，各种课玳做，详情=【扣⑧⑦零④⑨零③⑤】可送全套答案")
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
        anaNoGarbage("55555555")
        anaNoGarbage("233333，100度一下")
        anaNoGarbage("%\uD83D\uDE01\uD83D\uDC4D\uD83D\uDC4D\uD83D\uDE13(｡･∀･)ﾉﾞヾ让你知道在人任性(･ω･。)77-")
        anaNoGarbage("为什么我看完了心里健康教育视频不是100%而是98.8%。")
        anaNoGarbage("小写（大写）如：20000.00（贰万圆整）")
        anaNoGarbage("小写（大写）如：\r\n20000.00（贰万圆整）")
        anaNoGarbage("11月30号23.59分以前")
        anaNoGarbage("10.18  13:30")
        anaNoGarbage("09-10 09:22")
        anaNoGarbage("2018年9月29日19:11:24")
        anaNoGarbage("666666666")
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