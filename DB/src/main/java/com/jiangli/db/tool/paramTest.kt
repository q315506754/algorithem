package com.jiangli.db.tool

import com.google.gson.Gson

/**
 *
 *
 * @author Jiangli
 * @date 2020/12/2 13:55
 */
fun main(args: Array<String>) {
    var mp = """
     {"unitPrice":"1","orderId":"83548922329690","payTime":"1606815572","dealId":"1392929113","tpOrderId":"202012010001413","count":"1","totalMoney":"1","hbBalanceMoney":"0","userId":"4285338796","promoMoney":"0","promoDetail":"","hbMoney":"0","giftCardMoney":"0","payMoney":"1","payType":"1117","returnData":"","partnerId":"6000001","rsaSign":"ftSDhYuytYe1wWRog+JlArUteJltByqAxTXblDTtcHVEkj8kucezYb8ovV+/ddd+2j4lwBg0o6wOvfbmz2vivcwXusX6aDZlEpnpyLwpZ89ZsRRijwLamXdFdVyaggSCEOUpny0DWZsX6B6yNDbCDNQcZ4ncCn7n/vEYPqnbzTg=","status":"2"}
    """.trimIndent()

    val gson = Gson()
    val fromJson = gson.fromJson(mp, Map::class.java)
    println(fromJson)

    var s = ""
    fromJson.forEach { any, u ->
        s = "${s}&${any}=${u}"
    }
    println(s)
}