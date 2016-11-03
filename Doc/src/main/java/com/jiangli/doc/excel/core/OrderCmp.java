package com.jiangli.doc.excel.core;

import com.jiangli.doc.excel.anno.FromExcel;
import com.jiangli.doc.excel.anno.ToSql;
import com.jiangli.doc.excel.enums.ExcelValType;
import com.jiangli.doc.excel.enums.SqlType;
import net.sf.json.JSONObject;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/18 0018 14:12
 */
public class OrderCmp {
    @FromExcel(columnName = "订单号", valType = ExcelValType.STRING)
    @ToSql(columnName = "orderid", valType = SqlType.VARCHAR)
    private String orderId;

    @FromExcel(columnName = "间夜", valType = ExcelValType.MONEY)
    @ToSql(columnName = "roomNight", valType = SqlType.FLOAT)
    private double roomNight;

    @FromExcel(columnName = "入账金额", valType = ExcelValType.MONEY)
    @ToSql(columnName = "inTotal", valType = SqlType.FLOAT)
    private double inTotal;

    @FromExcel(columnName = "成本", valType = ExcelValType.MONEY)
    @ToSql(columnName = "outTotal", valType = SqlType.FLOAT)
    private double outTotal;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getRoomNight() {
        return roomNight;
    }

    public void setRoomNight(double roomNight) {
        this.roomNight = roomNight;
    }

    public double getInTotal() {
        return inTotal;
    }

    public void setInTotal(double inTotal) {
        this.inTotal = inTotal;
    }

    public double getOutTotal() {
        return outTotal;
    }

    public void setOutTotal(double outTotal) {
        this.outTotal = outTotal;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
