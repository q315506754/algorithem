package com.jiangli.doc.sql.helper.aries.goods

import com.jiangli.doc.sql.helper.aries.Ariesutil
import com.jiangli.doc.sql.helper.aries.Env
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

    val unit = "知果"
    val dianquanType = 5
    val deleteDianquanSql = """delete from `db_aries_pay_account`.`KNOWLEDGE_COIN`;"""
    println(deleteDianquanSql)
    val deleteGoodsSql = """delete from `db_aries_pay_goods`.`goods` where type=5;"""
    println(deleteGoodsSql)

    val list = arrayListOf(18,50,98,198,588,998)
    var goodsId = 0
    list.forEachIndexed { index, priceYuan ->
        val dianquanNumStr = """${priceYuan}${unit}"""
        val dianquanId = index+1

        val insertDianquanSql = """INSERT INTO `db_aries_pay_account`.`KNOWLEDGE_COIN` (`ID`, `COUPON`, `COIN`, `PRICE`, `CREATE_TIME`, `UPDATE_TIME`, `IS_DELETED`) VALUES ('$dianquanId', '$dianquanNumStr', '$priceYuan.00', '$priceYuan.00', '2018-04-19 18:39:37', NULL, '0');"""
        println(insertDianquanSql)

        //platform
        (1..2).forEach {
            val  queryExistsId = """SELECT ID FROM `db_aries_pay_goods`.`goods` WHERE  platform = $it AND type = $dianquanType AND b_item_id = $dianquanId;"""
            val qIdList = jdbc.query(queryExistsId, ColumnMapRowMapper())
//            println(qIdList)

            goodsId++
            if (qIdList.isNotEmpty()) {
                goodsId = qIdList[0]["ID"] as Int
            }

            val insertGoodsSql = """INSERT INTO `db_aries_pay_goods`.`goods` (`ID`,`platform`, `b_item_id`, `type`, `amount`, `discount_price`, `order_subject`, `order_body`, `cover_img`, `service_url`, `return_url`, `describe`, `on_shelf`, `is_delete`, `create_time`, `update_time`) VALUES (${goodsId},${it}, '$dianquanId', '$dianquanType', '${priceYuan*100}', NULL, '$dianquanNumStr', '$dianquanNumStr', '', '${env.rechargeCbUrl}', '', '$dianquanNumStr', '1', '0', '2018-05-01 18:01:07', NULL);"""
            println(insertGoodsSql)
        }

    }

    println("\r\n\r\ndel pay_account:coin_coupon:list")
}