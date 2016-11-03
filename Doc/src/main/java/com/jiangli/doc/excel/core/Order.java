package com.jiangli.doc.excel.core;

import com.jiangli.doc.excel.anno.FromExcel;
import com.jiangli.doc.excel.anno.ToSql;
import com.jiangli.doc.excel.enums.ExcelValType;
import com.jiangli.doc.excel.enums.SqlType;
import net.sf.json.JSONObject;

import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/15 0015 13:25
 */
public class Order {
    @FromExcel(columnName = "订单号", valType = ExcelValType.STRING)
    @ToSql(columnName = "orderid", valType = SqlType.VARCHAR)
    private String orderId;

    @FromExcel(columnName = "客户名称", valType = ExcelValType.STRING)
    @ToSql(columnName = "cusName", valType = SqlType.VARCHAR)
    private String cusName;

    @FromExcel(columnName = "入住人", valType = ExcelValType.STRING)
    @ToSql(columnName = "liveName", valType = SqlType.VARCHAR)
    private String liveName;

    @FromExcel(columnName = "分销商", valType = ExcelValType.STRING)
    @ToSql(columnName = "entName", valType = SqlType.VARCHAR)
    private String entName;

    @FromExcel(columnName = "备注", valType = ExcelValType.STRING)
    @ToSql(columnName = "remark", valType = SqlType.VARCHAR)
    private String remark;

    @FromExcel(columnName = "间夜", valType = ExcelValType.MONEY)
    @ToSql(columnName = "roomNight", valType = SqlType.FLOAT)
    private double roomNight;

    @FromExcel(columnName = "入账金额", valType = ExcelValType.MONEY)
    @ToSql(columnName = "inTotal", valType = SqlType.FLOAT)
    private double inTotal;

    @FromExcel(columnName = "成本", valType = ExcelValType.MONEY)
    @ToSql(columnName = "outTotal", valType = SqlType.FLOAT)
    private double outTotal;

    @FromExcel(columnName = "客户订单号", valType = ExcelValType.STRING)
    @ToSql(columnName = "agentOrderId", valType = SqlType.VARCHAR)
    private String agentOrderId;

    @FromExcel(columnName = "预定日期", valType = ExcelValType.DATEYMD)
    @ToSql(columnName = "orderdate", valType = SqlType.VARCHAR)
    private String orderDate;

    @FromExcel(columnName = "订单时间", valType = ExcelValType.DATEHMS)
    @ToSql(columnName = "orderTime", valType = SqlType.DATETIME)
    private String orderTime;


    @FromExcel(columnName = "城市", valType = ExcelValType.STRING)
    @ToSql(columnName = "city", valType = SqlType.VARCHAR)
    private String city;

    @FromExcel(columnName = "酒店", valType = ExcelValType.STRING)
    @ToSql(columnName = "hotel", valType = SqlType.VARCHAR)
    private String hotel;


    @FromExcel(columnName = "房型", valType = ExcelValType.STRING)
    @ToSql(columnName = "roomtype", valType = SqlType.VARCHAR)
    private String roomType;

    @FromExcel(columnName = "入住时间", valType = ExcelValType.DATEYMD)
    @ToSql(columnName = "checkinDate", valType = SqlType.VARCHAR)
    private String checkinDate;

    @FromExcel(columnName = "离店时间", valType = ExcelValType.DATEYMD)
    @ToSql(columnName = "checkoutDate", valType = SqlType.VARCHAR)
    private String checkoutDate;

    @FromExcel(columnName = "订单类型", valType = ExcelValType.STRING)
    @ToSql(columnName = "orderSource", valType = SqlType.VARCHAR)
    private String orderSource;

    @FromExcel(columnName = "订单来源", valType = ExcelValType.STRING)
    @ToSql(columnName = "operator", valType = SqlType.VARCHAR)
    private String operator;

    @FromExcel(columnName = "供应商", valType = ExcelValType.STRING)
    @ToSql(columnName = "sup", valType = SqlType.VARCHAR)
    private String sup;

    @FromExcel(columnName = "多途签约人", valType = ExcelValType.STRING)
    @ToSql(columnName = "didatourContractor", valType = SqlType.VARCHAR)
    private String didatourContractor;

    @FromExcel(columnName = "跟进助理", valType = ExcelValType.STRING)
    @ToSql(columnName = "followAssistant", valType = SqlType.VARCHAR)
    private String followAssistant;


    @FromExcel(columnName = "订单状态", valType = ExcelValType.STRING)
    @ToSql(columnName = "orderStatus", valType = SqlType.VARCHAR)
    private String orderStatus;


    @FromExcel(columnName = "是否自签", valType = ExcelValType.STRING)
    @ToSql(columnName = "autoSign", valType = SqlType.INT, dataConvert = true)
    private String autoSign;


    @FromExcel(columnName = "即时确认", valType = ExcelValType.STRING)
    @ToSql(columnName = "isHld", valType = SqlType.INT, dataConvert = true)
    private String isHld;


    @FromExcel(columnName = "签约部门", valType = ExcelValType.STRING)
    @ToSql(columnName = "signGroup", valType = SqlType.VARCHAR)
    private String signGroup;

    @FromExcel(columnName = "取消原因", valType = ExcelValType.STRING)
    @ToSql(columnName = "reason", valType = SqlType.VARCHAR)
    private String reason;

    @FromExcel(columnName = "直连成功", valType = ExcelValType.STRING)
    @ToSql(columnName = "isDirectSuccess", valType = SqlType.VARCHAR, dataConvert = true)
    private String isDirectSuccess;


    /**
     * --------------------------------
     * sql only
     * -------------------------------
     */
    @ToSql(columnName = "quantity", valType = SqlType.FLOAT)
    private double quantity;


