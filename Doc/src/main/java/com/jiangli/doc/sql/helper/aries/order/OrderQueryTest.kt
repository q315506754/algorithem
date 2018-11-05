package com.jiangli.doc.sql.helper.aries.order

import com.jiangli.common.utils.DateUtil
import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
import com.jiangli.doc.writeMapToExcel
import org.springframework.jdbc.core.ColumnMapRowMapper

/**
 *
 *
 * @author Jiangli
 * @date 2018/8/3 16:42
 */


fun main(args: Array<String>) {
//    val env = Env.YANFA
//    val env = Env.YUFA
    val env = Env.WAIWANG
    val jdbc = Ariesutil.getJDBC(env)


    val outputpath="""C:\Users\Jiangli\Desktop\order_${DateUtil.getCurrentDate_YMDHmsSS_f()}.xlsx"""

    val sql = """
SELECT
  o.CREATE_TIME as '订单创建时间',

  case o.STATUS
  when 0 then '未支付'
  when 1 then '支付成功'
  when 2 then '支付失败'
  ELSE '未知订单状态'
  END as '订单状态',

  cast(g.amount/100 as decimal(12,2)) as '商品价格(元)',

  case g.type
  when 2 then '课程'
  when 5 then '知点充值'
  ELSE '未知商品类型'
  END as '商品类型',


  case g.type
  when 2 then c.COURSE_NAME
  ELSE ''
  END as '课程名字',


  case o.PLATFORM WHEN 1 then '安卓' when 2 then 'IOS' ELSE '未知平台' END as '订单平台',
  case o.PAY_TYPE
  WHEN 1 then '支付宝'
  when 2 then '微信'
  when 3 then 'IOS内购'
  when 4 then '知点购买商品'
  ELSE '未知支付方式'
  END as '支付方式',

  cast(o.AMOUNT/100 as decimal(12,2)) as '订单金额(元)',
  u.NAME as '购买人姓名',
  u.MOBILE as '购买人手机',
  u.EMAIL as '购买人邮箱'
FROM db_aries_pay_core.TBL_ORDER o
  LEFT JOIN db_aries_user.tbl_user u on o.USER_ID = u.ID
  LEFT JOIN db_aries_pay_account.account_relation ar on o.USER_ID = ar.USER_ID
  LEFT JOIN db_aries_pay_account.ios_wallet wios on wios.ACCOUNT_ID = ar.ACCOUNT_ID
  LEFT JOIN db_aries_pay_account.wallet wand on wand.ACCOUNT_ID = ar.ACCOUNT_ID
  LEFT JOIN db_aries_pay_goods.goods g on o.ITEM_ID = g.id
  LEFT JOIN db_aries_2c_course.tm_course c on c.COURSE_ID = g.b_item_id
  LEFT JOIN db_aries_pay_account.knowledge_coin kc on g.b_item_id = kc.id
  LEFT JOIN db_aries_2c_course.tm_user_course tuc on tuc.COURSE_ID = c.COURSE_ID and tuc.USER_ID = o.USER_ID
  LEFT JOIN db_aries_2c_course.tm_user_study_course tusc on tusc.COURSE_ID = c.COURSE_ID and tusc.USER_ID = o.USER_ID
WHERE
  o.IS_DELETE=0
  #and o.STATUS=1
  and o.CREATE_TIME > '2018-08-11 11:00:00'
ORDER BY o.CREATE_TIME DESC ;
""".trimIndent()

    //create
    val qIdList = jdbc.query(sql, ColumnMapRowMapper())

    println(qIdList)

    writeMapToExcel(outputpath,qIdList)

}