    @ToSql(columnName = "days", valType = SqlType.FLOAT)
    private double days;


    @ToSql(columnName = "inOnePrice", valType = SqlType.FLOAT)
    private double inOnePrice;


    @ToSql(columnName = "outOnePrice", valType = SqlType.FLOAT)
    private double outOnePrice;

    @ToSql(columnName = "sales", valType = SqlType.VARCHAR)
    private String sales = "";

    @ToSql(columnName = "isTest", valType = SqlType.INT)
    private int isTest = 0;

    @ToSql(columnName = "propId", valType = SqlType.VARCHAR)
    private String propId = "";
    @ToSql(columnName = "rateCode", valType = SqlType.VARCHAR)
    private String rateCode = "";
    @ToSql(columnName = "mobile", valType = SqlType.VARCHAR)
    private String mobile = "";
    @ToSql(columnName = "providerId", valType = SqlType.VARCHAR)
    private String providerId = "";
    @ToSql(columnName = "offlineId", valType = SqlType.VARCHAR)
    private String offlineId = "";

    @ToSql(columnName = "orderRemark", valType = SqlType.VARCHAR)
    private String orderRemark = "";

    @ToSql(columnName = "createTime", valType = SqlType.VARCHAR)
    private String createTime;

    /**
     * --------------------------------
     * toBeAdded In DB
     * -------------------------------
     */
    @FromExcel(columnName = "省份", valType = ExcelValType.STRING)
//    @ToSql(columnName = "province",valType= SqlType.VARCHAR)
    private String province;

    @FromExcel(columnName = "毛利", valType = ExcelValType.MONEY)
//    @ToSql(columnName = "grossProfit",valType= SqlType.FLOAT)
    private double grossProfit;

    @FromExcel(columnName = "间夜毛利", valType = ExcelValType.MONEY)
//    @ToSql(columnName = "gProfitPerNight",valType= SqlType.FLOAT)
    private double gProfitPerNight;


    @FromExcel(columnName = "自签产量", valType = ExcelValType.MONEY)
//    @ToSql(columnName = "signRoomNight",valType= SqlType.FLOAT)
    private double signRoomNight;

    @FromExcel(columnName = "集团", valType = ExcelValType.STRING)
//    @ToSql(columnName = "entDoc",valType= SqlType.VARCHAR)
    private String entDoc;

    /**
     * --------------------------------
     * temp
     *
     * @ToSql(columnName = "outOnePrice")
     * -------------------------------
     */


    private List<Color> cellColors;


    public String getAgentOrderId() {
        return agentOrderId;
    }

    public void setAgentOrderId(String agentOrderId) {
        this.agentOrderId = agentOrderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public int getIsTest() {
        return isTest;
    }

    public void setIsTest(int isTest) {
        this.isTest = isTest;
    }

    public String getPropId() {
        return propId;
    }

    public void setPropId(String propId) {
        this.propId = propId;
    }

    public String getRateCode() {
        return rateCode;
    }

    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(String checkinDate) {
        this.checkinDate = checkinDate;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getSup() {
        return sup;
    }

    public void setSup(String sup) {
        this.sup = sup;
    }

    public String getDidatourContractor() {
        return didatourContractor;
    }

    public void setDidatourContractor(String didatourContractor) {
        this.didatourContractor = didatourContractor;
    }

    public String getFollowAssistant() {
        return followAssistant;
    }

    public void setFollowAssistant(String followAssistant) {
        this.followAssistant = followAssistant;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAutoSign() {
        return autoSign;
    }

    public void setAutoSign(String autoSign) {
        this.autoSign = autoSign;
    }

    public String getIsHld() {
        return isHld;
    }

    public void setIsHld(String isHld) {
        this.isHld = isHld;
    }

    public String getSignGroup() {
        return signGroup;
    }

    public void setSignGroup(String signGroup) {
        this.signGroup = signGroup;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getIsDirectSuccess() {
        return isDirectSuccess;
    }

    public void setIsDirectSuccess(String isDirectSuccess) {
        this.isDirectSuccess = isDirectSuccess;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getGrossProfit() {
        return grossProfit;
    }

    public void setGrossProfit(double grossProfit) {
        this.grossProfit = grossProfit;
    }

    public double getgProfitPerNight() {
        return gProfitPerNight;
    }

    public void setgProfitPerNight(double gProfitPerNight) {
        this.gProfitPerNight = gProfitPerNight;
    }

    public double getSignRoomNight() {
        return signRoomNight;
    }

    public void setSignRoomNight(double signRoomNight) {
        this.signRoomNight = signRoomNight;
    }

    public String getEntDoc() {
        return entDoc;
    }

    public void setEntDoc(String entDoc) {
        this.entDoc = entDoc;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Color> getCellColors() {
        return cellColors;
    }

    public void setCellColors(List<Color> cellColors) {
        this.cellColors = cellColors;
    }

    public double getDays() {
        return days;
    }

    public void setDays(double days) {
        this.days = days;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getLiveName() {
        return liveName;
    }

    public void setLiveName(String liveName) {
        this.liveName = liveName;
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

    public double getInOnePrice() {
        return inOnePrice;
    }

    public void setInOnePrice(double inOnePrice) {
        this.inOnePrice = inOnePrice;
    }

    public double getOutTotal() {
        return outTotal;
    }

    public void setOutTotal(double outTotal) {
        this.outTotal = outTotal;
    }

    public double getOutOnePrice() {
        return outOnePrice;
    }

    public void setOutOnePrice(double outOnePrice) {
        this.outOnePrice = outOnePrice;
    }

    @Override
    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}
